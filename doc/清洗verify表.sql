---清洗MC_MEMBER_CODE声明存储过程
create or replace  procedure  WASH_MC_MEMBER_CODE(inCount in number, outNumber out number)
as
  Cursor members is select i.id from member_mem_info i where i.mc_member_code is null and rownum<=inCount;
  someMember varchar(20);
  mc_code varchar(20);
begin
  outNumber:=0;
  open members;
  LOOP
    FETCH members INTO someMember;
    select S_MC_MEMBER_CODE.NEXTVAL into mc_code from dual;
    EXIT WHEN members%NOTFOUND;
    update member_mem_info i set i.mc_member_code = mc_code where i.id = someMember;
    outNumber:= outNumber + 1;
  END LOOP;
  COMMIT;
  EXCEPTION
     WHEN OTHERS THEN
       outNumber:=-1;
       rollback;
end WASH_MC_MEMBER_CODE;

--清除member_verify_jjzx_conflict中mem_id和men_name相同的数据,只取id最小的那条
delete from member_verify_jjzx_conflict conflict
where (conflict.mem_id,conflict.men_name) in (select mem_id,men_name from member_verify_jjzx_conflict group by mem_id,men_name having count(*) > 1)
and conflict.id not in (select min(id) from member_verify_jjzx_conflict group by mem_id,men_name having count(*)>1)

create table MEMBER_VERIFY_CONFLICT
(
  ID          NUMBER not null,
  MEM_INFO_ID NUMBER,
  MEM_ID      VARCHAR2(15),
  MEM_NUM     VARCHAR2(30),
  MEN_NAME    VARCHAR2(50),
  PASSWORD    VARCHAR2(50),
  STATUS      VARCHAR2(1) default 0 not null,
  LAST_UPD    DATE,
  CDS_ID      VARCHAR2(100)
);

-- Add comments to the columns 
comment on column MEMBER_VERIFY_CONFLICT.STATUS
  is '0-未验证  1-已验证';
-- Create/Recreate primary, unique and foreign key constraints 
alter table MEMBER_VERIFY_CONFLICT
  add constraint PK_MEMBER_VERIFY_CONFLICT primary key (ID);
  

--给membr_verify添加索引
CREATE INDEX member_verify_memId ON  member_verify(MEM_ID ASC);

--给membr_verify添加索引
CREATE INDEX member_verify_jjzx_memId ON  member_verify_jjzx(MEM_ID ASC);

--给membr_verify添加索引
CREATE INDEX verify_jjzx_conflict_memId ON  member_verify_jjzx_conflict(MEM_ID ASC);


CREATE SEQUENCE MEMBER_V_CONFLICT_SEQUANCE
INCREMENT BY 1  
START WITH 1     
NOMAXVALUE     
NOCYCLE 
CACHE 10;



select * from member_verify;  --目标的表

select * from member_verify_conflict; --目标冲突表

select * from member_verify_jjzx;  --无冲突数据

select * from member_verify_jjzx_conflict;  --有冲突数据


---step1 v2,v3 自身冲突存储过程
create or replace procedure wash_jjzxv_conflict_byself(minV in number,maxV in number, outNumber out number)
as
  tempN number;
begin
  tempN:=0;
  outNumber:=0;
  
  insert into 
    member_verify_conflict(id,mem_info_id,mem_id,mem_num,men_name,password,status,last_upd) 
  select 
    MEMBER_V_CONFLICT_SEQUANCE.NEXTVAL,jjzxv.mem_info_id,jjzxv.mem_id,jjzxv.mem_num,jjzxv.men_name,jjzxv.password,0,jjzxv.last_upd 
  from 
    member_verify_jjzx jjzxv 
  where 
    jjzxv.men_name in (select men_name from member_verify_jjzx group by men_name having count(1) > 1) 
  and 
    jjzxv.id > minV 
  and 
    jjzxv.id < maxV;
 
  tempN:=sql%rowcount;
  outNumber:=0+tempN;
  
  insert into 
    member_verify_conflict(id,mem_info_id,mem_id,mem_num,men_name,password,status,last_upd) 
  select 
    MEMBER_V_CONFLICT_SEQUANCE.NEXTVAL,jjzxv.mem_info_id,jjzxv.mem_id,jjzxv.mem_num,jjzxv.men_name,jjzxv.password,0,jjzxv.last_upd 
  from 
    member_verify_jjzx_conflict jjzxv 
  where 
    jjzxv.men_name in (select men_name from member_verify_jjzx_conflict group by men_name having count(1) > 1) 
  and 
    jjzxv.id > minV 
  and 
    jjzxv.id < maxV;
  
  tempN:=sql%rowcount;
  outNumber:=outNumber+tempN;
    
  COMMIT;
  EXCEPTION
     WHEN OTHERS THEN
       outNumber:=-1;
       rollback;
end wash_jjzxv_conflict_byself;

---step2 v2,v3 自己没有冲突,但是跟verify有冲突存储过程
create or replace procedure wash_jjzxv_conflict_byVerify(minV in number,maxV in number, outNumber out number)
as
  tempN number;
begin
  tempN:=0;
  outNumber:=0;
  
  insert into 
    member_verify_conflict(id,mem_info_id,mem_id,mem_num,men_name,password,status,last_upd) 
  select 
    MEMBER_V_CONFLICT_SEQUANCE.NEXTVAL,jjzxv.mem_info_id,jjzxv.mem_id,jjzxv.mem_num,jjzxv.men_name,jjzxv.password,0,jjzxv.last_upd 
  from
    (select 
      * 
    from 
      member_verify_jjzx 
    where 
      men_name in (select men_name from member_verify_jjzx group by men_name having count(1)=1)
    and men_name in (select men_name from member_verify)) jjzxv
  where
    id > minV and id < maxV;
 
  tempN:=sql%rowcount;
  outNumber:=0+tempN;
  
  insert into 
    member_verify_conflict(id,mem_info_id,mem_id,mem_num,men_name,password,status,last_upd) 
  select 
    MEMBER_V_CONFLICT_SEQUANCE.NEXTVAL,jjzxv.mem_info_id,jjzxv.mem_id,jjzxv.mem_num,jjzxv.men_name,jjzxv.password,0,jjzxv.last_upd
  from 
    (select 
       * 
     from 
       member_verify_jjzx_conflict v 
     where 
       v.men_name in (select men_name from member_verify_jjzx_conflict group by men_name having count(1)=1) 
     and men_name in (select men_name from member_verify)) jjzxv
  where 
    id > minV and id < maxV;
  
  tempN:=sql%rowcount;
  outNumber:=outNumber+tempN;
    
  COMMIT;
  EXCEPTION
     WHEN OTHERS THEN
       outNumber:=-1;
       rollback;
end wash_jjzxv_conflict_byVerify;


---step3 v2表自己没有冲突数据,并且跟verify没有冲突 存储过程
create or replace procedure wash_jjzxv_noConflict1(minV in number,maxV in number, outNumber out number)
as
begin
  outNumber:=0;

  insert into member_verify(id,mem_info_id,mem_id,mem_num,men_name,last_upd,sha1)
  select 
    S_MC_MEMBER_CODE.NEXTVAL,temp.mem_info_id,temp.mem_id,temp.mem_num,temp.men_name,temp.last_upd,temp.password
  from 
    (select 
       * 
     from 
       member_verify_jjzx 
     where 
       men_name in (select men_name from member_verify_jjzx group by men_name having count(1)=1) 
     and men_name not in (select men_name from member_verify)) temp
  where 
    temp.id > minV and temp.id < maxV;
    
  outNumber:=sql%rowcount;
    
  COMMIT;
  EXCEPTION
     WHEN OTHERS THEN
       outNumber:=-1;
       rollback;
end wash_jjzxv_noConflict1;


---step4 v3表自己没有冲突数据,并且跟verify没有冲突存储过程
create or replace procedure wash_jjzxv_noConflict2(minV in number,maxV in number, outNumber out number)
as
begin
  outNumber:=0;

 insert into member_verify(id,mem_info_id,mem_id,mem_num,men_name,last_upd,sha1)
 select 
   S_MC_MEMBER_CODE.NEXTVAL,temp.mem_info_id,temp.mem_id,temp.mem_num,temp.men_name,temp.last_upd,temp.password
 from 
   (select 
      * 
    from 
      member_verify_jjzx_conflict v 
    where 
      v.men_name in (select men_name from member_verify_jjzx_conflict group by men_name having count(1)=1) 
    and 
      men_name not in (select men_name from member_verify)) temp
  where temp.id > minV and temp.id < maxV;
  
  outNumber:=sql%rowcount;
    
  COMMIT;
  EXCEPTION
     WHEN OTHERS THEN
       outNumber:=-1;
       rollback;
end wash_jjzxv_noConflict2;


---step5 只要跟v1表一致就更新进member_verify声明存储过程
create or replace procedure wash_jjzxv_on_exists(outNumber out number)
as
  tempN number;
begin
  tempN:=0;
  outNumber:=0;
    
  update 
    member_verify rv set rv.sha1 = (select jjzxv.password from member_verify_jjzx jjzxv where jjzxv.men_name=rv.men_name and jjzxv.mem_id = rv.mem_id group by rv.id,jjzxv.password )
  where
    rv.id in ( select rv.id from member_verify_jjzx jjzxv where jjzxv.men_name=rv.men_name and jjzxv.mem_id = rv.mem_id and rv.sha1 is null group by rv.id );
    
    
  tempN:=sql%rowcount;
  outNumber:=0+tempN;
  
  update 
    member_verify rv set rv.sha1 = (select jjzxv.password from member_verify_jjzx_conflict jjzxv where jjzxv.men_name=rv.men_name and jjzxv.mem_id = rv.mem_id group by rv.id,jjzxv.password )
  where
    rv.id in ( select rv.id from member_verify_jjzx_conflict jjzxv where jjzxv.men_name=rv.men_name and jjzxv.mem_id = rv.mem_id and rv.sha1 is null group by rv.id );
  
  tempN:=sql%rowcount;
  outNumber:=outNumber+tempN;
    
  COMMIT;
  EXCEPTION
     WHEN OTHERS THEN
       outNumber:=-1;
       rollback;
end wash_jjzxv_on_exists;