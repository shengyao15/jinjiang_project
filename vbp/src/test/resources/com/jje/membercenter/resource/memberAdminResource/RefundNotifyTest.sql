delete from t_member_card_order  where  id in (203);
insert into t_member_card_order (ID, ORDER_NO, ORDER_TIME, CREATE_TIME, CARD_NO, ORDER_TYPE, CURRENT_LEVEL, NEXT_LEVEL, AMOUNT, PAY_TIME, PAYMENT_NO, PAY_TYPE, PAYMENT_VENDER, BANK_CODE, PAY_STATUS, STATUS, REFUND_AMOUNT, MC_MEMBER_CODE)
values (203, 'V190a8cde', TIMESTAMP'2012-08-05 00:00:00', TIMESTAMP'2012-06-05 00:00:00', '1012', 'MEMCARD', 'J Benefit Card', '', 1000, null, '', '', '', '', 1, 2, 0, '');
commit;
