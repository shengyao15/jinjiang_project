delete from T_VBP_SCORE_TRADE_ORDER;
delete from MEMBER_VERIFY where id =222222;
DELETE FROM MEMBER_MEM_INFO WHERE ID=610086;

INSERT INTO MEMBER_MEM_INFO(ID,MEM_ID,NAME,TITLE,EMAIL,PHONE,MEM_NUM,CARD_NO,MEM_TYPE,MEM_TIER,STATUS,ACTIVATE_CODE,ENROLL_DATE,CARD_LEVEL,IDENTITY_TYPE,IDENTITY_NO,MC_MEMBER_CODE) 
VALUES(610086,'1-XZ2-6','MEMTEST','MR.','2@test.com','999','1-18120148','H123898973','INDIVIDUAL','1','ACTIVE','Mobile Activiated',SYSDATE,1,'OTHERS','55555','882200');

insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD)
values(222222,610086, '1-XZ2-110', '1-18121235','16578656666','E10ADC3949BA59ABBE56E057F20F883E');


  INSERT INTO T_VBP_SCORE_TRADE_ORDER
		      (
		        ID,
		        ORDER_NO,
		        TRD_ORDER_NO,
		        CREATE_TIME,
		        MC_MEMBER_CODE,
		        PRODUCT_ID,
		        TRADE_SCORE,
		        STATUS,
		        PRODUCT_NAME,
		        EXCHANGE_TYPE,
		        TRANS_ID,
		        REMAIN_POINT,
		        LAST_UPDATE_TIME
		      ) values (
		        S_T_VBP_SCORE_TRADE_ORDER.NEXTVAL,
		        '1234A',
		        '123456A',
		        '2013-07-08',
		        '12345',
		        '1-HDGFTJK',
		        '30000',
		        'BLOCK',
		        'test',
		        'mallScoreExchange',
		        '12146900',
		        '12131',
		         TIMESTAMP '2011-09-16 00:00:00.000000'
		      );


  INSERT INTO T_VBP_SCORE_TRADE_ORDER
		      (
		        ID,
		        ORDER_NO,
		        TRD_ORDER_NO,
		        CREATE_TIME,
		        MC_MEMBER_CODE,
		        PRODUCT_ID,
		        TRADE_SCORE,
		        STATUS,
		        PRODUCT_NAME,
		        EXCHANGE_TYPE,
		        TRANS_ID,
		        REMAIN_POINT,
		        LAST_UPDATE_TIME
		      )
		    values
		      (
		       S_T_VBP_SCORE_TRADE_ORDER.NEXTVAL,
		        '1234W',
		        '123456W',
		        '2013-07-08',
		        '12345W',
		        '1-HDGFTJKW',
		        '30000',
		        'SUCCESS',
		        'testW',
		        'mallScoreExchange',
		        '12146900',
		        '12131',
		         TIMESTAMP '2011-09-16 00:00:00.000000'
		      );


  INSERT INTO
		      T_VBP_SCORE_TRADE_ORDER
		      (
		        ID,
		        ORDER_NO,
		        TRD_ORDER_NO,
		        CREATE_TIME,
		        MC_MEMBER_CODE,
		        PRODUCT_ID,
		        TRADE_SCORE,
		        STATUS,
		        PRODUCT_NAME,
		        EXCHANGE_TYPE,
		        TRANS_ID,
		        REMAIN_POINT,
		        LAST_UPDATE_TIME
		      )
		    values
		      (
		       S_T_VBP_SCORE_TRADE_ORDER.NEXTVAL,
		        '1234Q',
		        '123456Q',
		        '2013-07-08',
		        '12345Q',
		        '1-HDGFTJKQ',
		        '30000',
		        'FAIL',
		        'test',
		        'mallScoreExchange',
		        '12146900Q',
		        '12131',
		         TIMESTAMP '2011-09-16 00:00:00.000000'
		      );

