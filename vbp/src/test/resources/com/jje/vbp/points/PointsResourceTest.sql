insert into T_VBP_MEMBER_POINTS (
NAME,
MEM_NUM,
CARD_NUM,
PER_TITLE,
EMAIL,
TIER,
HIGHEST_TYPE,
POINTS,
POINT_EXPIRY_DT,
REDEEMED_POINTS,
TIER_UP_POINTS,
TIER_UP_TIMES,
TO_DATE,
CHANGED_POINTS,
AQUIRED_POINTS
) values(
'jianglai',
'1-1',
'31002222',
'm',
'ccjr.tti@qq.com',
'1',
'xxx',
999,
'2016',
40,
4654,
46514,
sysdate,
998,
9988
);

insert into T_VBP_MEMBER_POINTS (
NAME,
MEM_NUM,
CARD_NUM,
PER_TITLE,
EMAIL,
TIER,
HIGHEST_TYPE,
POINTS,
POINT_EXPIRY_DT,
REDEEMED_POINTS,
TIER_UP_POINTS,
TIER_UP_TIMES,
TO_DATE,
CHANGED_POINTS,
AQUIRED_POINTS
) values(
'jianglai',
'10086',
'31002222',
'm',
'ccjr.tti@qq.com',
'1',
'xxx',
999,
'2016',
40,
4654,
46514,
sysdate,
998,
9988
);

insert into MEMBER_MEM_INFO(mem_id,name,title,email,phone,mem_num,card_no,mem_type,mem_tier,status,activate_code,id,ENROLL_DATE,CARD_LEVEL,IDENTITY_TYPE,IDENTITY_NO,MC_MEMBER_CODE)
values('1-1','zhangshan','Mr.','','1234564545','987654','CARD_NO1232','Individual','1','Active','Not Activiate',987654,sysdate,1,'Others','987654','987654');

insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD) values(987654,987654, '1-1', '2-2','member_name@jack.com','E10ADC3949BA59ABBE56E057F20F883E');




insert into member_mem_info (ID, MEM_ID, MEM_NUM, ACTIVATE_CODE, NAME, TITLE, IDENTITY_TYPE, IDENTITY_NO, EMAIL, PHONE, CARD_NO, MEM_TYPE, MEM_TIER, ENROLL_CHANNEL, ENROLL_DATE, QUESTION, ANSWER, STATUS, CARD_LEVEL, LAST_UPD, CRM_KEY, DT_STATUS, DT_MSG, DT_UPD, MC_MEMBER_CODE, IP_ADDRESS, ACTIVE_DATE, ACTIVE_CHANNEL, THIRDPARTY_TYPE, M_CUSTOMER_ID)
values (3577, '1-11W9-745', '6184577', 'Mobile Activiated', '黃維哲', null, 'Taiwan', '03750', 'xuejiehua@qq.com', '308', '1134569366', 'Individual', '1', null, sysdate, null, null, 'Active', '1', sysdate, '168285', '00', null, sysdate, '11604', null, sysdate, 'Website', null, null);

insert into member_verify (ID, MEM_INFO_ID, MEM_ID, MEM_NUM, MEN_NAME, PASSWORD)
values (12999, 3577, '1-11W9-745', '6184577', '1134569366', '713F62FD829A54EB3B4BB4835A4336C3');

insert into member_verify (ID, MEM_INFO_ID, MEM_ID, MEM_NUM, MEN_NAME, PASSWORD)
values (13000, 3577, '1-11W9-745', '6184577', '308', '713F62FD829A54EB3B4BB4835A4336C3');

insert into member_verify (ID, MEM_INFO_ID, MEM_ID, MEM_NUM, MEN_NAME, PASSWORD)
values (13001, 3577, '1-11W9-745', '6184577', 'xuejiehua@qq.com', '713F62FD829A54EB3B4BB4835A4336C3');



insert into t_vbp_member_points (NAME, MEM_NUM, CARD_NUM, PER_TITLE, EMAIL, TIER, HIGHEST_TYPE, POINTS, POINT_EXPIRY_DT, REDEEMED_POINTS, TIER_UP_POINTS, TIER_UP_TIMES, TO_DATE, CHANGED_POINTS, AQUIRED_POINTS)
values ('黃維哲', '987654', '1134569366', null, null, '经典卡', '暂无', 0, null, 0, 30000, 30, sysdate, 0, 0);