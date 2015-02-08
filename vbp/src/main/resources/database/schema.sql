DROP TABLE T_CX_MEM_VERIFY CASCADE CONSTRAINTS;
DROP TABLE T_MEMBER_CARD_ORDER CASCADE CONSTRAINTS;
DROP TABLE T_MEMBER_CARD_ORDER_HISTORY CASCADE CONSTRAINTS;
DROP TABLE S_LST_OF_VAL CASCADE CONSTRAINTS;
DROP TABLE T_MEMBER_COUPON CASCADE CONSTRAINTS;
DROP TABLE T_MEMBER_SCORE_REDEEM CASCADE CONSTRAINTS;
DROP TABLE T_MEMBER_XML CASCADE CONSTRAINTS;
DROP TABLE T_MEMBER_ORDER_NOTE CASCADE CONSTRAINTS;
DROP TABLE  MEMBER_MEM_CARD CASCADE CONSTRAINTS;
DROP TABLE  MEMBER_MEM_INFO  CASCADE CONSTRAINTS;
DROP TABLE  MEMBER_VERIFY  CASCADE CONSTRAINTS;
DROP TABLE T_VBP_NOTE CASCADE CONSTRAINTS;
DROP TABLE T_VBP_SCORE_ORDER CASCADE CONSTRAINTS;
DROP TABLE T_VBP_MEMBER_SCORE_LEVEL_INFO CASCADE CONSTRAINTS;
DROP TABLE T_DATA_CODE CASCADE CONSTRAINTS;
DROP TABLE WEBMEMBER_VERIFY CASCADE CONSTRAINTS;
DROP TABLE WEBMEMBER_INFO CASCADE CONSTRAINTS;
DROP TABLE T_VBP_REFERRER_INFO CASCADE CONSTRAINTS;
DROP TABLE T_VBP_LOTTERY_COIN CASCADE CONSTRAINTS;
DROP TABLE T_VBP_LOTTERY_COIN_RULE CASCADE CONSTRAINTS;
DROP TABLE T_VBP_LOTTERY_COIN_DETAIL CASCADE CONSTRAINTS;
DROP TABLE T_VBP_TARGET_PROMOTION CASCADE CONSTRAINTS;
DROP TABLE T_VBP_NOTARGET_PROMOTION CASCADE CONSTRAINTS;
DROP TABLE T_VBP_COMMONLY_USED_CONTACT CASCADE CONSTRAINTS;
DROP TABLE T_VBP_BIG_CUSTOMER_ACCOUNT CASCADE CONSTRAINTS;
DROP TABLE T_VBP_BIG_CUSTOMER CASCADE CONSTRAINTS;
DROP TABLE T_VBP_BIG_CUSTOMER_RATE CASCADE CONSTRAINTS;
DROP TABLE T_VBP_THIRDPARTY_BIND CASCADE CONSTRAINTS;
DROP TABLE T_VBP_MEMBER_POINTS CASCADE CONSTRAINTS; 
DROP TABLE T_VBP_SCORE_TRADE_LOG CASCADE CONSTRAINTS;
DROP TABLE T_VBP_SCORE_TRADE_ORDER CASCADE CONSTRAINTS;
DROP TABLE T_VBP_CONFIG CASCADE CONSTRAINTS;
DROP TABLE T_VBP_MEMBER_FEEDBACK CASCADE CONSTRAINTS;
DROP TABLE T_VBP_MEMBER_SUB_FEEDBACK CASCADE CONSTRAINTS;
DROP TABLE T_VBP_BIG_CUSTOMER_MIDDLE CASCADE CONSTRAINTS;
DROP TABLE T_VBP_OP_RECORD_LOG CASCADE CONSTRAINTS;
DROP TABLE T_VBP_VALIDATE_CODE_LOG CASCADE CONSTRAINTS;
DROP TABLE T_VBP_VAILDATE_CODE CASCADE CONSTRAINTS;
DROP TABLE T_VBP_MEMBER_CONTACTSNS CASCADE CONSTRAINTS;
drop table T_VBP_MEMBER_RECOMMEND cascade constraints;
drop table T_VBP_MEMBER_LEVEL_BENEFIT cascade constraints;
drop table T_VBP_MEMBER_TAOBAO_BINDING cascade constraints;

DROP SEQUENCE S_T_CX_MEM_VERIFY;
DROP SEQUENCE S_T_MEMBER_CARD_ORDER;
DROP SEQUENCE S_T_MEMBER_CARD_ORDER_HISTORY;
DROP SEQUENCE S_T_S_LST_OF_VAL;
DROP SEQUENCE S_T_MEMBER_COUPON;
DROP SEQUENCE S_T_MEMBER_SCORE_REDEEM;
DROP SEQUENCE S_T_MEMBER_XML;
DROP SEQUENCE S_T_MEMBER_ORDER_NOTE;
DROP SEQUENCE  SEQ_MEMBER_MEM_CARD;
DROP SEQUENCE SEQ_MEMBER_MEM_INFO;
DROP SEQUENCE  SEQ_MEMBER_VERIFY;
DROP SEQUENCE S_VBP_NOTE;
DROP SEQUENCE S_VBP_SCORE_ORDER;
DROP SEQUENCE S_VBP_MEMBER_SCORE_LEVEL_INFO;
DROP SEQUENCE S_DATA_CODE;
DROP SEQUENCE S_WEBMEMBER_VERIFY;
DROP SEQUENCE S_WEBMEMBER_INFO;
DROP SEQUENCE S_VBP_REFERRER_INFO;
DROP SEQUENCE S_VBP_LOTTERY_COIN;
DROP SEQUENCE S_VBP_LOTTERY_COIN_DETAIL;
DROP SEQUENCE S_MC_MEMBER_CODE;
DROP SEQUENCE S_TEMP_CARD_NUM;

DROP SEQUENCE S_VBP_TARGET_PROMOTION;
DROP SEQUENCE S_VBP_NOTARGET_PROMOTION;
DROP SEQUENCE S_VBP_COMMONLY_USED_CONTACT;
DROP SEQUENCE S_VBP_BIG_CUSTOMER_ACCOUNT;
DROP SEQUENCE S_VBP_BIG_CUSTOMER;
DROP SEQUENCE S_VBP_BIG_CUSTOMER_RATE;
DROP SEQUENCE S_VBP_THIRDPARTY_BIND;
DROP SEQUENCE S_T_VBP_SCORE_TRADE_LOG;
DROP SEQUENCE S_T_VBP_SCORE_TRADE_ORDER;
DROP SEQUENCE S_VBP_MEMBER_POINTS;
DROP SEQUENCE S_VBP_CONFIG;
DROP SEQUENCE S_VBP_MEMBER_FEEDBACK;
DROP SEQUENCE S_VBP_MEMBER_SUB_FEEDBACK;
DROP SEQUENCE S_VBP_BIG_CUSTOMER_MIDDLE;
DROP SEQUENCE S_VBP_OP_RECORD_LOG;
DROP SEQUENCE S_VBP_VAILDATE_CODE;
DROP SEQUENCE S_VBP_VALIDATE_CODE_LOG;
DROP SEQUENCE S_VBP_MEMBER_CONTACTSNS;
drop sequence S_VBP_MEMBER_RECOMMEND;
drop sequence S_VBP_MEMBER_TAOBAO_BINDING;

CREATE SEQUENCE S_T_MEMBER_SCORE_REDEEM  INCREMENT BY 1   START WITH 1;
CREATE SEQUENCE S_T_MEMBER_COUPON INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE S_T_S_LST_OF_VAL INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE S_T_CX_MEM_VERIFY INCREMENT BY 1  START WITH 4;
CREATE SEQUENCE S_T_MEMBER_CARD_ORDER INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE S_T_MEMBER_CARD_ORDER_HISTORY INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE S_T_MEMBER_XML INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE S_T_MEMBER_ORDER_NOTE INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_MEMBER_MEM_CARD INCREMENT BY 1 START WITH 100;
CREATE SEQUENCE SEQ_MEMBER_MEM_INFO INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_MEMBER_VERIFY INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE S_VBP_NOTE INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_VBP_SCORE_ORDER INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_VBP_MEMBER_SCORE_LEVEL_INFO INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_DATA_CODE INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_WEBMEMBER_VERIFY INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_WEBMEMBER_INFO INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_VBP_REFERRER_INFO INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_VBP_LOTTERY_COIN INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_VBP_LOTTERY_COIN_DETAIL INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_MC_MEMBER_CODE INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_TEMP_CARD_NUM INCREMENT BY 1 START WITH 400000;

CREATE SEQUENCE S_VBP_TARGET_PROMOTION INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_VBP_NOTARGET_PROMOTION INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_VBP_COMMONLY_USED_CONTACT INCREMENT BY 1 START WITH 10000;

CREATE SEQUENCE S_VBP_BIG_CUSTOMER_ACCOUNT INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_VBP_BIG_CUSTOMER INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_VBP_BIG_CUSTOMER_RATE INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_VBP_THIRDPARTY_BIND START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE S_T_VBP_SCORE_TRADE_LOG INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_T_VBP_SCORE_TRADE_ORDER INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_VBP_MEMBER_POINTS START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE S_VBP_CONFIG START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE S_VBP_MEMBER_FEEDBACK START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE S_VBP_MEMBER_SUB_FEEDBACK START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE S_VBP_BIG_CUSTOMER_MIDDLE START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE S_VBP_OP_RECORD_LOG START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE S_VBP_VALIDATE_CODE_LOG START WITH 10000 INCREMENT BY 1;
CREATE SEQUENCE S_VBP_VAILDATE_CODE  INCREMENT BY 1   START WITH 1;
CREATE SEQUENCE S_VBP_MEMBER_CONTACTSNS  INCREMENT BY 1   START WITH 1;

CREATE TABLE T_CX_MEM_VERIFY
(
   ROW_ID               VARCHAR2 (15 CHAR) NOT NULL,
   MEM_NAME             VARCHAR2 (50 CHAR),
   NAME                 VARCHAR2 (50 CHAR),
   EMAIL                VARCHAR2 (100 CHAR),
   PHONE_NUM            VARCHAR2 (40 CHAR),
   MEM_NUM              VARCHAR2 (20 CHAR),
   MEM_CARD_NUM         VARCHAR2 (30 CHAR),
   MEM_CARD_TYPE        VARCHAR2 (30 CHAR),
   MEM_CARD_TIER        VARCHAR2 (30 CHAR),
   MEM_PASSWORD         VARCHAR2 (100 CHAR),
   MEM_TYPE             VARCHAR2 (30 CHAR),
   MEM_TIER             VARCHAR2 (30 CHAR),
   MEM_ENROLL_CHANNEL   VARCHAR2 (30 CHAR),
   ENROLL_DATE          DATE,
   QUESTION             VARCHAR2 (100 CHAR),
   ANSWER               VARCHAR2 (100 CHAR),
   MEM_ID               VARCHAR2 (15 CHAR),
   STATUS               VARCHAR2 (15 CHAR),
  CONSTRAINT PK_CX_MEM_VERIFY PRIMARY KEY (ROW_ID)
);

CREATE TABLE T_MEMBER_CARD_ORDER
(
   ID               NUMBER NOT NULL,
   ORDER_NO         VARCHAR2 (15),
   ORDER_TIME       DATE,
   CREATE_TIME      DATE,
   CARD_NO          VARCHAR2 (100),
   ORDER_TYPE       VARCHAR2 (15),
   CURRENT_LEVEL    VARCHAR2 (50),
   NEXT_LEVEL       VARCHAR2 (50),
   AMOUNT           NUMBER,
   PAY_TIME         DATE,
   PAYMENT_NO       VARCHAR2 (100),
   PAY_TYPE         VARCHAR2 (50),
   PAYMENT_VENDER   VARCHAR2 (50),
   BANK_CODE        VARCHAR2 (50),
   PAY_STATUS       NUMBER,
   STATUS           NUMBER,
   REFUND_AMOUNT    NUMBER DEFAULT 0 NOT NULL,
   MC_MEMBER_CODE   VARCHAR2(32),
   CONSTRAINT T_MEMBER_CARD_ORDER PRIMARY KEY (ID)
);

CREATE TABLE T_MEMBER_CARD_ORDER_HISTORY
(
   LOG_ID           NUMBER NOT NULL,
   ORDER_ID         NUMBER NOT NULL,
   ORDER_NO         VARCHAR2 (15),
   ORDER_TIME       DATE,
   CREATE_TIME      DATE,
   CARD_NO          VARCHAR2 (100),
   ORDER_TYPE       VARCHAR2 (15),
   CURRENT_LEVEL    VARCHAR2 (50),
   NEXT_LEVEL       VARCHAR2 (50),
   AMOUNT           NUMBER,
   PAY_TIME         DATE,
   PAYMENT_NO       VARCHAR2 (100),
   PAY_TYPE         VARCHAR2 (50),
   PAYMENT_VENDER   VARCHAR2 (50),
   BANK_CODE        VARCHAR2 (50),
   PAY_STATUS       NUMBER,
   STATUS           NUMBER,
   REFUND_AMOUNT    NUMBER DEFAULT 0 NOT NULL,
   SALE_CHANNEL 		VARCHAR2(30),
   MC_MEMBER_CODE   VARCHAR2(32),
   OPERATION_TYPE 	VARCHAR2(32),
   OPERATION_TIME 	DATE DEFAULT SYSDATE NOT NULL,
   REMARK						VARCHAR2(2000),
   CONSTRAINT T_MEMBER_CARD_ORDER_HISTORY PRIMARY KEY (LOG_ID)
);


CREATE TABLE S_LST_OF_VAL
(
   ROW_ID              VARCHAR2 (15 CHAR) NOT NULL,
   CREATED             DATE DEFAULT SYSDATE NOT NULL,
   CREATED_BY          VARCHAR2 (15 CHAR) NOT NULL,
   LAST_UPD            DATE DEFAULT SYSDATE NOT NULL,
   LAST_UPD_BY         VARCHAR2 (15 CHAR) NOT NULL,
   DCKING_NUM          NUMBER (22, 7) DEFAULT 0,
   MODIFICATION_NUM    NUMBER (10) DEFAULT 0 NOT NULL,
   CONFLICT_ID         VARCHAR2 (15 CHAR) DEFAULT '0' NOT NULL,
   PAR_ROW_ID          VARCHAR2 (15 CHAR),
   ACTIVE_FLG          CHAR (1 CHAR) DEFAULT 'Y' NOT NULL,
   DFLT_LIC_FLG        CHAR (1 CHAR) DEFAULT 'N' NOT NULL,
   LANG_ID             VARCHAR2 (15 CHAR) DEFAULT 'ENU' NOT NULL,
   MLTORG_DISALW_FLG   CHAR (1 CHAR) DEFAULT 'N' NOT NULL,
   MULTI_LINGUAL_FLG   CHAR (1 CHAR) DEFAULT 'N' NOT NULL,
   NAME                VARCHAR2 (50 CHAR) NOT NULL,
   REQD_LIC_FLG        CHAR (1 CHAR) DEFAULT 'N' NOT NULL,
   RPLCTN_LVL_CD       VARCHAR2 (30 CHAR) DEFAULT 'All' NOT NULL,
   TYPE                VARCHAR2 (30 CHAR) NOT NULL,
   VAL                 VARCHAR2 (30 CHAR) NOT NULL,
   MODIFIABLE_FLG      CHAR (1 CHAR) DEFAULT 'Y',
   DB_LAST_UPD         DATE,
   ORDER_BY            NUMBER (22, 7),
   TARGET_HIGH         NUMBER (22, 7),
   TARGET_LOW          NUMBER (22, 7),
   TRANSLATE_FLG       CHAR (1 CHAR),
   WEIGHTING_FACTOR    NUMBER (22, 7),
   BITMAP_ID           VARCHAR2 (15 CHAR),
   BU_ID               VARCHAR2 (15 CHAR),
   CODE                VARCHAR2 (30 CHAR),
   DB_LAST_UPD_SRC     VARCHAR2 (50 CHAR),
   DESC_TEXT           VARCHAR2 (255 CHAR),
   HIGH                VARCHAR2 (100 CHAR),
   INTEGRATION_ID      VARCHAR2 (30 CHAR),
   LOW                 VARCHAR2 (100 CHAR),
   SUB_TYPE            VARCHAR2 (30 CHAR),
   CONSTRAINT PK_S_LST_OF_VAL PRIMARY KEY (ROW_ID)
);


CREATE TABLE T_MEMBER_COUPON
(
   ID            NUMBER NOT NULL,
   MEMBER_ID     VARCHAR2 (50),
   USE_MODULE    VARCHAR2 (2),
   CREATOR_ID    VARCHAR2 (50),
   CREATE_TIME        DATE,
   COUPON_NO     VARCHAR2 (50),
   STATUS             NUMBER,
   CONSTRAINT PK_T_MEMBER_COUPON PRIMARY KEY (ID)
);

CREATE TABLE T_MEMBER_SCORE_REDEEM
(
   ID             NUMBER NOT NULL,
   ORDER_NO       VARCHAR2 (15),
   ORDER_TIME         DATE,
   CREATE_TIME        DATE,
   MEMBER_ID      VARCHAR2 (50),
   PRODUCT_NO     VARCHAR2 (50),
   REDEEM_SCORE       NUMBER,
   STATUS             NUMBER,
   CONSTRAINT T_MEMBER_SCORE_REDEEM PRIMARY KEY (ID)
);


CREATE TABLE T_MEMBER_XML
(
   ID                 VARCHAR2 (32 CHAR),
   EMAIL              VARCHAR2 (100 CHAR),
   PHONE_NUM          VARCHAR2 (40 CHAR),
   ORDER_NO           VARCHAR2 (60 CHAR),
   CERTIFICATE_TYPE   VARCHAR2 (60 CHAR),
   CERTIFICATE_NO     VARCHAR2 (60 CHAR),
   XML                NCLOB,
   CALL_BACK_FLAG     CHAR (1) DEFAULT 'N' NOT NULL,
   INSERTED_BY        VARCHAR2 (40 CHAR),
   INSERTED_DATE      TIMESTAMP DEFAULT SYSDATE NOT NULL,
   UPDATED_BY         VARCHAR2 (40 CHAR),
   UPDATED_DATE       TIMESTAMP DEFAULT SYSDATE NOT NULL,
   COUPON_CODE        VARCHAR2(10),
   CONSTRAINT PK_T_MEMBER_XML PRIMARY KEY (ID)
);

CREATE TABLE T_MEMBER_ORDER_NOTE
(
   ID            VARCHAR2 (32 CHAR),
   ORDER_NO      VARCHAR2 (30 CHAR),
   CONTENT       VARCHAR2 (100 CHAR),
   OPERATION     VARCHAR2 (20 CHAR),
   USER_ID       VARCHAR2 (30 CHAR),
   USER_NAME     VARCHAR2 (30 CHAR),
   REASON        VARCHAR2 (500 CHAR),
   CREATE_TIME   TIMESTAMP NOT NULL,
   CONSTRAINT PK_T_MEMBER_ORDER_NOTE PRIMARY KEY (ID)
);

CREATE TABLE MEMBER_MEM_CARD
(
   ID            NUMBER NOT NULL,
   MEM_INFO_ID   NUMBER NOT NULL,
   MEM_ID        VARCHAR2 (15) NOT NULL,
   CARD_TYPE_CD  VARCHAR2(50) NOT NULL,
   X_CARD_NUM    VARCHAR2 (50) NOT NULL,
   SOURCE        VARCHAR2 (50),
   VALID_DATE    DATE DEFAULT SYSDATE ,
   DUE_DATE      DATE,
   STATUS        VARCHAR2 (10),
   LAST_UPD      DATE,
   CRM_KEY       VARCHAR2 (50),
   DT_STATUS     VARCHAR2 (10),
   DT_MSG        VARCHAR2 (1000),
   DT_UPD        DATE,
   CONSTRAINT PK_MEMBER_MEM_CARD PRIMARY KEY (ID)
);

create table MEMBER_MEM_INFO
(
  ID             NUMBER primary key not null,
  MEM_ID         VARCHAR2(45) not null,
  MEM_NUM        VARCHAR2(90) not null,
  ACTIVATE_CODE  VARCHAR2(150),
  NAME           VARCHAR2(150) not null,
  TITLE          VARCHAR2(90),
  IDENTITY_TYPE  VARCHAR2(150),
  IDENTITY_NO    VARCHAR2(150),
  EMAIL          VARCHAR2(300),
  PHONE          VARCHAR2(90),
  CARD_NO        VARCHAR2(30),
  MEM_TYPE       VARCHAR2(90),
  MEM_TIER       VARCHAR2(90) ,
  ENROLL_CHANNEL VARCHAR2(90),
  ENROLL_DATE    DATE,
  QUESTION       VARCHAR2(100),
  ANSWER         VARCHAR2(100),
  STATUS         VARCHAR2(150) not null,
  CARD_LEVEL     VARCHAR2(30) not null,
  LAST_UPD       DATE,
  CRM_KEY        VARCHAR2(150),
  DT_STATUS      VARCHAR2(30),
  DT_MSG         VARCHAR2(3000),
  DT_UPD         DATE,
  MC_MEMBER_CODE VARCHAR2(50),
  IP_ADDRESS     VARCHAR2(150),
  M_CUSTOMER_ID  number,
  REGIST_TAG varchar2(300),
  IS_QUICK_USER VARCHAR2(10),
  ACTIVE_TAG varchar2(300),
  NEW_MEM_TIER       VARCHAR2(90),
  CONSTRAINT PK_MEMBER_MEM_INFO PRIMARY KEY (ID)
);

CREATE TABLE MEMBER_VERIFY
(
   ID         NUMBER NOT NULL,
   MEM_INFO_ID   NUMBER NOT NULL,
   MEM_ID     VARCHAR2 (15) NOT NULL,
   MEM_NUM    VARCHAR2 (30) NOT NULL,
   MEN_NAME   VARCHAR2 (50) NOT NULL,
   PASSWORD   VARCHAR2 (50) NOT NULL,
   CONSTRAINT PK_MEMBER_VERIFY PRIMARY KEY (ID),
   CONSTRAINT UNI_MEN_NAME_MEMBER_V UNIQUE (MEN_NAME)
);

create table T_VBP_NOTE
(
  ID             NUMBER(10) not null,
  OWNER_CATEGORY VARCHAR2(64) not null,
  OWNER_ID       NUMBER(10) not null,
  CONTENT        VARCHAR2(1024),
  OPERATION      VARCHAR2(64),
  CREATOR_ID     VARCHAR2(100),
  CREATOR_NAME   VARCHAR2(64),
  CREATE_TIME    TIMESTAMP(6),
  CONSTRAINT PK_VBP_NOTE PRIMARY KEY (ID)
);


 CREATE TABLE T_VBP_SCORE_ORDER  (
   ID                 NUMBER         NOT NULL,
   ORDER_NO           VARCHAR2(15),
   CREATE_TIME        DATE,
   MEMBER_ID          VARCHAR2(50),
   BUY_SCORE          NUMBER,
   ORDER_STATUS       varchar2(10),
   PAY_AMOUT          NUMBER,
   PAY_TIME           DATE,
   PAYMENT_NO         VARCHAR2(100),
   PAY_TYPE           VARCHAR2(50),
   PAYMENT_VENDER     VARCHAR2(50),
   BANK_CODE          VARCHAR2(50),
   PAY_STATUS         varchar2(10),
   CONSTRAINT PK_VBP_SCORE_ORDER PRIMARY KEY (ID)
);

ALTER TABLE MEMBER_VERIFY ADD FOREIGN KEY (MEM_INFO_ID) REFERENCES MEMBER_MEM_INFO (ID);
ALTER TABLE MEMBER_MEM_CARD ADD FOREIGN KEY (MEM_INFO_ID) REFERENCES MEMBER_MEM_INFO (ID);
 
CREATE TABLE T_VBP_MEMBER_SCORE_LEVEL_INFO  (
   ID                 NUMBER         NOT NULL,
   MEMBER_INFO_ID     NUMBER NOT NULL,
   MEM_NUM          VARCHAR2(50) NOT NULL,
   AVAILABLE_SCORE    NUMBER,
   RANK_SCORE         NUMBER,
   RANK_TIME_SIZE     NUMBER,
   UPDATED_DATE       TIMESTAMP DEFAULT SYSDATE NOT NULL,
   SCORE_LEVEL         NUMBER,
   SCORE_MEMEBER_LEVEl_INDATE       DATE,
   SCORE_TYPE     VARCHAR2(50) NOT NULL ,
   CRM_KEY       VARCHAR2 (50),
   DT_STATUS     VARCHAR2 (10),
   DT_MSG        VARCHAR2 (1000),
   DT_UPD        DATE,
   CONSTRAINT PK_VBP_MEMBER_SCORE_LEVEL_INFO PRIMARY KEY (ID),
   CONSTRAINT UNI_VBP_MEMBER_SCORE_LEVEL UNIQUE (MEM_NUM)
);

CREATE TABLE T_data_code
(
   ID                 NUMBER         NOT NULL,
   NAME               VARCHAR2(100)  NOT NULL,
   code               VARCHAR2(50)   NOT NULL,
   val                VARCHAR2(1000),
   type               VARCHAR2(50)   NOT NULL,
   describe           VARCHAR2(1000),
   sort               VARCHAR2(50),
   CREATE_date        DATE DEFAULT SYSDATE NOT NULL,
   CREATE_person      VARCHAR2(100),
   update_date        DATE DEFAULT SYSDATE NOT NULL,
   update_person      VARCHAR2(100),
   CONSTRAINT PK_data_code PRIMARY KEY (ID)
);
alter table T_DATA_CODE add constraint UK_DATA_CODE unique (CODE, TYPE);


CREATE TABLE WEBMEMBER_VERIFY
(
   ID         NUMBER NOT NULL,
   MEM_INFO_ID   NUMBER NOT NULL,   
   MEN_NAME   VARCHAR2 (50) NOT NULL,
   PASSWORD   VARCHAR2 (50) NOT NULL,  
   CONSTRAINT PK_WEBMEMBER_VERIFY PRIMARY KEY (ID),
   CONSTRAINT UNI_MEN_NAME_WEBMEMBER_V UNIQUE (MEN_NAME)
);


CREATE TABLE WEBMEMBER_INFO
(
   ID              		NUMBER NOT NULL,
   EMAIL            	VARCHAR2 (30) NULL,
   PHONE            	VARCHAR2 (30),
   TEMP_CARD_NO         VARCHAR2 (12) NOT NULL,
   MEM_NUM         		VARCHAR2 (30) ,
   LAST_UPDATE_TIME 	TIMESTAMP,
   LAST_LOGIN_TIME  	TIMESTAMP,
   REFERRER_CARD_NO   	VARCHAR2 (10),
   IS_MOBILE_BIND  		NUMBER DEFAULT 0 NOT NULL,
   IS_EMAIL_BIND   		NUMBER  DEFAULT 0 NOT NULL,
   REGIST_CHANNEL    	VARCHAR2 (100) NOT NULL,
   REGIST_TIME          TIMESTAMP NOT NULL,  
   ACTIVITY_CODE        VARCHAR2 (100) ,   
   QUESTION             VARCHAR2 (100),
   ANSWER               VARCHAR2 (100),
   MC_MEMBER_CODE       VARCHAR2 (50),
   REGIST_TAG varchar2(300),
   MEM_TYPE          VARCHAR2 (50) default 'QUICK_REGIST' NOT NULL,
   IP_ADDRESS       VARCHAR2 (150),
   TRANSFORM_TYPE VARCHAR2(20) DEFAULT 'UNKNOWN' NOT NULL,
   CONSTRAINT PK_WEBMEMBER_INFO PRIMARY KEY (ID),
   CONSTRAINT UNI_EMAIL unique(TEMP_CARD_NO)
);


CREATE TABLE T_VBP_REFERRER_INFO
(
   ID              		NUMBER NOT NULL,
   MEM_INFO_ID  		NUMBER  NOT NULL,
   REFERRER_CARD_NO  VARCHAR2 (30)  NOT NULL,   
   CONSTRAINT PK_T_VBP_REFERRER_INFO PRIMARY KEY (ID)
);


CREATE TABLE T_VBP_LOTTERY_COIN
(
   ID                 NUMBER NOT NULL,
   MC_MEMBER_CODE     VARCHAR2(30) NOT NULL,
   AVAILABLE_LOTTERY_COIN           NUMBER,
   UPDATE_DATE        DATE DEFAULT SYSDATE NOT NULL,
   CONSTRAINT PK_T_VBP_LOTTERY_COIN PRIMARY KEY (ID),
   CONSTRAINT UNI_T_VBP_LOTTERY_COIN unique(MC_MEMBER_CODE)
);

CREATE TABLE T_VBP_LOTTERY_COIN_RULE
(
   ID              				NUMBER NOT NULL,
   OPERATE_NAME         		VARCHAR2 (50) NOT NULL,
   LOTTERY_COIN   	NUMBER,
   CONSTRAINT PK_T_VBP_LOTTERY_COIN_RULE PRIMARY KEY (ID)
);


CREATE TABLE T_VBP_LOTTERY_COIN_DETAIL
(
   ID                 		NUMBER 			NOT NULL,
   LOTTERY_COIN_ID          NUMBER 			NOT NULL,
   OPERATE_CATEGORY     	VARCHAR2(30) 	NOT NULL,
   COIN_QUANTITY         	NUMBER 			NOT NULL,
   CREATE_DATE        		DATE DEFAULT SYSDATE NOT NULL,
   CONSTRAINT PK_T_VBP_LOTTERY_COIN_DETAIL PRIMARY KEY (ID)
);


CREATE TABLE T_VBP_TARGET_PROMOTION
(
   ROW_ID                   VARCHAR2(15)   NOT NULL,
   CREATED               DATE DEFAULT SYSDATE NOT NULL,
   CREATED_BY        VARCHAR2(15)   NOT NULL,
   LAST_UPD          DATE DEFAULT SYSDATE NOT NULL,
   LAST_UPD_BY        VARCHAR2(15)   NOT NULL,
   MODIFICATION_NUM         VARCHAR2(10)   NOT NULL,
   CONFILICT_ID        VARCHAR2(15)    NOT NULL,
   DT_STATUS                VARCHAR2(10)    ,
   DB_LAST_UPD         DATE DEFAULT SYSDATE NOT NULL,
   REPLY_DATE                DATE DEFAULT SYSDATE NOT NULL,
   BATCH_NUM                 VARCHAR2(15) ,
   CAMP_CON_ID         VARCHAR2(15)    ,
   CONTRACT_ID               VARCHAR2(15)    ,
   CON_EMAIL                 VARCHAR2(50)    ,
   CON_PHONE         VARCHAR2(50)    ,
   DB_LAST_UPD_SRC           VARCHAR2(50)    ,
   DISCOUNT_ID               VARCHAR2(50)    ,
   DISCOUNT_RULE_ID          VARCHAR2(50)    ,
   MEMBER_ID                 VARCHAR2(15)    ,
   MEMBER_NUM         VARCHAR2(15)    ,
   ORDER_ID                  VARCHAR2(200)    ,
   PRODUCT_ID                VARCHAR2(50)    ,
   RECEIVE_STATUS            VARCHAR2(50)    ,
   REPLY_FAIL_REASON         VARCHAR2(200)    ,
   REPLY_STATUS              VARCHAR2(50)    ,
   REQ_ID                    VARCHAR2(15)    ,
   SOURCE_TYPE               VARCHAR2(50)    ,
   SRC_DESC                  VARCHAR2(500)    ,
   SEND_STATUS               VARCHAR2(30)    ,
   CONSTRAINT PK_T_VBP_TARGET_PROMOTION PRIMARY KEY (ROW_ID)
); 

CREATE TABLE T_VBP_NOTARGET_PROMOTION
(
   ROW_ID                   VARCHAR2(15)   NOT NULL,
   CREATED               DATE DEFAULT SYSDATE NOT NULL,
   CREATED_BY        VARCHAR2(15)   NOT NULL,
   LAST_UPD          DATE DEFAULT SYSDATE NOT NULL,
   LAST_UPD_BY        VARCHAR2(15)   NOT NULL,
   MODIFICATION_NUM         VARCHAR2(10)   NOT NULL,
   CONFILICT_ID        VARCHAR2(15)    NOT NULL,
   DB_LAST_UPD         DATE DEFAULT SYSDATE,
   DB_LAST_UPD_SRC           VARCHAR2(50)    ,
   DT_STATUS                 VARCHAR2(50)    ,
   JJBE_ORDER_ID       VARCHAR2(30),
   MEM_NUM             VARCHAR2(15)    ,
   SRC_ID                    VARCHAR2(15)    ,
   CONSTRAINT PK_T_VBP_NOTARGET_PROMOTION PRIMARY KEY (ROW_ID)
);

CREATE TABLE T_VBP_COMMONLY_USED_CONTACT 
(
  ID    NUMBER  NOT NULL,
  MC_MEMBER_CODE    VARCHAR2(50) NOT NULL,
  NAME    VARCHAR2(150) NOT NULL,
  IDENTITY_TYPE    VARCHAR2(150),
  IDENTITY_NO    VARCHAR2(150),
  BIRTHDAY DATE,
  PHONE VARCHAR2 (30),
  GENDER VARCHAR2(32),
  CARD_NO VARCHAR2(30),
  CREATE_DATE DATE DEFAULT SYSDATE,
  UPDATE_DATE DATE DEFAULT SYSDATE,
  CONSTRAINT PK_T_VBP_COMMONLY_USED_CONTACT PRIMARY KEY (ID)
);

CREATE TABLE T_VBP_BIG_CUSTOMER_ACCOUNT 
(
  ID    NUMBER  NOT NULL,
  USERNAME    VARCHAR2(150) NOT NULL,
  PASSWORD    VARCHAR2(100),
  CREATE_DATE DATE DEFAULT SYSDATE,
  UPDATE_DATE DATE DEFAULT SYSDATE,
  CONSTRAINT PK_T_VBP_BIG_CUSTOMER_ACCOUNT PRIMARY KEY (ID)
);



CREATE TABLE T_VBP_BIG_CUSTOMER_MIDDLE
(
  ID    NUMBER  NOT NULL,
  ACCOUNT_ID    NUMBER  NOT NULL,
  BIG_CUSTOMER_ID    NUMBER  NOT NULL,
  CONSTRAINT PK_T_VBP_BIG_CUSTOMER_MIDDLE   PRIMARY KEY (ID)
);


CREATE TABLE T_VBP_BIG_CUSTOMER 
(
  ID    NUMBER  NOT NULL,
  NAME    VARCHAR2(150) NOT NULL,
  CODE  VARCHAR2(50),
  ACCOUNT_ID    NUMBER  NOT NULL,
  CHANNEL  VARCHAR2(500),
  CONTACT_NAME  VARCHAR2(150),
  EMAIL  VARCHAR2(30),
  PHONE  VARCHAR2(20),
  MC_MEMBER_CODE VARCHAR2(150),
  CREATE_DATE DATE DEFAULT SYSDATE,
  UPDATE_DATE DATE DEFAULT SYSDATE,
  CRM_ID VARCHAR2(30),
  CONSTRAINT PK_T_VBP_BIG_CUSTOMER PRIMARY KEY (ID),
  CONSTRAINT FK_T_VBP_BIG_CUSTOMER_ACCOUNT FOREIGN KEY (ACCOUNT_ID) REFERENCES T_VBP_BIG_CUSTOMER_ACCOUNT(ID)
);

-- Add/modify columns 
alter table MEMBER_MEM_INFO add ACTIVE_DATE TIMESTAMP;
alter table MEMBER_MEM_INFO add ACTIVE_CHANNEL varchar2(30);

alter table T_MEMBER_CARD_ORDER add SALE_CHANNEL varchar2(30);


create table T_VBP_THIRDPARTY_BIND
(
  ID               number not null,
  MC_MEMBERCODE    varchar2(20),
  MEM_NUM          varchar2(20),
  THIRDPARTY_TYPE  varchar2(50),
  THIRDPARTY_SIGN  varchar2(100),
  THIRDPARTY_LEVEL varchar2(10),
  VIP_FLAG         varchar2(10),
  ORIG_LEVEL		VARCHAR2(10),   
  DEST_LEVEL		VARCHAR2(10),   
  REGISTER_FLAG		VARCHAR2(10),
  SCORE_FLAG		VARCHAR2(10),   
  COUPON_FLAG		VARCHAR2(10),  
  CREATE_DATE      TIMESTAMP(6)
)
;
alter table T_VBP_THIRDPARTY_BIND
  add constraint PK_THIRDPARTY_BIND primary key (ID);
-- Add comments to the table 
comment on table T_VBP_THIRDPARTY_BIND
  is '第三方账号绑定';
-- Add comments to the columns 
comment on column T_VBP_THIRDPARTY_BIND.ID
  is '序列号';
comment on column T_VBP_THIRDPARTY_BIND.MC_MEMBERCODE
  is '会员唯一标识';
comment on column T_VBP_THIRDPARTY_BIND.MEM_NUM
  is 'CRM会员标识';
comment on column T_VBP_THIRDPARTY_BIND.THIRDPARTY_TYPE
  is '第三方合作商';
comment on column T_VBP_THIRDPARTY_BIND.THIRDPARTY_SIGN
  is '第三方标识';
comment on column T_VBP_THIRDPARTY_BIND.THIRDPARTY_LEVEL
  is '第三方等级';
comment on column T_VBP_THIRDPARTY_BIND.VIP_FLAG
  is '是否会员';
comment on column T_VBP_THIRDPARTY_BIND.CREATE_DATE
  is '创建时间';
  
comment on column T_VBP_THIRDPARTY_BIND.orig_level
  is '原会员级别';
comment on column T_VBP_THIRDPARTY_BIND.dest_level
  is '升级会员级别';
comment on column T_VBP_THIRDPARTY_BIND.register_flag
  is '绑定新注册标示';
comment on column T_VBP_THIRDPARTY_BIND.score_flag
  is '绑定送积分标示';
comment on column T_VBP_THIRDPARTY_BIND.coupon_flag
  is '绑定送优惠券标示';  
  
alter table MEMBER_MEM_INFO add THIRDPARTY_TYPE VARCHAR2(30);
comment on column MEMBER_MEM_INFO.THIRDPARTY_TYPE
  is '合作类型';
    
CREATE TABLE T_VBP_SCORE_TRADE_ORDER
(
   ID                 NUMBER NOT NULL,
   ORDER_NO           VARCHAR2 (50),
   TRD_ORDER_NO       VARCHAR2 (50) NOT NULL,
   CREATE_TIME        TIMESTAMP,
   MC_MEMBER_CODE     VARCHAR2 (50),
   PRODUCT_ID         VARCHAR2 (50),
   TRADE_SCORE        NUMBER,
   STATUS             VARCHAR2 (50),
   PRODUCT_NAME       VARCHAR2(250),
   EXCHANGE_TYPE      VARCHAR2 (50),
   TRANS_ID           VARCHAR2 (50),
   REMAIN_POINT       VARCHAR2 (50),
   LAST_UPDATE_TIME   TIMESTAMP,
   CONSTRAINT T_VBP_SCORE_TRADE_ORDER  PRIMARY KEY (ID),
   CONSTRAINT UNI_SCORE_TRADE_ORDER_NO UNIQUE (TRD_ORDER_NO)
);

CREATE TABLE T_VBP_SCORE_TRADE_LOG
(
   ID                 NUMBER NOT NULL,
   ORDER_NO           VARCHAR2 (50),
   TRD_ORDER_NO       VARCHAR2 (50),
   CREATE_TIME        TIMESTAMP,
   MC_MEMBER_CODE     VARCHAR2 (50),
   PRODUCT_ID         VARCHAR2 (50),
   TRADE_SCORE        NUMBER,
   STATUS             VARCHAR2 (50),
   PRODUCT_NAME       VARCHAR2(250),
   EXCHANGE_TYPE      VARCHAR2 (50),
   TRANS_ID           VARCHAR2 (50),
   REMAIN_POINT       VARCHAR2 (50),
   LAST_UPDATE_TIME   TIMESTAMP,
   REMARK             CLOB,
   CONSTRAINT T_VBP_SCORE_TRADE_LOG  PRIMARY KEY (ID)
);

CREATE TABLE T_VBP_MEMBER_POINTS
(
   NAME                VARCHAR2 (100),
   MEM_NUM               VARCHAR2 (50),
   CARD_NUM             VARCHAR2 (30),
   PER_TITLE            VARCHAR2 (15),
   EMAIL                VARCHAR2 (100),
   TIER                 VARCHAR2 (30),
   HIGHEST_TYPE         VARCHAR2 (20),
   POINTS                NUMBER,
   POINT_EXPIRY_DT       VARCHAR2 (15),
   REDEEMED_POINTS       NUMBER,
   TIER_UP_POINTS        NUMBER,
   TIER_UP_TIMES         NUMBER,
   TO_DATE               DATE,
   CHANGED_POINTS        NUMBER,
   AQUIRED_POINTS        NUMBER,
   CONSTRAINT PK_T_VBP_MEMBER_POINTS PRIMARY KEY (MEM_NUM)
);

CREATE TABLE T_VBP_CONFIG
(
   ID                   NUMBER NOT NULL,
   KEY                  VARCHAR2 (100),
   TYPE                 VARCHAR2(100),
   VALUE                VARCHAR2(2000),
   CONSTRAINT PK_T_VBP_CONFIG PRIMARY KEY (ID)
);

-- Create T_VBP_MEMBER_FEEDBACK table
create table T_VBP_MEMBER_FEEDBACK
(
  ID             number not null,
  OPERATOR       varchar2(45),
  CHANNEL        varchar2(45) not null,
  SOURCES        varchar2(45) not null,
  STATUS         varchar2(45) not null,
  MC_MEMBER_CODE varchar2(50) not null,
  CONTENT        varchar2(2500),
  FEEDBACK_TYPE VARCHAR2(200),
  SOLVE_MEMO     varchar2(2500),
  CREATE_TIME    timestamp not null,
  UPDATE_TIME    timestamp
)
;
-- Add comments to the columns 
comment on column T_VBP_MEMBER_FEEDBACK.OPERATOR
  is '操作人';
comment on column T_VBP_MEMBER_FEEDBACK.CHANNEL
  is '投诉渠道板块';
comment on column T_VBP_MEMBER_FEEDBACK.STATUS
  is '处理状态';
comment on column T_VBP_MEMBER_FEEDBACK.MC_MEMBER_CODE
  is '会员编号';
comment on column T_VBP_MEMBER_FEEDBACK.CONTENT
  is '投诉内容';
comment on column T_VBP_MEMBER_FEEDBACK.SOLVE_MEMO
  is '解决方案备注';
  is '解决方案备注';
comment on column T_VBP_MEMBER_FEEDBACK.CREATE_TIME
  is '创建时间';
comment on column T_VBP_MEMBER_FEEDBACK.UPDATE_TIME
  is '更新时间';
comment on column T_VBP_MEMBER_FEEDBACK.FEEDBACK_TYPE
  is '问题类型';

--frank.zhang--
alter table t_vbp_commonly_used_contact add EN_FIRST_NAME VARCHAR2 (100);
alter table t_vbp_commonly_used_contact add EN_LAST_NAME VARCHAR2 (100);
alter table t_vbp_commonly_used_contact add CERTIFICATE_ADDRESS VARCHAR2 (200);
alter table t_vbp_commonly_used_contact add CERTIFICATE_DATE DATE;
alter table t_vbp_commonly_used_contact add LABEL VARCHAR2 (100);

comment on column t_vbp_commonly_used_contact.EN_FIRST_NAME
  is '英文名';
comment on column t_vbp_commonly_used_contact.EN_LAST_NAME
  is '英文姓';
comment on column t_vbp_commonly_used_contact.CERTIFICATE_ADDRESS
  is '证件签发地';
comment on column t_vbp_commonly_used_contact.CERTIFICATE_DATE
  is '证件有效期';
comment on column t_vbp_commonly_used_contact.LABEL
  is '标签';


-- Create 投诉反馈 table
create table t_vbp_member_sub_feedback
(
  ID          number not null,
  FEEDBACK_ID number not null,
  OPERATOR    varchar2(45),
  CREATE_TIME timestamp(6) not null,
  CONTENT     varchar2(2500)
);

CREATE TABLE T_VBP_OP_RECORD_LOG
(
	ID 			NUMBER NOT NULL,
	OP_TYPE 	VARCHAR2(100),
	MESSAGE 	VARCHAR2(500),
	CONTENT 	VARCHAR2(2000),
	CREATE_DATE	TIMESTAMP,
	UPDATE_DATE	TIMESTAMP,
	CONSTRAINT PK_T_VBP_OP_RECORD_LOG PRIMARY KEY (ID)
);

CREATE TABLE T_VBP_VALIDATE_CODE_LOG
(
	ID 			NUMBER NOT NULL,
	IP			VARCHAR2(100),
	PHONE	 	VARCHAR2(30),
	MAIL		VARCHAR2(100),
	CODE 		VARCHAR2(100),
	CREATE_DATE	TIMESTAMP  NOT NULL,
	CONSTRAINT PK_T_VBP_VALIDATE_CODE_LOG PRIMARY KEY (ID)
);

CREATE TABLE T_VBP_VAILDATE_CODE
(
  ID          NUMBER NOT NULL,
  CODE        VARCHAR2(10) NOT NULL,
  RECEIVER    VARCHAR2(100) NOT NULL,
  CREATE_DATE TIMESTAMP NOT NULL
);

alter table T_VBP_VAILDATE_CODE
  add constraint PK_VBP_VAILDATE_CODE primary key (ID);

  
create table T_VBP_MEMBER_CONTACTSNS
(
  ID          number not null,
  MEM_ID      varchar2(45) not null,
  USETYPE     varchar2(150),
  SNS         varchar2(300) not null,
  STATUS      varchar2(45) not null,
  BINDDATE    date,
  ENDDATE     date,
  BINDCHANNEL varchar2(45),
  COMMENTS    varchar2(300)
);
alter table T_VBP_MEMBER_CONTACTSNS 
  add constraint PK_T_VBP_CONTACTSNS primary key (ID);

  
---绑定手机验证  helen  ---
  alter table T_VBP_VALIDATE_CODE_LOG  add MEMBER_INFO_ID VARCHAR2 (100);

--frank.zhang 2014-07-15------------------------------------------------------
create sequence S_VBP_MEMBER_RECOMMEND increment by 1 start with 10000;

CREATE TABLE T_VBP_MEMBER_RECOMMEND
(
	ID				NUMBER NOT NULL,
	Recommender_Id	VARCHAR2(100),
	Register_Id		VARCHAR2(100),
	Campaign		VARCHAR2(100),
	CREATE_DATE		TIMESTAMP DEFAULT SYSDATE,
	UPDATE_DATE		TIMESTAMP DEFAULT SYSDATE,
	CONSTRAINT PK_VBP_MEMBER_RECOMMEND PRIMARY KEY (ID)
);
comment on table T_VBP_MEMBER_RECOMMEND is '会员推荐';
comment on column T_VBP_MEMBER_RECOMMEND.Recommender_Id is '推荐人memberId';
comment on column T_VBP_MEMBER_RECOMMEND.Register_Id is '注册人memberId';
comment on column T_VBP_MEMBER_RECOMMEND.Campaign is '推荐活动批号';
------------------------------------------------------frank.zhang 2014-07-15--
--edison.zuo 2014-07-17------------------------------------------------------
create sequence S_VBP_MEMBER_RECOMMEND_COUPON increment by 1 start with 10000;

CREATE TABLE T_VBP_MEMBER_RECOMMEND_COUPON
(
	ID				NUMBER NOT NULL,
	Recommender_Id	VARCHAR2(100),
	Campaign		VARCHAR2(100),
	CREATE_DATE		TIMESTAMP DEFAULT SYSDATE,
	CONSTRAINT PK_VBP_MEMBER_RECOMMEND_COUPON PRIMARY KEY (ID)
);
comment on table T_VBP_MEMBER_RECOMMEND_COUPON is '会员推荐发放优惠券记录';
comment on column T_VBP_MEMBER_RECOMMEND_COUPON.Recommender_Id is '已发放推荐人memberId';
comment on column T_VBP_MEMBER_RECOMMEND_COUPON.Campaign is '推荐活动批号';
------------------------------------------------------edison.zuo 2014-07-17--

--carter.luo 2014-07-15------------------------------------------------------
create table T_VBP_RECOMMEND_ORDER
(
  id           NUMBER,
  member_id    VARCHAR2(50),
  recommend_id VARCHAR2(50),
  score_level  NUMBER,
  member_name  VARCHAR2(50),
  booking_date DATE,
  last_upd     DATE
);

create sequence S_VBP_RECOMMEND_ORDER increment by 1 start with 10000;
------------------------------------------------------carter.luo 2014-07-15--

--frank.zhang 2014-08-08------------------------------------------------------
create table T_VBP_MEMBER_LEVEL_BENEFIT
(
	created             VARCHAR2(20),
	member_id           VARCHAR2(20),
	tier_id             VARCHAR2(20),
	amt_val             NUMBER(15,3),
	available_score     NUMBER(15,3),
	new_tier_id         VARCHAR2(20),
	new_amt_val         NUMBER(15,3),
	new_available_score NUMBER(15,3),
	amt_diff            NUMBER(15,3),
	score_diff          NUMBER(15,3)
);

comment on table T_VBP_MEMBER_LEVEL_BENEFIT is '会员层级优势';
comment on column T_VBP_MEMBER_LEVEL_BENEFIT.created is '年月';
comment on column T_VBP_MEMBER_LEVEL_BENEFIT.tier_id is '层级';
comment on column T_VBP_MEMBER_LEVEL_BENEFIT.amt_val is '实际消费金额';
comment on column T_VBP_MEMBER_LEVEL_BENEFIT.available_score is '实际获得积分';
comment on column T_VBP_MEMBER_LEVEL_BENEFIT.new_tier_id is '更高层级';
comment on column T_VBP_MEMBER_LEVEL_BENEFIT.new_amt_val is '期望消费金额';
comment on column T_VBP_MEMBER_LEVEL_BENEFIT.new_available_score is '期望获得积分';
comment on column T_VBP_MEMBER_LEVEL_BENEFIT.amt_diff is '金额差';
comment on column T_VBP_MEMBER_LEVEL_BENEFIT.score_diff is '积分差';
------------------------------------------------------frank.zhang 2014-08-08--

--frank.zhang 2014-08-26------------------------------------------------------
create sequence S_VBP_MEMBER_TAOBAO_BINDING increment by 1 start with 10000;
create table T_VBP_MEMBER_TAOBAO_BINDING
(
	ID				NUMBER NOT NULL,
	TAOBAO_ID		VARCHAR2(100) unique,
	TAOBAO_LEVEL VARCHAR2(100),
	MEMBER_ID		VARCHAR2(100),
	STATUS			VARCHAR2(100),
	BIND_MODE		VARCHAR2(100),
	CREATE_DATE		TIMESTAMP DEFAULT SYSDATE,
	UPDATE_DATE		TIMESTAMP DEFAULT SYSDATE,
	CONSTRAINT PK_VBP_MEMBER_TAOBAO_BINDING PRIMARY KEY (ID)
);

comment on table T_VBP_MEMBER_TAOBAO_BINDING is '淘宝绑定';
comment on column T_VBP_MEMBER_TAOBAO_BINDING.TAOBAO_ID is '淘宝ID';
comment on column T_VBP_MEMBER_TAOBAO_BINDING.MEMBER_ID is '锦江ID';
comment on column T_VBP_MEMBER_TAOBAO_BINDING.STATUS is '状态';
comment on column T_VBP_MEMBER_TAOBAO_BINDING.BIND_MODE is '方式';
comment on column T_VBP_MEMBER_TAOBAO_BINDING.TAOBAO_LEVEL is '淘宝等级';
------------------------------------------------------frank.zhang 2014-08-26--

-----------tristan------------
alter table MEMBER_MEM_INFO add HOTEL_CHANNEL varchar2(100);
------------------------------

--frank.zhang 2014-10-15------------------------------------------------------
drop table T_MEMBER_WECHAT_INFO cascade constraints;
create table T_MEMBER_WECHAT_INFO
(
  id             NUMBER not null,
  mc_member_code VARCHAR2(200) not null,
  wechat_id      VARCHAR2(200) not null,
  service_id     VARCHAR2(200) not null,
  type           VARCHAR2(10) default 0,
  state          VARCHAR2(10) default 1,
  create_time    TIMESTAMP DEFAULT SYSDATE,
  update_time    TIMESTAMP DEFAULT SYSDATE
);
------------------------------------------------------frank.zhang 2014-10-15--

--xitong.yan 2014-12-02-------------------------------------------------------
drop table T_VBP_MEMBER_BIND_INFO;
create table T_VBP_MEMBER_BIND_INFO
(
	id									NUMBER,
	member_id							VARCHAR2(20) NOT NULL,
	mc_member_code						VARCHAR2(20) NOT NULL,
	bind_type							VARCHAR2(200) NOT NULL,
	bind_key 							VARCHAR2(200) NOT NULL,
	channel								VARCHAR2(200) NOT NULL,
	status								VARCHAR2(200) ,
	create_time 						TIMESTAMP DEFAULT SYSDATE,
	update_time 						TIMESTAMP DEFAULT  SYSDATE,
	CONSTRAINT	PK_VBP_MEMBER_BIND_INFO PRIMARY KEY(id)
);


comment on table T_VBP_MEMBER_BIND_INFO is '绑定信息表';
comment on column T_VBP_MEMBER_BIND_INFO.member_id is '锦江用户ID';
comment on column T_VBP_MEMBER_BIND_INFO.mc_member_code is '锦江用户ID';
comment on column T_VBP_MEMBER_BIND_INFO.bind_type is '绑定类型为:注册绑定,登录绑定';
comment on column T_VBP_MEMBER_BIND_INFO.bind_key is '绑定的key';
comment on column T_VBP_MEMBER_BIND_INFO.channel is '绑定的来源';
comment on column T_VBP_MEMBER_BIND_INFO.status is '是否启用:不启用,启用';
comment on column T_VBP_MEMBER_BIND_INFO.create_time is '绑定时间';
comment on column T_VBP_MEMBER_BIND_INFO.update_time is '更新时间';

drop SEQUENCE  member_bind_sequence;
CREATE SEQUENCE member_bind_sequence  INCREMENT BY 1 START WITH 10000;
-------------------------------------------------------xitong.yan 2014-12-02--


create table MEMBER_VERIFY_JJZX
(
  ID          NUMBER not null,
  MEM_INFO_ID NUMBER not null,
  MEM_ID      VARCHAR2(15) not null,
  MEM_NUM     VARCHAR2(30) not null,
  MEN_NAME    VARCHAR2(50) not null,
  PASSWORD    VARCHAR2(50) not null,
  STATUS      VARCHAR2(1)
)

alter table MEMBER_VERIFY_JJZX
  add constraint PK_MEMBER_VERIFY_JJZX primary key (ID)

comment on column MEMBER_VERIFY_JJZX.STATUS
  is '0-未验证  1-已验证';  
  
create table MEMBER_VERIFY_JJZX_CONFLICT
(
  ID          NUMBER not null,
  MEM_INFO_ID NUMBER not null,
  MEM_ID      VARCHAR2(15) not null,
  MEM_NUM     VARCHAR2(30) not null,
  MEN_NAME    VARCHAR2(50) not null,
  PASSWORD    VARCHAR2(50) not null,
  STATUS      VARCHAR2(1)
)

alter table MEMBER_VERIFY_JJZX_CONFLICT
  add constraint PK_MEMBER_VERIFY_JJZX_CONFLICT primary key (ID)

comment on column MEMBER_VERIFY_JJZX_CONFLICT.STATUS
  is '0-未验证  1-已验证';
  