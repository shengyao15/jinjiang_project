DELETE FROM WEBMEMBER_INFO WHERE ID in (12345,12346);
DELETE FROM WEBMEMBER_VERIFY WHERE ID IN (1000,1001,1002);
DELETE FROM MEMBER_MEM_CARD WHERE ID IN (12345);
DELETE FROM MEMBER_MEM_INFO WHERE ID IN (123456);
delete from MEMBER_VERIFY where id =22222222;
delete from MEMBER_VERIFY where id =22222221;
delete from MEMBER_MEM_CARD where id =22222222;
delete from MEMBER_MEM_INFO where id =22222222;


insert into MEMBER_MEM_INFO(id,mem_id,name,title,email,phone,mem_num,card_no,mem_type,mem_tier,status,activate_code,ENROLL_DATE,CARD_LEVEL,IDENTITY_TYPE,IDENTITY_NO,MC_MEMBER_CODE) 
values(22222221,'1-XZ2-110','张三','Mr.','xiaoJJ22@126.com','16578656664','1-18121235','9157863308','TmpMem','1','Not Activiate','Not Activiate',sysdate,1,'Others','112312111','3231663100');

insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD)
values(22222221,22222221, '1-XZ2-110', '1-18121234','16578656664','E10ADC3949BA59ABBE56E057F20F883E');


insert into MEMBER_MEM_INFO(id,mem_id,name,title,email,phone,mem_num,card_no,mem_type,mem_tier,status,activate_code,ENROLL_DATE,CARD_LEVEL,IDENTITY_TYPE,IDENTITY_NO,MC_MEMBER_CODE) 
values(12345,'1-XZ2-110','张三','Mr.','xiaoJJ@126.com','1383838238','1-18121235','9157863308','Individual','1','Not Activiate','Not Activiate',sysdate,1,'Others','112312111','12345');

insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD)
values(12345,12345, '1-XZ2-110', '1-18121235','16578656666','E10ADC3949BA59ABBE56E057F20F883E');


insert into WEBMEMBER_INFO (ID, EMAIL, PHONE, TEMP_CARD_NO, MEM_NUM, LAST_UPDATE_TIME, LAST_LOGIN_TIME, REFERRER_CARD_NO, IS_MOBILE_BIND, IS_EMAIL_BIND, REGIST_CHANNEL, REGIST_TIME, ACTIVITY_CODE,QUESTION,ANSWER,MEM_TYPE,MC_MEMBER_CODE)
values (12345, 'test@jinjiang.com', '1301234578', 'H12345', '', TIMESTAMP '2011-5-16 0:00:00.000000', TIMESTAMP '2011-5-16 0:00:00.000000', '', 0, 0, 'Website', TIMESTAMP '2011-5-16 0:00:00.000000', '','Safe001','123456','QUICK_REGIST','12345');


INSERT INTO WEBMEMBER_VERIFY ( ID, MEM_INFO_ID, MEN_NAME, PASSWORD ) VALUES ( 1000, 12345, 'test@jinjiang.com', '123456' ) ;
INSERT INTO WEBMEMBER_VERIFY ( ID, MEM_INFO_ID, MEN_NAME, PASSWORD ) VALUES ( 1001, 12345, '1301234578', '123456' ) ;

insert into MEMBER_MEM_INFO(mem_id,name,title,email,phone,mem_num,card_no,mem_type,mem_tier,status,activate_code,id,ENROLL_DATE,CARD_LEVEL,IDENTITY_TYPE,IDENTITY_NO,MC_MEMBER_CODE) 
values('1-XZ2-622','memtest','Mr.','whatfuck@jinjiang.com','19112855454','1-18120148','1157866002','Individual','1','Active','Moblie Activiate',123456,sysdate,1,'ID','55555','789456');

insert into MEMBER_MEM_CARD(ID,MEM_INFO_ID,MEM_ID,CARD_TYPE_CD,X_CARD_NUM,SOURCE,VALID_DATE,DUE_DATE,STATUS,CRM_KEY,DT_STATUS,DT_MSG,DT_UPD) 
values(12345,123456, '1-XZ2-622','JJ Card', '115786330811', 'JJ001', TIMESTAMP '2010-5-16 0:00:00.000000', null, 'Sent',  '1','1','1', sysdate    );


insert into WEBMEMBER_INFO (ID, EMAIL, PHONE, TEMP_CARD_NO, MEM_NUM, LAST_UPDATE_TIME, LAST_LOGIN_TIME, REFERRER_CARD_NO, IS_MOBILE_BIND, IS_EMAIL_BIND, REGIST_CHANNEL, REGIST_TIME, ACTIVITY_CODE,QUESTION,ANSWER,MEM_TYPE,MC_MEMBER_CODE)
values (12346, 'test121312@jinjiang.com', null, 'H12345643', '', TIMESTAMP '2011-5-16 0:00:00.000000', TIMESTAMP '2011-5-16 0:00:00.000000', '', 0, 0, 'Website', TIMESTAMP '2011-5-16 0:00:00.000000', '','Safe001','123456','QUICK_REGIST','789456');


INSERT INTO WEBMEMBER_VERIFY ( ID, MEM_INFO_ID, MEN_NAME, PASSWORD ) VALUES ( 1002, 12346, 'test121312@jinjiang.com', '123456' ) ;