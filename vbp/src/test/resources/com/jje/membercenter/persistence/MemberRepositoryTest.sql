delete from MEMBER_MEM_INFO where id = 47457001;
delete from MEMBER_MEM_INFO where id = 47457002;

insert into MEMBER_MEM_INFO(id,mem_id,name, title,email, phone, mem_num,card_no,mem_type,mem_tier,status,activate_code,ENROLL_DATE,CARD_LEVEL,IDENTITY_TYPE,IDENTITY_NO,MC_MEMBER_CODE) 
values(47457001,'1-6D8T4U','81192','Mr.', '1873894944@qq.com', '13795398694', '1-385043070','7700107352','Individual','1','Active','Not Activiate','2014-2-20',1,'Work','654649461','3231663100');

insert into MEMBER_MEM_INFO(id,mem_id,name, title,email, phone, mem_num,card_no,mem_type,mem_tier,status,activate_code,ENROLL_DATE,CARD_LEVEL,IDENTITY_TYPE,IDENTITY_NO,MC_MEMBER_CODE) 
values(47457002,'1-6D8T4A','81193','Mr.', '1873894941@qq.com', '13795398695', '1-385043071','7700107352','Individual','1','Active','Not Activiate','2014-2-20',1,'Work','654649461','3231663103');
