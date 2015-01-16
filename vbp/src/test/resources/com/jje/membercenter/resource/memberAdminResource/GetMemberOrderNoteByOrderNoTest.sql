delete from t_member_order_note  where  id in (1201);
insert into t_member_order_note (ID, ORDER_NO, CONTENT, OPERATION, USER_ID, USER_NAME, REASON, CREATE_TIME)
values ('1201', '14123', '', 'admin', '1000', '1000', '', TIMESTAMP'2012-08-05 00:00:00');
commit;
