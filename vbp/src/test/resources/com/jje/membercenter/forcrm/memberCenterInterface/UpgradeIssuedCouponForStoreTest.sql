delete  MEMBER_VERIFY where id in (12777771);
delete  MEMBER_MEM_INFO where id in (15000451);
insert into MEMBER_MEM_INFO(
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
MC_MEMBER_CODE ) values (
'3-201201121',
'evan.du1-12121',
'Mr.',
'evan1-d222@jinjiang.com',
'C1-13524101988',
'3-FF57184046',
'7d766664567',
'Individual',
'1',
'Activiate',
'Not Activiate',
15000451,
sysdate,
1,
'Others',
'4123131978hhddd',
'10783');
insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD)
values(12777771,15000451, '3-201201121', '3-FF57184046','1C-13524101988','E10ADC3949BA59ABBE56E057F20F883E');

