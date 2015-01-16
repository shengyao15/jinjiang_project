delete from t_vbp_member_score_level_info s where s.member_info_id in (321, 322);
delete from MEMBER_MEM_CARD s where s.MEM_INFO_ID in (321, 322);
delete from member_verify v where v.MEM_INFO_ID in (321,322);
delete from member_mem_info i where i.id = 321;
delete from member_mem_info i where i.id = 322;


insert into member_mem_info (ID, MEM_ID, MEM_NUM, ACTIVATE_CODE, NAME, TITLE, IDENTITY_TYPE, IDENTITY_NO, EMAIL, PHONE, CARD_NO, MEM_TYPE, MEM_TIER, ENROLL_CHANNEL, ENROLL_DATE, QUESTION, ANSWER, STATUS, CARD_LEVEL, LAST_UPD, CRM_KEY, DT_STATUS, DT_MSG, DT_UPD, MC_MEMBER_CODE, IP_ADDRESS)
values (321, '1-TDHVL', '1-49338561', 'Activiated', 'xixhaosdfdei', '', 'Passport', '1235dsdfdfdf', 'wiwifdddddddf@123.com', '17542312655', '7700017727', 'Individual', '1', 'Ikamobile', TIMESTAMP '2012-08-03', '', '', 'Active', '1', TIMESTAMP '2012-08-16 15:36:55', '', '', '', null, '10082', '');


insert into member_mem_info (ID, MEM_ID, MEM_NUM, ACTIVATE_CODE, NAME, TITLE, IDENTITY_TYPE, IDENTITY_NO, EMAIL, PHONE, CARD_NO, MEM_TYPE, MEM_TIER, ENROLL_CHANNEL, ENROLL_DATE, QUESTION, ANSWER, STATUS, CARD_LEVEL, LAST_UPD, CRM_KEY, DT_STATUS, DT_MSG, DT_UPD, MC_MEMBER_CODE, IP_ADDRESS)
values (322, '1-TDHVV', '1-49338562', 'Activiated', 'xixhaosdfdei', '', 'Passport', '1235dsdfdfdf', 'wiwifdddddddf@123.com', '17542312655', '7700017728', 'Individual', '1', 'Ikamobile', TIMESTAMP '2012-08-03', '', '', 'Active', '1', TIMESTAMP '2012-08-16 15:36:55', '', '', '', null, '10083', '');


insert into t_vbp_member_score_level_info (ID, MEMBER_INFO_ID, MEM_NUM, AVAILABLE_SCORE, RANK_SCORE, RANK_TIME_SIZE, UPDATED_DATE, SCORE_LEVEL, SCORE_MEMEBER_LEVEL_INDATE, SCORE_TYPE, CRM_KEY, DT_STATUS, DT_MSG, DT_UPD)
values (10011, 321, '1-49338561', 999999999, 0, 0, sysdate, 2, null, '0', '', '', '', null);

insert into t_vbp_member_score_level_info (ID, MEMBER_INFO_ID, MEM_NUM, AVAILABLE_SCORE, RANK_SCORE, RANK_TIME_SIZE, UPDATED_DATE, SCORE_LEVEL, SCORE_MEMEBER_LEVEL_INDATE, SCORE_TYPE, CRM_KEY, DT_STATUS, DT_MSG, DT_UPD)
values (10012, 322, '1-49338562', 999999999, 0, 0, sysdate, 1, null, '0', '', '', '', null);


insert into MEMBER_MEM_CARD(ID,MEM_INFO_ID,MEM_ID,CARD_TYPE_CD,X_CARD_NUM,SOURCE,VALID_DATE,DUE_DATE,STATUS,CRM_KEY,DT_STATUS,DT_MSG,DT_UPD) 
values(1201,321, '1-TDHVL','JJ Card', '17542312655', 'JJ001', TIMESTAMP '2010-5-16 0:00:00.000000', null, 'Sent',  '1','1','1', sysdate    );
insert into MEMBER_MEM_CARD(ID,MEM_INFO_ID,MEM_ID,CARD_TYPE_CD,X_CARD_NUM,SOURCE,VALID_DATE,DUE_DATE,STATUS,CRM_KEY,DT_STATUS,DT_MSG,DT_UPD) 
values(1202,321, '1-TDHVL','J Benefit Card', '11578633081', 'JJ001', TIMESTAMP '2011-5-19 0:00:00.000000',   TIMESTAMP '2013-5-19 0:00:00.000000', 'Sent',  '1','1','1', sysdate    );


