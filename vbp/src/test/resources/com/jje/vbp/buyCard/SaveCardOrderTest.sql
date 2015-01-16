delete from MEMBER_MEM_INFO where id =22222222;
delete from MEMBER_VERIFY where id=100;

insert into MEMBER_MEM_INFO
(id
mem_id,
name,
title,
email,
phone,
mem_num,
card_no,
mem_type,
mem_tier,
status,
activate_code,
id,
ENROLL_DATE,
CARD_LEVEL,
IDENTITY_TYPE,
IDENTITY_NO,
MC_MEMBER_CODE) 
values(22222222,'1-XZ2-6',
'memtest','mositz.','mositz@163.com','19112855454','1-18120148','1157866002','Individual','1','Active','Not Activiate',4331,sysdate,1,'Others','12345','11275');

insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD) 
values(100,22222222, '1-XZ2-2', '1157863308','mositz@163.com','E10ADC3949BA59ABBE56E057F20F883E');

