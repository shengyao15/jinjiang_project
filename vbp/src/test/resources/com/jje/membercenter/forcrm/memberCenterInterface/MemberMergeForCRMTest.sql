delete  MEMBER_VERIFY where id in (1277777,1277778,1277779,1277787,1277788,1277789);
delete  MEMBER_MEM_CARD where id in (3500045111,250004511,350004511);
delete  MEMBER_MEM_INFO where id in (3500045111,250004511,350004511);

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
'2-Y1NHQ',
'evan.du1-1',
'Mr.',
'evan1-1@jinjiang.com',
'1-13524101988',
'3-B57184046',
'7766664567',
'Individual',
'1',
'Activiate',
'Not Activiate',
3500045111,
sysdate,
1,
'Others',
'4123131978hhddd',
'10783');

insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD)
values(1277777,3500045111, '2-Y1NHQ', '3-B57184046','1-13524101988','E10ADC3949BA59ABBE56E057F20F883E');
insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD)
values(1277778,3500045111, '2-Y1NHQ', '3-B57184046','evan.du1-1@jinjiang.com','E10ADC3949BA59ABBE56E057F20F883E');
insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD)
values(1277779,3500045111, '2-Y1NHQ', '3-B57184046','7766664567','E10ADC3949BA59ABBE56E057F20F883E');

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
MC_MEMBER_CODE)
values('3-Y1NHQ',
'evan1-2.du',
'Mr.',
'evan1-2.du@jinjiang.com',
'2-13524101988',
'3-A57184046',
'8766664567',
'Individual',
'1',
'Active',
'Not Activiate',
250004511,
sysdate,
1,
'Others',
'34123131978hhd',
'20783');


insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD)
values(1277787,250004511, '3-Y1NHQ', '3-A57184046','8766664567','E10ADC3949BA59ABBE56E057F20F883E');
insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD)
values(1277788,250004511, '3-Y1NHQ', '3-A57184046','evan1-2.du@jinjiang.com','E10ADC3949BA59ABBE56E057F20F883E');
insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD)
values(1277789,250004511, '3-Y1NHQ', '3-A57184046','2-13524101988','E10ADC3949BA59ABBE56E057F20F883E');


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
MC_MEMBER_CODE)
values('4-Y1NHQ',
'evan1-2.du',
'Mr.',
'evan1-5.du@jinjiang.com',
'4-13524101988',
'4-A57184046',
'8966664567',
'Individual',
'1',
'Active',
'Not Activiate',
350004511,
sysdate,
1,
'Others',
'3412313ss1978hhd',
'30783');
