insert into MEMBER_MEM_INFO(mem_id,name,title,email,phone,mem_num,card_no,mem_type,mem_tier,status,activate_code,id,ENROLL_DATE,CARD_LEVEL,IDENTITY_TYPE,IDENTITY_NO,MC_MEMBER_CODE) values('1-RBN9C','memtest','Mr.xiao','whatfuck@jinjiang.com','19002855454','1-45892992','7700015447','Individual','1','Activiate','未绑定',5000,sysdate,1,'Others','44444','221042');

insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD) values(5000,5000, '1-RBN9C', '1-45892992','19002855454','E10ADC3949BA59ABBE56E057F20F883E');
insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD) values(5001,5000, '1-RBN9C', '1-45892992','whatfuck@jinjiang.com','E10ADC3949BA59ABBE56E057F20F883E');
insert into MEMBER_VERIFY(ID,MEM_INFO_ID,MEM_ID,MEM_NUM,MEN_NAME,PASSWORD) values(5002,5000, '1-RBN9C', '1-45892992','7700015447','E10ADC3949BA59ABBE56E057F20F883E');