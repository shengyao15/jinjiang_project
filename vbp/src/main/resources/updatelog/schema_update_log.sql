---------------------------------update by emily.zhu 2012-03-02 start----------------------------------
drop table T_VBP_MEMBER_SCORE_LEVEL_INFO cascade constraints;
drop sequence S_VBP_MEMBER_SCORE_LEVEL_INFO;
create sequence S_VBP_MEMBER_SCORE_LEVEL_INFO increment by 1 start with 10000;
CREATE TABLE T_VBP_MEMBER_SCORE_LEVEL_INFO  (
   ID                 	NUMBER         	NOT NULL,
   MEMBER_INFO_ID     	NUMBER 			NOT NULL,
   MEM_NUM          	VARCHAR2(50) 		NOT NULL,
   AVAILABLE_SCORE    	NUMBER,
   RANK_SCORE         	NUMBER,
   RANK_TIME_SIZE     	NUMBER,
   UPDATED_DATE       	TIMESTAMP DEFAULT SYSDATE NOT NULL,
   SCORE_LEVEL         	NUMBER,
   SCORE_MEMEBER_LEVEl_INDATE       DATE,
   SCORE_TYPE     		VARCHAR2(50) 			NOT NULL ,
   CRM_KEY       		VARCHAR2 (50),
   DT_STATUS     		VARCHAR2 (10),
   DT_MSG        		VARCHAR2 (1000),
   DT_UPD        		DATE,
   CONSTRAINT PK_VBP_MEMBER_SCORE_LEVEL_INFO PRIMARY KEY (ID)
);
---------------------------------update by emily.zhu 2012-03-02 end----------------------------------

---------------------------------update by jianmin.huang 2012-03-02 start----------------------------------
drop table T_DATA_CODE cascade constraints;
drop sequence S_DATA_CODE;
create sequence S_DATA_CODE increment by 1 start with 10000;
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

insert into T_DATA_CODE (ID, NAME, CODE, VAL, TYPE, DESCRIBE, SORT, CREATE_DATE, CREATE_PERSON, UPDATE_DATE, UPDATE_PERSON)
values (1, '金卡升级次数 ', 'GOLD_UPGRADE_NUMBER', '30', 'UPGRADE_NUMBER', '', '', sysdate, '', sysdate, '');

insert into T_DATA_CODE (ID, NAME, CODE, VAL, TYPE, DESCRIBE, SORT, CREATE_DATE, CREATE_PERSON, UPDATE_DATE, UPDATE_PERSON)
values (2, '铂金卡升级次数', 'PLATINUM_UPGRADE_NUMBER', '80', 'UPGRADE_NUMBER', '', '', sysdate, '', sysdate, '');

insert into T_DATA_CODE (ID, NAME, CODE, VAL, TYPE, DESCRIBE, SORT, CREATE_DATE, CREATE_PERSON, UPDATE_DATE, UPDATE_PERSON)
values (4, '铂金卡升级积分', 'PLATINUM_UPGRADE_SCORES', '80000', 'UPGRADE_SCORES', '', '', sysdate, '', sysdate, '');

insert into T_DATA_CODE (ID, NAME, CODE, VAL, TYPE, DESCRIBE, SORT, CREATE_DATE, CREATE_PERSON, UPDATE_DATE, UPDATE_PERSON)
values (3, '金卡升级积分', 'GOLD_UPGRADE_SCORES', '30000', 'UPGRADE_SCORES', '', '', sysdate, '', sysdate, '');

---------------------------------update by jianmin.huang 2012-03-02 end----------------------------------


---------------------------------update by emily.zhu 2012-03-06 start----------------------------------
DROP TABLE WEBMEMBER_VERIFY CASCADE CONSTRAINTS;
DROP SEQUENCE S_WEBMEMBER_VERIFY;
CREATE SEQUENCE S_WEBMEMBER_VERIFY  INCREMENT BY 1 START WITH 10000;
CREATE TABLE WEBMEMBER_VERIFY
(
   ID         NUMBER NOT NULL,
   MEM_INFO_ID   NUMBER NOT NULL,   
   MEN_NAME   VARCHAR2 (50) NOT NULL,
   PASSWORD   VARCHAR2 (50) NOT NULL,
   IS_WEBMEMBER  NUMBER DEFAULT 1 NOT NULL,
   CONSTRAINT PK_WEBMEMBER_VERIFY PRIMARY KEY (ID)
);


DROP TABLE WEBMEMBER_INFO CASCADE CONSTRAINTS;
DROP SEQUENCE S_WEBMEMBER_INFO;
CREATE SEQUENCE S_WEBMEMBER_INFO INCREMENT BY 1 START WITH 10000;
CREATE TABLE WEBMEMBER_INFO
(
   ID              	NUMBER NOT NULL,
   EMAIL            VARCHAR2 (30)  NOT NULL,
   PHONE            VARCHAR2 (30),
   TEMP_CARD_NO          VARCHAR2 (12) NOT NULL,
   MEM_NUM         	VARCHAR2 (10) ,
   LAST_UPDATE_TIME TIMESTAMP,
   LAST_LOGIN_TIME  TIMESTAMP,
   REFERRER_CARD_NO   VARCHAR2 (10),
   IS_MOBILE_BIND  	NUMBER DEFAULT 0 NOT NULL,
   IS_EMAIL_BIND   	NUMBER  DEFAULT 0 NOT NULL,
   REGIST_CHANNEL    VARCHAR2 (100) NOT NULL,
   REGIST_TIME          TIMESTAMP NOT NULL,  
   ACTIVITY_CODE          VARCHAR2 (100) ,   
   CRM_KEY       		VARCHAR2 (50),
   DT_STATUS     		VARCHAR2 (10),
   DT_MSG        		VARCHAR2 (1000),
   DT_UPD        		DATE,
   CONSTRAINT PK_WEBMEMBER_INFO PRIMARY KEY (ID),
   CONSTRAINT UNI_EMAIL unique(TEMP_CARD_NO)
);
---------------------------------update by emily.zhu 2012-03-06 end----------------------------------

--------------------------------ALTER by sam.hu 2012-03-14 start-----------------------------------
ALTER TABLE MEMBER_MEM_CARD MODIFY VALID_DATE NULL;
ALTER TABLE MEMBER_MEM_CARD MODIFY STATUS NULL;
--------------------------------ALTER by sam.hu 2012-03-14 end-----------------------------------
--**********************aboves already deploy to product environment 20120322******************


--********************************* already deploy to product environment (20120417) start ******************************
--------------------------------update by emily.zhu 2012-04-01 start----------------------------------

CREATE SEQUENCE S_VBP_REFERRER_INFO INCREMENT BY 1 START WITH 10000;
CREATE TABLE T_VBP_REFERRER_INFO
(
   ID              		NUMBER NOT NULL,
   MEM_INFO_ID  		NUMBER  NOT NULL,
   REFERRER_CARD_NO  VARCHAR2 (30)  NOT NULL,   
   CONSTRAINT PK_T_VBP_REFERRER_INFO PRIMARY KEY (ID)
);
---------------------------------update by emily.zhu 2012-04-01 end----------------------------------

---------------------------------update by lily.xia 2012-04-05 start---------------------------------
CREATE SEQUENCE S_VBP_LOTTERY_COIN INCREMENT BY 1 START WITH 10000;
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

insert into T_VBP_LOTTERY_COIN_RULE (ID,OPERATE_NAME,LOTTERY_COIN) values(1,'EVERY_DAY',-2);
insert into T_VBP_LOTTERY_COIN_RULE (ID,OPERATE_NAME,LOTTERY_COIN) values(2,'EVERY_WEEKLY',-4);
insert into T_VBP_LOTTERY_COIN_RULE (ID,OPERATE_NAME,LOTTERY_COIN) values(3,'EVERY_MONTH',-6);

insert into T_VBP_LOTTERY_COIN_RULE (ID,OPERATE_NAME,LOTTERY_COIN) values(4,'QUICK_REG',6);
insert into T_VBP_LOTTERY_COIN_RULE (ID,OPERATE_NAME,LOTTERY_COIN) values(5,'FULL_REG',16);
insert into T_VBP_LOTTERY_COIN_RULE (ID,OPERATE_NAME,LOTTERY_COIN) values(6,'SHARE',6);
insert into T_VBP_LOTTERY_COIN_RULE (ID,OPERATE_NAME,LOTTERY_COIN) values(7,'RECOMMEND_FRIEND',4);
insert into T_VBP_LOTTERY_COIN_RULE (ID,OPERATE_NAME,LOTTERY_COIN) values(8,'COMPLETE_INFO',10);
insert into T_VBP_LOTTERY_COIN_RULE (ID,OPERATE_NAME,LOTTERY_COIN) values(9,'VERIFY_MOBILE',4);
insert into T_VBP_LOTTERY_COIN_RULE (ID,OPERATE_NAME,LOTTERY_COIN) values(10,'VERIFY_EMAIL',4);
insert into T_VBP_LOTTERY_COIN_RULE (ID,OPERATE_NAME,LOTTERY_COIN) values(11,'TWO_COIN',2);
insert into T_VBP_LOTTERY_COIN_RULE (ID,OPERATE_NAME,LOTTERY_COIN) values(12,'FOUR_COIN',4);
insert into T_VBP_LOTTERY_COIN_RULE (ID,OPERATE_NAME,LOTTERY_COIN) values(13,'SIX_COIN',6);
---------------------------------update by lily.xia 2012-04-05 end-----------------------------------


---------------------------------update by iven.chen 2012-04-13 start-----------------------------------
CREATE SEQUENCE S_VBP_LOTTERY_COIN_DETAIL INCREMENT BY 1 START WITH 10000;
CREATE TABLE T_VBP_LOTTERY_COIN_DETAIL
(
   ID                 		NUMBER 			NOT NULL,
   LOTTERY_COIN_ID          NUMBER 			NOT NULL,
   OPERATE_CATEGORY     	VARCHAR2(30) 	NOT NULL,
   COIN_QUANTITY         	NUMBER 			NOT NULL,
   CREATE_DATE        		DATE DEFAULT SYSDATE NOT NULL,
   CONSTRAINT PK_T_VBP_LOTTERY_COIN_DETAIL PRIMARY KEY (ID)
);
---------------------------------update by iven.chen 2012-04-13 end-----------------------------------

---------------------------------update by jianmin.huang 2012-04-13 start--------------------------------
alter table WEBMEMBER_INFO add QUESTION VARCHAR2 (100);
alter table WEBMEMBER_INFO add ANSWER   VARCHAR2 (100);
---------------------------------update by jianmin.huang 2012-04-13 end----------------------------------
--********************************* already deploy to product environment (20120417) end ******************************

 --********************************* already deploy to product environment (20120524) start ******************************
---------------------------------update by jianmin.huang 2012-04-23 start--------------------------------
CREATE SEQUENCE S_MC_MEMBER_CODE INCREMENT BY 1 START WITH 10000;
alter table WEBMEMBER_INFO add mc_Member_Code VARCHAR2 (50);
alter table MEMBER_MEM_INFO add mc_Member_Code VARCHAR2 (50);
alter table WEBMEMBER_INFO add  MEM_TYPE  VARCHAR2 (50)  default 'QUICK_REGIST' not null;
---------------------------------update by jianmin.huang 2012-04-23 end----------------------------------

---------------------------------update by sam.hu 2012-05-11 start----------------------------------------
alter table MEMBER_VERIFY MODIFY MEN_NAME UNIQUE;
alter table WEBMEMBER_VERIFY  MODIFY MEN_NAME UNIQUE;
CREATE INDEX IND_N_MEMBER_KEY_MC_CODE ON MEMBER_MEM_INFO
(MC_MEMBER_CODE)
LOGGING
NOPARALLEL;
CREATE INDEX IND_N_WEBMEMBER_KEY_MC_CODE ON WEBMEMBER_INFO
(MC_MEMBER_CODE)
LOGGING
NOPARALLEL;
---------------------------------update by sam.hu 2012-05-11 end----------------------------------------

---------------------------------update by iven.chen 2012-05-11 start----------------------------------------
alter table WEBMEMBER_VERIFY drop column IS_WEBMEMBER;
alter table WEBMEMBER_INFO MODIFY MEM_TYPE not null;
---------------------------------update by iven.chen 2012-05-11 start----------------------------------------
--********************************* already deploy to product environment (20120524) end ******************************

--********************************* already deploy to product environment (20120528) start ******************************
---------------------------------update by emily.zhu 2012-05-28 start----------------------------------------
alter table WEBMEMBER_INFO add IP_ADDRESS VARCHAR2 (150);
alter table MEMBER_MEM_INFO add IP_ADDRESS VARCHAR2 (150);
---------------------------------update by emily.zhu 2012-05-28 end----------------------------------------
--********************************* already deploy to product environment (20120528) end ******************************

--********************************* already deploy to product environment (20120614) start ******************************
---------------------------------update by frank 2012-04-17 start-----------------------------------
alter table T_VBP_MEMBER_SCORE_LEVEL_INFO add constraint UNI_VBP_MEMBER_SCORE_LEVEL unique (MEM_NUM);
---------------------------------update by frank 2012-04-17 end-------------------------------------
---------------------------------update by sam.hu 2012-06-5 start-----------------------------------------------
update webmember_info set regist_channel='Mobile' where regist_channel='MOBILE';
update webmember_info set regist_channel='Website' where regist_channel='WEB';
---------------------------------update by sam.hu 2012-06-5 end-----------------------------------------------
--********************************* already deploy to product environment (20120614) end ******************************


--********************************* already deploy to product environment (20120629) start ******************************
------------------------------emily.zhu start at 2012-06-19 ---------------------------------------------------------------------------------------------------------
 Create Table webmember_verify_nm_20120619 As (Select v.* From webmember_verify v ,webmember_info i Where i.id= v.mem_info_id and i.mem_type='NORMAL' );
	 
 delete webmember_verify where  mem_info_id in(select id from webmember_info where  mem_type='NORMAL');
	 
 ---rollback:
MERGE INTO webmember_verify  v 
USING webmember_verify_nm_20120619   vb
ON (v.id =vb.id) 
WHEN not MATCHED THEN
INSERT (ID,MEM_INFO_ID,PASSWORD,MEN_NAME) VALUES (vb.id,vb.mem_info_id,vb.password,vb.men_name); 
------------------------------emily.zhu   end at 2012-06-19 ---------------------------------------------------------------------------------------------------------
--********************************* already deploy to product environment (20120629) end ******************************

--********************************* already deploy to product environment (20120726) start ******************************
------------------------------jacky.chen   start at 2012-06-27 ---------------------------------------------------------------------------------------------------------
ALTER   TABLE   T_MEMBER_CARD_ORDER   ADD MC_MEMBER_CODE   VARCHAR2(50);
UPDATE T_MEMBER_CARD_ORDER T SET T.MC_MEMBER_CODE=(SELECT B.MC_MEMBER_CODE FROM MEMBER_MEM_INFO B WHERE B.MEM_NUM = T.MEMBER_ID)
WHERE EXISTS (SELECT B.MC_MEMBER_CODE FROM MEMBER_MEM_INFO B WHERE B.MEM_NUM = T.MEMBER_ID and T.MC_MEMBER_CODE IS NULL);
------------------------------jacky.chen   end at 2012-06-27 ---------------------------------------------------------------------------------------------------------

--------------------------------ALTER by jack.shi 2012-07-24 start-----------------------------------
ALTER TABLE T_VBP_TARGET_PROMOTION ORDER_ID  VARCHAR2(200);
--------------------------------ALTER by jack.shi 2012-07-24 end-----------------------------------
--********************************* already deploy to product environment (20120726) end ******************************

------------------------------jacky.chen   start at 2012-07-26 ---------------------------------------------------------------------------------------------------------
-- Drop columns 
alter table T_MEMBER_CARD_ORDER drop column MEMBER_ID;
------------------------------jacky.chen   end at 2012-07-26 ---------------------------------------------------------------------------------------------------------

--********************************* already deploy to product environment (20120913) start ******************************
------------------------------cilin.xiao && jack.shi   start at 2012-08-17 ---------------------------------------------------------------------------------------------------------
--DROP TABLE T_VBP_COMMONLY_USED_CONTACT;
--DROP SEQUENCE S_VBP_COMMONLY_USED_CONTACT;

--DROP sequence T_S_VBP_COMMONLY_USED_CONTACT;
--DROP TABLE T_T_VBP_COMMONLY_USED_CONTACT;

--创建常用联系人表，索引
CREATE SEQUENCE S_VBP_COMMONLY_USED_CONTACT INCREMENT BY 1 START WITH 10000;
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


--创建临时常用联系人表，索引
CREATE SEQUENCE T_S_VBP_COMMONLY_USED_CONTACT INCREMENT BY 1 START WITH 10000;
CREATE   GLOBAL   TEMPORARY   TABLE   T_T_VBP_COMMONLY_USED_CONTACT
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
  CONSTRAINT PK_T_T_VBP_C_USED_CONTACT PRIMARY KEY (ID)
)ON   COMMIT   DELETE   ROWS; 

--copy旅游订单游客信息到临时常用联系人表中
INSERT INTO T_T_VBP_COMMONLY_USED_CONTACT
   (ID,
    MC_MEMBER_CODE,
    NAME,
    IDENTITY_TYPE,
    IDENTITY_NO,
    BIRTHDAY,
    PHONE,
    GENDER,
    CARD_NO
    )
   SELECT T_S_VBP_COMMONLY_USED_CONTACT.NEXTVAL,
          r1.mc_member_code,
          g.name,
          g.certification_type,
          g.certification_number,
          g.birthday,
          g.mobile,
          g.sex,
          g.membercardno
     FROM T_TBP_GUEST g, T_TBP_RESERVATION r1
    WHERE g.reservation_id IN
          (SELECT r.id
             FROM t_tbp_reservation r
            WHERE r.mc_member_code IS NOT NULL)
      AND r1.id = g.reservation_id;
	  
--将临时常用联系人表数据copy到正式常用联系人表中
INSERT INTO T_VBP_COMMONLY_USED_CONTACT
   (ID,
    MC_MEMBER_CODE,
    NAME,
    IDENTITY_TYPE,
    IDENTITY_NO,
    BIRTHDAY,
    PHONE,
    GENDER,
    CARD_NO
    )
SELECT S_VBP_COMMONLY_USED_CONTACT.NEXTVAL,
          g.mc_member_code,
          g.name,
          g.identity_type,
          g.identity_no,
          g.birthday,
          g.phone,
          g.gender,
          g.card_no
     FROM T_T_VBP_COMMONLY_USED_CONTACT g 
	WHERE g.id IN (SELECT MIN(a.id) FROM T_T_VBP_COMMONLY_USED_CONTACT a GROUP BY a.name);

--删除掉临时常用联系人表
DROP TABLE T_T_VBP_COMMONLY_USED_CONTACT;
DROP SEQUENCE T_S_VBP_COMMONLY_USED_CONTACT;
------------------------------cilin.xiao && jack.shi   end at 2012-08-17 ---------------------------------------------------------------------------------------------------------
--********************************* already deploy to product environment (20120913) end ******************************

--********************************* already deploy to product environment (20121031) start ******************************
--------------chengqiang.liu & cilin.xiao modify WEBMEMBER_INFO start---------------------------------
alter table WEBMEMBER_INFO modify EMAIL null;
--------------chengqiang.liu & cilin.xiao modify WEBMEMBER_INFO end-------------------------------------
--********************************* already deploy to product environment (20121031) end ******************************

--********************************* already deploy to product environment (20121115) start ******************************
---------------------------------update by emily.zhu 2012-11-01 start--------------------------------
alter table T_MEMBER_XML add COUPON_CODE VARCHAR(12);
---------------------------------update by emily.zhu 2012-11-01 end----------------------------------
--********************************* already deploy to product environment (20121115) end ******************************

--********************************* already deploy to product environment (20121129) start ******************************
------------------------------jack.shi && cilin.xiao 2012-11-20 start------------
ALTER TABLE WEBMEMBER_INFO ADD TRANSFORM_TYPE VARCHAR2(20) DEFAULT 'UNKNOWN' NOT NULL; 
------------------------------jack.shi && cilin.xiao 2012-11-20 end------------
--********************************* already deploy to product environment (20121129) end ******************************



----------------------------update by cilin.xiao && xingxing.zhang  2012-12-17 start----------
--ALTER TABLE WEBMEMBER_VERIFY DROP CONSTRAINT UNI_MEN_NAME_WEBMEMBER_V;

CREATE UNIQUE INDEX IDX_UNI_WEB_V_MEN_NAME ON WEBMEMBER_VERIFY (LOWER(MEN_NAME));
----------------------------update by cilin.xiao && xingxing.zhang  2012-12-17 start----------

-------update by cilin.xiao && evan.du 2013-01-22 start --------
--已经执行,只做release notes的记录
create index idx_mms_name_idno on jje_admin.MEMBER_MEM_INFO M (name,(UPPER(M.IDENTITY_NO)) Tablespace jje_20m_its 
-------update by cilin.xiao && evan.du 2013-01-22 end --------

--********************************* already deploy to product environment (20130131) end ******************************


--********************************* already deploy to product environment (20130314) start ******************************
--------update by xingxing.zhang && cilin.xiao 2013-01-28 start ----------------------

CREATE SEQUENCE S_VBP_BIG_CUSTOMER_ACCOUNT INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_VBP_BIG_CUSTOMER INCREMENT BY 1 START WITH 10000;

CREATE TABLE T_VBP_BIG_CUSTOMER_ACCOUNT
(
  ID    NUMBER  NOT NULL,
  USERNAME    VARCHAR2(150) NOT NULL,
  PASSWORD    VARCHAR2(100),
  CREATE_DATE DATE DEFAULT SYSDATE,
  UPDATE_DATE DATE DEFAULT SYSDATE,
  CONSTRAINT PK_T_VBP_BIG_CUSTOMER_ACCOUNT PRIMARY KEY (ID)
);

CREATE TABLE T_VBP_BIG_CUSTOMER 
(
  ID    NUMBER  NOT NULL,
  NAME    VARCHAR2(150) NOT NULL,
  ACCOUNT_ID    NUMBER  NOT NULL,
  CHANNEL  VARCHAR2(150) NOT  NULL,
  CONTACT_NAME  VARCHAR2(150),
  EMAIL  VARCHAR2(30) ,
  PHONE  VARCHAR2(20) ,
  CREATE_DATE DATE DEFAULT SYSDATE,
  UPDATE_DATE DATE DEFAULT SYSDATE,
  CONSTRAINT PK_T_VBP_BIG_CUSTOMER PRIMARY KEY (ID),
  CONSTRAINT FK_T_VBP_BIG_CUSTOMER_ACCOUNT FOREIGN KEY (ACCOUNT_ID) REFERENCES T_VBP_BIG_CUSTOMER_ACCOUNT(ID)
);

--大客户初始化的账户信息
insert into T_VBP_BIG_CUSTOMER_ACCOUNT (id,username,password) values(10000,'HKO','73FA7A241B152292416AE228864043B8');

insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10000,'港锦旅_子客户1',10000,'HKRO_SUBCLIENTS01','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10001,'港锦旅_子客户2',10000,'HKRO_SUBCLIENTS02','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10002,'Shanghai Tourism(HK)Co.Ltd',10000,'TOURISM','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10003,'Kings Travel Service Ltd',10000,'KING_TRAVEL','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10004,'Terumo China(HK)Ltd',10000,'TERUMO_CHINA','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10005,'YekoTrading Ltd',10000,'YEKO_TRADING','','','');
--------update by xingxing.zhang && cilin.xiao 2013-01-28 end -----=======---- 
--大客户初始化的账户信息
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10006,'A2A Capital Management Ltd',10000,'A2A','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10007,'Basell Asia Pacific Ltd',10000,'BASELL_ASIA_PACIFIC','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10008,'Chinatrust Commercial Bank, Ltd',10000,'CHINATRUST_COMMERCIAL_BANK','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10009,'China Merchants Holdings (Intl) Ltd',10000,'MERCHANTS_HOLDINGS','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10010,'Deloitte Touche Tohmatsu',10000,'DELOITTE_TOUCHE_TOHMATSU','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10011,'Edge Design',10000,'EDGE','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10012,'Gofront Holding Ltd',10000,'GOFRONT_HOLDING','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10013,'Hoi Tung Marine Machinery Suppliers Ltd',10000,'HOI_TUNG_MARINE_MACHINERY_SUPPLIERS','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10014,'Inthere Ltd HK',10000,'INTHERE','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10015,'Lei Yue Mun Trading Ltd',10000,'LEIYUEMUN_TRADING','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10016,'Mandeville & Associates Ltd',10000,'MANDEVILLE_ASSOCIATES','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10017,'Portcullis Trustnet (HK) Ltd',10000,'PORTCULLIS_TRUSTNET','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10018,'Safilo Far East Ltd',10000,'SAFILO_FAR_EAST','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10019,'The Bank of New York Mellon',10000,'NEW_YORK_MELLON','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10020,'Tianda Group Ltd',10000,'TIANDA_GROUP','','','');

--------update by xingxing.zhang  2013-03-12 end-----=======---- 
--********************************* already deploy to product environment (20130314) end ******************************

--********************************* already deploy to product environment (20130425) start ******************************
------------------update by chengqiang.liu 2013-03-27 start--------------------
-- Add/modify columns 
alter table MEMBER_MEM_INFO add ACTIVE_DATE TIMESTAMP;
alter table MEMBER_MEM_INFO add ACTIVE_CHANNEL varchar2(30);
------------------update by chengqiang.liu 2013-03-27 end----------------------

------------------update by xingxing.zhang 2013-04-18----------------
    ALTER TABLE t_vbp_big_customer ADD MC_MEMBER_CODE VARCHAR2(150)  ; 
    
    alter table t_vbp_big_customer  MODIFY MC_MEMBER_CODE UNIQUE;
    
    update t_vbp_big_customer b set b.mc_member_code=S_MC_MEMBER_CODE.Nextval
    
------------------update by xingxing.zhang 2013-04-18 end---------------- 
  --********************************* already deploy to product environment (20130425) end ******************************
 
  --********************************* already deploy to product environment (20130509) start ******************************    
------------------update by chengqiang.liu & paul.liu 2013-05-02 start----------------    
alter table T_MEMBER_CARD_ORDER add SALE_CHANNEL varchar2(30);   
comment on column T_MEMBER_CARD_ORDER.SALE_CHANNEL
  is '销售渠道';
------------------update by chengqiang.liu & paul.liu 2013-05-02 end----------------
  --********************************* already deploy to product environment (20130509) end ******************************      
  
  
------------------CREATE by chengqiang 2013-05-09 start----------------      
  -- Create table
create table T_VBP_THIRDPARTY_BIND
(
  ID               number not null,
  MC_MEMBERCODE    varchar2(20),
  MEM_NUM          varchar2(20),
  THIRDPARTY_TYPE  varchar2(50),
  THIRDPARTY_SIGN  varchar2(100),
  THIRDPARTY_LEVEL varchar2(10),
  VIP_FLAG         varchar2(10),
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
  
CREATE SEQUENCE S_VBP_THIRDPARTY_BIND START WITH 10000 INCREMENT BY 1;
------------------CREATE by chengqiang 2013-05-09 end----------------    

------------------update by chengqiang 2013-05-13 start----------------    
alter table MEMBER_MEM_INFO add THIRDPARTY_TYPE VARCHAR2(30);
comment on column MEMBER_MEM_INFO.THIRDPARTY_TYPE
  is '合作类型';
------------------update by chengqiang 2013-05-13 end----------------    
  
 ------------------update by chengqiang 2013-06-08 start----------------
alter table T_VBP_BIG_CUSTOMER add CODE varchar2(50);
alter table T_VBP_BIG_CUSTOMER modify channel VARCHAR2(500);
alter table T_VBP_BIG_CUSTOMER modify channel null;
update T_VBP_BIG_CUSTOMER set code = name;
insert into T_VBP_BIG_CUSTOMER_ACCOUNT (id,username,password) values(10001,'JJE','73FA7A241B152292416AE228864043B8');
insert into T_VBP_BIG_CUSTOMER (id,name,code,account_id,CHANNEL,contact_name,email,phone) values(10021,'锦江员工','JJ Employee',10001,'','','','');

alter table MEMBER_MEM_INFO add M_CUSTOMER_ID varchar2(30);
-- Add comments to the columns 
comment on column MEMBER_MEM_INFO.M_CUSTOMER_ID
  is '大客户ID';
------------------update by chengqiang 2013-06-08 end----------------

------------------update by chengqiang 2013-07-02 start----------------  
alter table T_VBP_BIG_CUSTOMER add CRM_ID VARCHAR2(30);
comment on column T_VBP_BIG_CUSTOMER.CRM_ID
  is 'CRM唯一序号';
  ------------------update by chengqiang 2013-07-02 end----------------


------------------update by urey.jiang 2013-07-09 start----------------
 CREATE TABLE T_VBP_MEMBER_POINTS
(
   ID                   NUMBER NOT NULL,
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
   CONSTRAINT PK_T_VBP_MEMBER_POINTS PRIMARY KEY (ID)
);
CREATE SEQUENCE S_VBP_MEMBER_POINTS START WITH 10000 INCREMENT BY 1;

-- Add comments to the columns
comment on column T_VBP_MEMBER_POINTS.name
  is '会员姓名';
comment on column T_VBP_MEMBER_POINTS.MEM_NUM
  is '会员编号';
comment on column T_VBP_MEMBER_POINTS.card_num
  is '会员卡号';
comment on column T_VBP_MEMBER_POINTS.per_title
  is '会员性别';
comment on column T_VBP_MEMBER_POINTS.email
  is '会员邮箱';
comment on column T_VBP_MEMBER_POINTS.tier
  is '会员层级';
comment on column T_VBP_MEMBER_POINTS.highest_type
  is '享卡级别';
comment on column T_VBP_MEMBER_POINTS.points
  is '可用积分';
comment on column T_VBP_MEMBER_POINTS.point_expiry_dt
  is '积分有效期';
comment on column T_VBP_MEMBER_POINTS.redeemed_points
  is '兑换所用积分';
comment on column T_VBP_MEMBER_POINTS.tier_up_points
  is '距下次升级所需定级积分';
comment on column T_VBP_MEMBER_POINTS.tier_up_times
  is '距下次升级所需定级次数';
comment on column T_VBP_MEMBER_POINTS.to_date
  is '截止日期（2月账单截止日期就为1月31日）';
comment on column T_VBP_MEMBER_POINTS.changed_points
  is '上月积分变动值（获得-兑换）';
comment on column T_VBP_MEMBER_POINTS.aquired_points
  is '上月获得可用积分 ';



  ------------------update by urey.jiang  2013-07-09 end----------------
  
  ------------------update by xingxing.zhang 2013-07-12 start-------------
  CREATE SEQUENCE S_T_VBP_SCORE_TRADE INCREMENT BY 1 START WITH 10000;
  
  CREATE TABLE T_VBP_SCORE_TRADE
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
   REMARK             VARCHAR2(2000),
   CONSTRAINT T_VBP_SCORE_TRADE  PRIMARY KEY (ID)
);


-- Add comments to the columns 
comment on column T_VBP_SCORE_TRADE.ID
  is 'id';
comment on column T_VBP_SCORE_TRADE.ORDER_NO
  is '订单号';
comment on column T_VBP_SCORE_TRADE.TRD_ORDER_NO
  is '积分商城返回的订单号';
comment on column T_VBP_SCORE_TRADE.CREATE_TIME
  is '创建订单时间';
comment on column T_VBP_SCORE_TRADE.MC_MEMBER_CODE
  is '会员唯一标识符';
comment on column T_VBP_SCORE_TRADE.PRODUCT_ID
  is '兑换商品id';
comment on column T_VBP_SCORE_TRADE.TRADE_SCORE
  is '兑换积分值';
comment on column T_VBP_SCORE_TRADE.STATUS
  is '状态';
comment on column T_VBP_SCORE_TRADE.PRODUCT_NAME
  is '兑换商品的名称';
comment on column T_VBP_SCORE_TRADE.EXCHANGE_TYPE
  is '兑换类型';
comment on column T_VBP_SCORE_TRADE.TRANS_ID
  is 'crm交易号';
comment on column T_VBP_SCORE_TRADE.REMAIN_POINT
  is '用户剩余积分';
comment on column T_VBP_SCORE_TRADE.LAST_UPDATE_TIME
  is '最后更新时间';
comment on column T_VBP_SCORE_TRADE.REMARK
  is '备注';
  
--------------------update by xingxing.zhang 2013-07-12 end-----------------



  ------------------update by urey.jiang  2013-07-16 start----------------
    CREATE TABLE T_VBP_CONFIG
(
   ID                   NUMBER NOT NULL,
   KEY                  VARCHAR2 (100),
   TYPE                 VARCHAR2(100),
   VALUE                VARCHAR2(2000),
   CONSTRAINT PK_T_VBP_CONFIG PRIMARY KEY (ID)
);
CREATE SEQUENCE S_VBP_CONFIG START WITH 10000 INCREMENT BY 1;
  alter table T_VBP_CONFIG add constraint keyl_unique unique(key);

  ------------------update by urey.jiang  2013-07-16 end----------------

insert into T_VBP_BIG_CUSTOMER (id,name,code,account_id,CHANNEL,contact_name,email,phone,MC_MEMBER_CODE) values(S_VBP_BIG_CUSTOMER.NEXTVAL,'上海城投集团','上海城投集团','','SHCTJT','冯东华','syf@chengtou.com','64338222-3364','628371');

insert into T_VBP_BIG_CUSTOMER (id,name,code,account_id,CHANNEL,contact_name,email,phone,MC_MEMBER_CODE) values(S_VBP_BIG_CUSTOMER.NEXTVAL,'糖酒','糖酒','','SHTJJT','茆敏','maom@sscw.com.cn','64188188-530','628367');


---------------------------------update by Frank 2013-7-26 start-----------------------------------

CREATE SEQUENCE S_T_MEMBER_CARD_ORDER_HISTORY INCREMENT BY 1 START WITH 1;

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

comment on table T_MEMBER_CARD_ORDER_HISTORY
  is '会员卡订单历史记录表';
comment on column T_MEMBER_CARD_ORDER_HISTORY.log_id
  is '历史记录ID';
comment on column T_MEMBER_CARD_ORDER_HISTORY.order_id
  is '订单ID';
comment on column T_MEMBER_CARD_ORDER_HISTORY.order_no
  is '订单号';
comment on column T_MEMBER_CARD_ORDER_HISTORY.order_time
  is '订单时间';
comment on column T_MEMBER_CARD_ORDER_HISTORY.create_time
  is '创建时间';
comment on column T_MEMBER_CARD_ORDER_HISTORY.card_no
  is '卡号';
comment on column T_MEMBER_CARD_ORDER_HISTORY.order_type
  is '订单类型';
comment on column T_MEMBER_CARD_ORDER_HISTORY.current_level
  is '现在卡级别';
comment on column T_MEMBER_CARD_ORDER_HISTORY.next_level
  is '下一个级别';
comment on column T_MEMBER_CARD_ORDER_HISTORY.amount
  is '数量';
comment on column T_MEMBER_CARD_ORDER_HISTORY.pay_time
  is '支付时间';
comment on column T_MEMBER_CARD_ORDER_HISTORY.payment_no
  is '支付号';
comment on column T_MEMBER_CARD_ORDER_HISTORY.pay_type
  is '支付类型';
comment on column T_MEMBER_CARD_ORDER_HISTORY.payment_vender
  is '支付供应商';
comment on column T_MEMBER_CARD_ORDER_HISTORY.bank_code
  is '银行代码';
comment on column T_MEMBER_CARD_ORDER_HISTORY.pay_status
  is '支付状态';
comment on column T_MEMBER_CARD_ORDER_HISTORY.status
  is '订单状态';
comment on column T_MEMBER_CARD_ORDER_HISTORY.refund_amount
  is '退款金额';
comment on column T_MEMBER_CARD_ORDER_HISTORY.sale_channel
  is '会员唯一标识符';
comment on column T_MEMBER_CARD_ORDER_HISTORY.mc_member_code
  is '销售渠道';
comment on column T_MEMBER_CARD_ORDER_HISTORY.operation_type
  is '操作类型';
comment on column T_MEMBER_CARD_ORDER_HISTORY.operation_time
  is '操作时间';
comment on column T_MEMBER_CARD_ORDER_HISTORY.remark
  is '操作备注';

---------------------------------update by Frank 2013-7-26 end-------------------------------------

---------------------------------update by evan.du 2013-7-30 start-------------------------------------

update jje_admin.t_vbp_big_customer  b  set b.channel='JJEMP' where id='10021'


create index IDX_MMV_MEM_NAME on JJE_ADMIN.MEMBER_VERIFY (LOWER(MEN_NAME));
create index IDX_UNI_WEB_V_MEN_id on jje_admin.WEBMEMBER_VERIFY(MEM_INFO_ID) ;
create index IDX_UNI_WEB_i_email on JJE_ADMIN.WEBMEMBER_INFO(LOWER(EMAIL)) ;


---------------------------------update by evan.du 2013-7-30 end-------------------------------------

---------------------------------update by Frank 2013-9-22 start-----------------------------------
CREATE SEQUENCE S_T_VBP_SCORE_TRADE_LOG INCREMENT BY 1 START WITH 10000;
CREATE SEQUENCE S_T_VBP_SCORE_TRADE_ORDER INCREMENT BY 1 START WITH 10000;
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

comment on column T_VBP_SCORE_TRADE_ORDER.ID
  is 'id';
comment on column T_VBP_SCORE_TRADE_ORDER.ORDER_NO
  is '订单号';
comment on column T_VBP_SCORE_TRADE_ORDER.TRD_ORDER_NO
  is '积分商城返回的订单号';
comment on column T_VBP_SCORE_TRADE_ORDER.CREATE_TIME
  is '创建订单时间';
comment on column T_VBP_SCORE_TRADE_ORDER.MC_MEMBER_CODE
  is '会员唯一标识符';
comment on column T_VBP_SCORE_TRADE_ORDER.PRODUCT_ID
  is '兑换商品id';
comment on column T_VBP_SCORE_TRADE_ORDER.TRADE_SCORE
  is '兑换积分值';
comment on column T_VBP_SCORE_TRADE_ORDER.STATUS
  is '状态';
comment on column T_VBP_SCORE_TRADE_ORDER.PRODUCT_NAME
  is '兑换商品的名称';
comment on column T_VBP_SCORE_TRADE_ORDER.EXCHANGE_TYPE
  is '兑换类型';
comment on column T_VBP_SCORE_TRADE_ORDER.TRANS_ID
  is 'crm交易号';
comment on column T_VBP_SCORE_TRADE_ORDER.REMAIN_POINT
  is '用户剩余积分';
comment on column T_VBP_SCORE_TRADE_ORDER.LAST_UPDATE_TIME
  is '最后更新时间';

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

comment on column T_VBP_SCORE_TRADE_LOG.ID
  is 'id';
comment on column T_VBP_SCORE_TRADE_LOG.ORDER_NO
  is '订单号';
comment on column T_VBP_SCORE_TRADE_LOG.TRD_ORDER_NO
  is '积分商城返回的订单号';
comment on column T_VBP_SCORE_TRADE_LOG.CREATE_TIME
  is '创建订单时间';
comment on column T_VBP_SCORE_TRADE_LOG.MC_MEMBER_CODE
  is '会员唯一标识符';
comment on column T_VBP_SCORE_TRADE_LOG.PRODUCT_ID
  is '兑换商品id';
comment on column T_VBP_SCORE_TRADE_LOG.TRADE_SCORE
  is '兑换积分值';
comment on column T_VBP_SCORE_TRADE_LOG.STATUS
  is '状态';
comment on column T_VBP_SCORE_TRADE_LOG.PRODUCT_NAME
  is '兑换商品的名称';
comment on column T_VBP_SCORE_TRADE_LOG.EXCHANGE_TYPE
  is '兑换类型';
comment on column T_VBP_SCORE_TRADE_LOG.TRANS_ID
  is 'crm交易号';
comment on column T_VBP_SCORE_TRADE_LOG.REMAIN_POINT
  is '用户剩余积分';
comment on column T_VBP_SCORE_TRADE_LOG.LAST_UPDATE_TIME
  is '最后更新时间';
comment on column T_VBP_SCORE_TRADE_LOG.REMARK
  is '备注';

insert into T_VBP_SCORE_TRADE_LOG
(
   ID              ,
   ORDER_NO        ,
   TRD_ORDER_NO    ,
   CREATE_TIME     ,
   MC_MEMBER_CODE  ,
   PRODUCT_ID      ,
   TRADE_SCORE     ,
   STATUS          ,
   PRODUCT_NAME    ,
   EXCHANGE_TYPE   ,
   TRANS_ID        ,
   REMAIN_POINT    ,
   LAST_UPDATE_TIME,
   REMARK          
)
select S_T_VBP_SCORE_TRADE_LOG.NEXTVAL,
t.order_no,
t.trd_order_no,
t.create_time,
t.mc_member_code,
t.product_id,
t.trade_score,
t.status,
t.product_name,
t.exchange_type,
t.trans_id,
t.remain_point,
t.last_update_time,
t.remark
from T_VBP_SCORE_TRADE t
---------------------------------update by Frank 2013-9-22 end-------------------------------------

---------------------------------update by Frank 2013-9-27 start-----------------------------------

alter table T_VBP_THIRDPARTY_BIND add orig_level VARCHAR2(10);
alter table T_VBP_THIRDPARTY_BIND add dest_level VARCHAR2(10);
alter table T_VBP_THIRDPARTY_BIND add register_flag VARCHAR2(10);
alter table T_VBP_THIRDPARTY_BIND add score_flag VARCHAR2(10);
alter table T_VBP_THIRDPARTY_BIND add coupon_flag VARCHAR2(10);
 
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
  
---------------------------------update by Frank 2013-9-27 end-------------------------------------

--------update by urey.jiang 2013-10-15  start-----=======----
alter table WEBMEMBER_INFO add REGIST_TAG varchar2(300);
alter table MEMBER_MEM_INFO add REGIST_TAG VARCHAR2(300);
comment on column MEMBER_MEM_INFO.REGIST_TAG
  is '注册标签';

  alter table MEMBER_MEM_INFO add ACTIVE_TAG VARCHAR2(300);
comment on column MEMBER_MEM_INFO.ACTIVE_TAG
  is '激活标签';
--------update by urey.jiang 2013-10-15  end-----=======----


--------update by urey.jiang 2014-1-2  start-----=======----
CREATE SEQUENCE S_VBP_BIG_CUSTOMER_MIDDLE START WITH 10000 INCREMENT BY 1;

CREATE TABLE T_VBP_BIG_CUSTOMER_MIDDLE
(
  ID    NUMBER  NOT NULL,
  ACCOUNT_ID    NUMBER  NOT NULL,
  BIG_CUSTOMER_ID    NUMBER  NOT NULL,
  CONSTRAINT PK_T_VBP_BIG_CUSTOMER_MIDDLE   PRIMARY KEY (ID)
);


insert into t_vbp_big_customer_account (ID, USERNAME, PASSWORD, CREATE_DATE, UPDATE_DATE)
values (10003, 'JJHotels_IT', 'AABEE8A184C798EAC5258FA3888B5062', sysdate, sysdate);
--------update by urey.jiang 2014-1-2  end-----=======----

---------------------------------update by Zhang 2014-1-29 start-----------------------------------
CREATE SEQUENCE S_VBP_OP_RECORD_LOG START WITH 10000 INCREMENT BY 1;
CREATE TABLE T_VBP_OP_RECORD_LOG
(
	ID 			NUMBER NOT NULL,
	OP_TYPE 	VARCHAR2(100),
	MESSAGE 	VARCHAR2(500),
	CONTENT 	VARCHAR2(2000),
	CREATE_DATE	TIMESTAMP,
	UPDATE_DATE	TIMESTAMP,
	CONSTRAINT PK_T_VBP_OP_RECORD_LOG PRIMARY KEY (ID)
)
---------------------------------update by Zhang 2014-1-29 end-----------------------------------


---------------------------------update by Carter 2014-4-14 start-----------------------------------

CREATE TABLE T_VBP_VAILDATE_CODE
(
  ID          NUMBER NOT NULL,
  CODE        VARCHAR2(10) NOT NULL,
  RECEIVER    VARCHAR2(100) NOT NULL,
  CREATE_DATE DATE NOT NULL
)

alter table T_VBP_VAILDATE_CODE
  add constraint PK_VBP_VAILDATE_CODE primary key (ID)
  
  
CREATE SEQUENCE S_VBP_VAILDATE_CODE  INCREMENT BY 1   START WITH 1;  
---------------------------------update by Carter 2014-4-14 end-----------------------------------



---------------------------------update by Carter 2014-05-01 start-----------------------------------
alter table MEMBER_MEM_INFO add NEW_MEM_TIER VARCHAR2(100);
comment on column MEMBER_MEM_INFO.NEW_MEM_TIER is '新会员层级';

alter table MEMBER_MEM_INFO modify (MEM_TIER null);
---------------------------------update by Carter 2014-05-01 end------------------------------------

---------------------------------update by jack.shi 2014-04-30 start-----------------------------------
--新会员层级价格数据
insert into S_LST_OF_VAL (ROW_ID,CREATED,CREATED_BY,LAST_UPD,LAST_UPD_BY,DCKING_NUM,MODIFICATION_NUM,CONFLICT_ID,PAR_ROW_ID,ACTIVE_FLG,DFLT_LIC_FLG,LANG_ID,MLTORG_DISALW_FLG,MULTI_LINGUAL_FLG,NAME,REQD_LIC_FLG,RPLCTN_LVL_CD,TYPE,VAL,MODIFIABLE_FLG,DB_LAST_UPD,ORDER_BY,TARGET_HIGH,TARGET_LOW,TRANSLATE_FLG,WEIGHTING_FACTOR,BITMAP_ID,BU_ID,CODE,DB_LAST_UPD_SRC,DESC_TEXT,HIGH,INTEGRATION_ID,LOW,SUB_TYPE) values ('1-PR-12',TIMESTAMP '2011-5-16 0:00:00.000000','0-1',TIMESTAMP '2011-5-16 0:00:00.000000','0-1',0,8,'0',null,'Y','N','CHS','N','Y','BuyGold','N','All','PRICE_LEVEL','0.01','Y',TIMESTAMP '2011-5-16 0:00:00.000000',12,null,null,'Y',null,null,null,null,'User','EIM',null,null,null,null);
insert into S_LST_OF_VAL (ROW_ID,CREATED,CREATED_BY,LAST_UPD,LAST_UPD_BY,DCKING_NUM,MODIFICATION_NUM,CONFLICT_ID,PAR_ROW_ID,ACTIVE_FLG,DFLT_LIC_FLG,LANG_ID,MLTORG_DISALW_FLG,MULTI_LINGUAL_FLG,NAME,REQD_LIC_FLG,RPLCTN_LVL_CD,TYPE,VAL,MODIFIABLE_FLG,DB_LAST_UPD,ORDER_BY,TARGET_HIGH,TARGET_LOW,TRANSLATE_FLG,WEIGHTING_FACTOR,BITMAP_ID,BU_ID,CODE,DB_LAST_UPD_SRC,DESC_TEXT,HIGH,INTEGRATION_ID,LOW,SUB_TYPE) values ('1-PR-13',TIMESTAMP '2011-5-16 0:00:00.000000','0-1',TIMESTAMP '2011-5-16 0:00:00.000000','0-1',0,8,'0',null,'Y','N','CHS','N','Y','BuyPlatinum','N','All','PRICE_LEVEL','0.01','Y',TIMESTAMP '2011-5-16 0:00:00.000000',12,null,null,'Y',null,null,null,null,'User','EIM',null,null,null,null);
insert into S_LST_OF_VAL (ROW_ID,CREATED,CREATED_BY,LAST_UPD,LAST_UPD_BY,DCKING_NUM,MODIFICATION_NUM,CONFLICT_ID,PAR_ROW_ID,ACTIVE_FLG,DFLT_LIC_FLG,LANG_ID,MLTORG_DISALW_FLG,MULTI_LINGUAL_FLG,NAME,REQD_LIC_FLG,RPLCTN_LVL_CD,TYPE,VAL,MODIFIABLE_FLG,DB_LAST_UPD,ORDER_BY,TARGET_HIGH,TARGET_LOW,TRANSLATE_FLG,WEIGHTING_FACTOR,BITMAP_ID,BU_ID,CODE,DB_LAST_UPD_SRC,DESC_TEXT,HIGH,INTEGRATION_ID,LOW,SUB_TYPE) values ('1-PR-14',TIMESTAMP '2011-5-16 0:00:00.000000','0-1',TIMESTAMP '2011-5-16 0:00:00.000000','0-1',0,9,'0',null,'Y','N','CHS','N','Y','Invite To Black','N','All','PRICE_LEVEL','0.01','Y',TIMESTAMP '2011-5-16 0:00:00.000000',13,null,null,'Y',null,null,null,null,'User','EIM',null,null,null,null);
insert into S_LST_OF_VAL (ROW_ID,CREATED,CREATED_BY,LAST_UPD,LAST_UPD_BY,DCKING_NUM,MODIFICATION_NUM,CONFLICT_ID,PAR_ROW_ID,ACTIVE_FLG,DFLT_LIC_FLG,LANG_ID,MLTORG_DISALW_FLG,MULTI_LINGUAL_FLG,NAME,REQD_LIC_FLG,RPLCTN_LVL_CD,TYPE,VAL,MODIFIABLE_FLG,DB_LAST_UPD,ORDER_BY,TARGET_HIGH,TARGET_LOW,TRANSLATE_FLG,WEIGHTING_FACTOR,BITMAP_ID,BU_ID,CODE,DB_LAST_UPD_SRC,DESC_TEXT,HIGH,INTEGRATION_ID,LOW,SUB_TYPE) values ('1-PR-15',TIMESTAMP '2011-5-16 0:00:00.000000','0-1',TIMESTAMP '2011-5-16 0:00:00.000000','0-1',0,8,'0',null,'Y','N','CHS','N','Y','RechargeGold','N','All','PRICE_LEVEL','0.01','Y',TIMESTAMP '2011-5-16 0:00:00.000000',14,null,null,'Y',null,null,null,null,'User','EIM',null,null,null,null);
insert into S_LST_OF_VAL (ROW_ID,CREATED,CREATED_BY,LAST_UPD,LAST_UPD_BY,DCKING_NUM,MODIFICATION_NUM,CONFLICT_ID,PAR_ROW_ID,ACTIVE_FLG,DFLT_LIC_FLG,LANG_ID,MLTORG_DISALW_FLG,MULTI_LINGUAL_FLG,NAME,REQD_LIC_FLG,RPLCTN_LVL_CD,TYPE,VAL,MODIFIABLE_FLG,DB_LAST_UPD,ORDER_BY,TARGET_HIGH,TARGET_LOW,TRANSLATE_FLG,WEIGHTING_FACTOR,BITMAP_ID,BU_ID,CODE,DB_LAST_UPD_SRC,DESC_TEXT,HIGH,INTEGRATION_ID,LOW,SUB_TYPE) values ('1-PR-16',TIMESTAMP '2011-5-16 0:00:00.000000','0-1',TIMESTAMP '2011-5-16 0:00:00.000000','0-1',0,8,'0',null,'Y','N','CHS','N','Y','RechargePlatinum','N','All','PRICE_LEVEL','0.01','Y',TIMESTAMP '2011-5-16 0:00:00.000000',14,null,null,'Y',null,null,null,null,'User','EIM',null,null,null,null);
insert into S_LST_OF_VAL (ROW_ID,CREATED,CREATED_BY,LAST_UPD,LAST_UPD_BY,DCKING_NUM,MODIFICATION_NUM,CONFLICT_ID,PAR_ROW_ID,ACTIVE_FLG,DFLT_LIC_FLG,LANG_ID,MLTORG_DISALW_FLG,MULTI_LINGUAL_FLG,NAME,REQD_LIC_FLG,RPLCTN_LVL_CD,TYPE,VAL,MODIFIABLE_FLG,DB_LAST_UPD,ORDER_BY,TARGET_HIGH,TARGET_LOW,TRANSLATE_FLG,WEIGHTING_FACTOR,BITMAP_ID,BU_ID,CODE,DB_LAST_UPD_SRC,DESC_TEXT,HIGH,INTEGRATION_ID,LOW,SUB_TYPE) values ('1-PR-17',TIMESTAMP '2011-5-16 0:00:00.000000','0-1',TIMESTAMP '2011-5-16 0:00:00.000000','0-1',0,8,'0',null,'Y','N','CHS','N','Y','UpgradePlatinum','N','All','PRICE_LEVEL','0.01','Y',TIMESTAMP '2011-5-16 0:00:00.000000',14,null,null,'Y',null,null,null,null,'User','EIM',null,null,null,null);
---------------------------------update by jack.shi 2014-04-30 end------------------------------------

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
)

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
------------------------------------------------------frank.zhang 2014-08-26--
-----xiaolei.yang 2014-08-29-----
alter table T_VBP_MEMBER_TAOBAO_BINDING add TAOBAO_LEVEL VARCHAR2(100);
comment on column T_VBP_MEMBER_TAOBAO_BINDING.TAOBAO_LEVEL is '淘宝等级';
-----xiaolei.yang 2014-08-29-----


--xitong.yan 2014-12-02-------------------------------------------------------
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
comment on column T_VBP_MEMBER_BIND_INFO.upadte_time is '更新时间';

CREATE SEQUENCE member_bind_sequence increment by 1 start with 10000;
-------------------------------------------------------xitong.yan 2014-12-02--

