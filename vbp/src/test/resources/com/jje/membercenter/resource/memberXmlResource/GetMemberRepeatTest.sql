DELETE FROM MEMBER_VERIFY WHERE ID = 987654;
DELETE FROM MEMBER_MEM_INFO WHERE ID = 987654;

insert into MEMBER_MEM_INFO(mem_id,name,title,email,phone,mem_num,card_no,mem_type,mem_tier,status,activate_code,id,ENROLL_DATE,CARD_LEVEL,IDENTITY_TYPE,IDENTITY_NO,MC_MEMBER_CODE) 
values('1-1','zhangshan','Mr.','','1234564545','2-2','CARD_NO1232','Individual','1','Active','Not Activiate',987654,sysdate,1,'Others','987654','987654');

insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD) values(987654,987654, '1-1', '2-2','member_name@jack.com','E10ADC3949BA59ABBE56E057F20F883E');