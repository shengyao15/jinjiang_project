delete from MEMBER_VERIFY where mem_id in ('1-FFFFF01','1-FFFFF02');
delete from MEMBER_MEM_INFO where mem_id in ('1-FFFFF01','1-FFFFF02');
delete from T_VBP_MEMBER_BIND_INFO;

insert into MEMBER_MEM_INFO(
mem_id,name,title,email,phone,mem_num,card_no,mem_type,mem_tier,
status,activate_code,id,ENROLL_DATE,CARD_LEVEL,IDENTITY_TYPE,IDENTITY_NO,
MC_MEMBER_CODE) 
values('1-FFFFF01','name01','Mr.','email001@126.com','username01','234234234',
'111f128','Individual','1','Active','Activiate',9999001,
sysdate,'银卡','Others','11111','mcMemberCode01');

insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD) 
values(101010101,9999001, '1-FFFFF01', '95339533','username01','password01');

insert into MEMBER_MEM_CARD
(ID,MEM_INFO_ID,MEM_ID,CARD_TYPE_CD,X_CARD_NUM,
SOURCE,VALID_DATE,DUE_DATE,STATUS,CRM_KEY,DT_STATUS,DT_MSG,DT_UPD) 
values(12120101,9999001, '1-FFFFF01','GOLD', '111f128', 'JJ001', 
TIMESTAMP '2010-5-16 0:00:00.000000', sysdate, 'Sent',  '1','1','1', sysdate    );

insert into t_vbp_member_score_level_info 
(UPDATED_DATE, id, member_info_id, score_type, rank_score, crm_key, mem_num)
values (sysdate, 288282, 9999001, 0, 500000, 62024645.000000000000000, '95339533');

insert into MEMBER_MEM_INFO(
mem_id,name,title,email,phone,mem_num,card_no,mem_type,mem_tier,
status,activate_code,id,ENROLL_DATE,CARD_LEVEL,IDENTITY_TYPE,IDENTITY_NO,
MC_MEMBER_CODE) 
values('1-FFFFF02','name02','Mr.','email002@126.com','username02','23423423402',
'111f12802','Individual','1','Active','Activiate',9999002,
sysdate,'银卡','Others','1111102','mcMemberCode02');

insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD) 
values(101010102,9999002, '1-FFFFF02', '111f12802','username02','password02');


insert into T_VBP_MEMBER_BIND_INFO(id,
member_id, mc_member_code, bind_type,
bind_key, channel, status, create_time, update_time)
values(100001, '1-FFFFF01', 'mcMemberCode01', 'aaaa','key01', 'cardstay', 'status_on', sysdate, sysdate);
