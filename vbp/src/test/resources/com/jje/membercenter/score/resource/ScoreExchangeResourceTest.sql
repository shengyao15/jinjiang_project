DELETE FROM MEMBER_MEM_INFO WHERE ID=10086;
delete from MEMBER_VERIFY where id =222222;

INSERT INTO MEMBER_MEM_INFO(ID,MEM_ID,NAME,TITLE,EMAIL,PHONE,MEM_NUM,CARD_NO,MEM_TYPE,MEM_TIER,STATUS,ACTIVATE_CODE,ENROLL_DATE,CARD_LEVEL,IDENTITY_TYPE,IDENTITY_NO,MC_MEMBER_CODE) 
VALUES(10086,'1-XZ2-6','MEMTEST','MR.','2@test.com','999','1-18120148','H123898973','INDIVIDUAL','1','ACTIVE','Mobile Activiated',SYSDATE,1,'OTHERS','55555','882200');

insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD)
values(2222222,10086, '1-XZ2-110', '1-18121235','16578656666','E10ADC3949BA59ABBE56E057F20F883E');
