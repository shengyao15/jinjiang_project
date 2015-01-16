delete from member_verify v where v.id in (1100000, 1000000);
delete from MEMBER_MEM_CARD c where c.MEM_INFO_ID in (321,322);
delete from member_mem_info i where i.id in (321, 322);



insert into member_mem_info (ID, MEM_ID, MEM_NUM, ACTIVATE_CODE, NAME, TITLE, IDENTITY_TYPE, IDENTITY_NO, EMAIL, PHONE, CARD_NO, MEM_TYPE, MEM_TIER, ENROLL_CHANNEL, ENROLL_DATE, QUESTION, ANSWER, STATUS, CARD_LEVEL, LAST_UPD, CRM_KEY, DT_STATUS, DT_MSG, DT_UPD, MC_MEMBER_CODE, IP_ADDRESS)
values (321, '1-TDHVL', '1-49338561', 'Activiated', 'xixhaosdfdei', 'Mr.', 'Passport', '1235dsdfdfdf', 'wiwifdddddddf@123.com', '17542312655', '7700017727', 'Individual', '1', 'Ikamobile', TIMESTAMP '2012-08-03', '', '', 'Active', '1', TIMESTAMP '2012-08-16 15:36:55', '', '', '', null, '10082', '');


insert into member_mem_info (ID, MEM_ID, MEM_NUM, ACTIVATE_CODE, NAME, TITLE, IDENTITY_TYPE, IDENTITY_NO, EMAIL, PHONE, CARD_NO, MEM_TYPE, MEM_TIER, ENROLL_CHANNEL, ENROLL_DATE, QUESTION, ANSWER, STATUS, CARD_LEVEL, LAST_UPD, CRM_KEY, DT_STATUS, DT_MSG, DT_UPD, MC_MEMBER_CODE, IP_ADDRESS)
values (322, '1-TDHVV', '1-49338562', 'Activiated', 'xixhaosdfdei', 'Mr.', 'Passport', '1235dsdfdfdf', 'wiwifdddddddf@123.com', '17542312655', '7700017728', 'Individual', '1', 'Ikamobile', TIMESTAMP '2012-08-03', '', '', 'Active', '1', TIMESTAMP '2012-08-16 15:36:55', '', '', '', null, '10083', '');



insert into member_verify (ID, MEM_INFO_ID, MEM_ID, MEM_NUM, MEN_NAME, PASSWORD)
values (1100000, 321, '1-TDHVL', '1-49338561', '7700017427', 'E10ADC3949BA59ABBE56E057F20F883E');

insert into member_verify (ID, MEM_INFO_ID, MEM_ID, MEM_NUM, MEN_NAME, PASSWORD)
values (1000000, 322, '1-TDHVV', '1-49338562', '12000000041', 'E10ADC3949BA59ABBE56E057F20F883E');
