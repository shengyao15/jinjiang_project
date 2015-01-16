DELETE FROM MEMBER_MEM_INFO I WHERE I.ID IN (1991001,1991002) ;

insert into MEMBER_MEM_INFO(mem_id,name,title,email,phone,mem_num,card_no,mem_type,mem_tier,status,activate_code,id,ENROLL_DATE,CARD_LEVEL,IDENTITY_TYPE,IDENTITY_NO,MC_MEMBER_CODE) 
values('1-XZ2-001','jack_test_0001','Mr.','jack001@1.1','19112855454','1-18120148','1157866002','Individual','1','Active','未绑定',1991001,sysdate,1,'ID','jack.shi','9');

insert into MEMBER_MEM_INFO(mem_id,name,title,email,phone,mem_num,card_no,mem_type,mem_tier,status,activate_code,id,ENROLL_DATE,CARD_LEVEL,IDENTITY_TYPE,IDENTITY_NO,MC_MEMBER_CODE) 
values('1-XZ2-002','jack_test_0002','Mr.','jack002@1.1','19222855454','1-18120248','2257866002','Individual','1','Off','未绑定',1991002,sysdate,1,'ID','jack.shi1212','9');
