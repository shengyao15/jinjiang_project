delete from webmember_info;
delete from webmember_verify;

insert into webmember_info (ID, EMAIL, PHONE, TEMP_CARD_NO, MEM_NUM, LAST_UPDATE_TIME, LAST_LOGIN_TIME, REFERRER_CARD_NO, IS_MOBILE_BIND, IS_EMAIL_BIND, REGIST_CHANNEL, REGIST_TIME, ACTIVITY_CODE, QUESTION, ANSWER, MC_MEMBER_CODE, MEM_TYPE, IP_ADDRESS, TRANSFORM_TYPE, REGIST_TAG)
values (11670, 'sb16@sb.sb', null, 'T00002070073', null,sysdate, null, null, 0, 0, 'Website', sysdate, null, null, null, '18505', 'QUICK_REGIST', '192.168.1.119', 'UNKNOWN', null);



insert into webmember_verify (ID, MEM_INFO_ID, MEN_NAME, PASSWORD)
values (13355, 11670, 'sb16@sb.sb', 'E10ADC3949BA59ABBE56E057F20F883E');

insert into webmember_verify (ID, MEM_INFO_ID, MEN_NAME, PASSWORD)
values (13356, 11670, 'T00002070073', 'E10ADC3949BA59ABBE56E057F20F883E');
