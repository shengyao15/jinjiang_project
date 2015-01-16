delete from T_VBP_BIG_CUSTOMER where account_id = 10086;
delete from T_VBP_BIG_CUSTOMER_ACCOUNT where id = 10086;


insert into T_VBP_BIG_CUSTOMER_ACCOUNT (id,username,password) values(10086,'HKO','73FA7A241B152292416AE228864043B8');

insert into T_VBP_BIG_CUSTOMER (id,name,account_id,CHANNEL,contact_name,email,phone,crm_id) values(10086,'港锦旅_子客户1',10086,'JJEMP','','','','10086');
commit;