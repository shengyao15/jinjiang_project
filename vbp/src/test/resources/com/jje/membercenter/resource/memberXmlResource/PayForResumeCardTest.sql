delete from T_MEMBER_XML where id= '2ac51d6ac4cc41e9925bce2f81c2244d';
delete from T_MEMBER_CARD_ORDER where id = 10086;

insert into T_MEMBER_XML (ID, EMAIL, PHONE_NUM, ORDER_NO, CERTIFICATE_TYPE, CERTIFICATE_NO, XML, CALL_BACK_FLAG, INSERTED_BY, INSERTED_DATE, UPDATED_BY, UPDATED_DATE)
values ('2ac51d6ac4cc41e9925bce2f81c2244d', '10086@v.cc', '123123', '10086', 'Others', '10086', '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><resumeCardDto><action>Recharge J</action><membid>1-75352922</membid><memcardno>10086</memcardno></resumeCardDto>', 'N', 'System', sysdate, 'System', sysdate);


insert into T_MEMBER_CARD_ORDER (ID, ORDER_NO, ORDER_TIME, CREATE_TIME, CARD_NO, ORDER_TYPE, CURRENT_LEVEL, NEXT_LEVEL, AMOUNT, PAY_TIME, PAYMENT_NO, PAY_TYPE, PAYMENT_VENDER, BANK_CODE, PAY_STATUS, STATUS, REFUND_AMOUNT, MC_MEMBER_CODE)
values (10086, '10086', sysdate, sysdate, '10086', 'MEMCARD', 'J Benefit Card', '', 0.01, sysdate, '2012080814358115', '', 'ALIPAY', '', 1, 2, 0, '');