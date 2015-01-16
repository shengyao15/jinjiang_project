delete from WEBMEMBER_INFO where id in ('100080');
delete from MEMBER_MEM_INFO where id in ('10080','10081');

insert into WEBMEMBER_INFO (ID, EMAIL, PHONE, TEMP_CARD_NO, MEM_NUM, LAST_UPDATE_TIME, LAST_LOGIN_TIME, REFERRER_CARD_NO, IS_MOBILE_BIND, IS_EMAIL_BIND, REGIST_CHANNEL, REGIST_TIME, ACTIVITY_CODE,QUESTION,ANSWER,MEM_TYPE,MC_MEMBER_CODE)
values (100080, 'lily_i@126.com', '15900916061', 'H2068640103', '', TIMESTAMP '2011-5-16 0:00:00.000000', TIMESTAMP '2011-5-16 0:00:00.000000', '', 0, 0, 'Website', TIMESTAMP '2011-5-16 0:00:00.000000', '','Safe001','123456','QUICK_REGIST','112');

insert into MEMBER_MEM_INFO(mem_id,name,title,email,phone,mem_num,card_no,mem_type,mem_tier,status,activate_code,id,ENROLL_DATE,CARD_LEVEL,IDENTITY_TYPE,IDENTITY_NO,MC_MEMBER_CODE) 
values('1-XZ2-6','memtest','Mr.','whatfuck@jinjiang.com','15900916061','1-18120148','11578660ddd02','Individual','1','Active','Not Activiate',10080,sysdate,1,'Others','55555',null);
insert into MEMBER_MEM_INFO(mem_id,name,title,email,phone,mem_num,card_no,mem_type,mem_tier,status,activate_code,id,ENROLL_DATE,CARD_LEVEL,IDENTITY_TYPE,IDENTITY_NO,MC_MEMBER_CODE) 
values('1-EZ4C9','陈勇','Mr.','chenyongne@126.com','15900916061','1-19056406','11578633d08','QUICK_REGIST','1','Active','Activiate',10081,sysdate,1,'Others','11111','123');
