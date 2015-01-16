delete from T_VBP_BIG_CUSTOMER where account_id = 10000;
delete from T_VBP_BIG_CUSTOMER_ACCOUNT where id = 10000;
delete from  T_VBP_BIG_CUSTOMER_MIDDLE;


insert into T_VBP_BIG_CUSTOMER_ACCOUNT (id,username,password) values(10000,'HKO','73FA7A241B152292416AE228864043B8');

insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10000,'港锦旅_子客户1',10000,'HKRO_SUBCLIENTS01','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10001,'港锦旅_子客户2',10000,'HKRO_SUBCLIENTS02','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10002,'Shanghai Tourism(HK)Co.Ltd',10000,'TOURISM','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10003,'Kings Travel Service Ltd',10000,'KING_TRAVEL','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10004,'Terumo China(HK)Ltd',10000,'TERUMO_CHINA','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone) values(10005,'YekoTrading Ltd',10000,'YEKO_TRADING','','','');
insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone,crm_id) values(10006,'锦江员工',10000,'AVATAR_OTA|JJEMP|AVATAR|WWW','','','','100');
insert into T_VBP_BIG_CUSTOMER_MIDDLE (id,account_id,big_customer_id) values (10001,10000,10000);
insert into T_VBP_BIG_CUSTOMER_MIDDLE (id,account_id,big_customer_id) values (10002,10000,10001);
insert into T_VBP_BIG_CUSTOMER_MIDDLE (id,account_id,big_customer_id) values (10003,10000,10002);
insert into T_VBP_BIG_CUSTOMER_MIDDLE (id,account_id,big_customer_id) values (10004,10000,10003);
insert into T_VBP_BIG_CUSTOMER_MIDDLE (id,account_id,big_customer_id) values (10005,10000,10004);
insert into T_VBP_BIG_CUSTOMER_MIDDLE (id,account_id,big_customer_id) values (10006,10000,10005);
insert into T_VBP_BIG_CUSTOMER_MIDDLE (id,account_id,big_customer_id) values (10007,10000,10006) ;
commit;


