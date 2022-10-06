drop table member;
drop sequence member_member_id_seq;

create sequence member_member_id_seq;

create table member (
  member_id 	  number(8),      --기본키
  email 		  varchar(30),    --(이메일이 아이디)
  password 		  varchar2(20),   -- nn
  name 			  varchar2 (10),  -- 유니크 nn
  nickname 		  varchar2 (20),  -- 유니크 nn
  phone 		  varchar2 (12),  -- 유니크 nn
  birthday 		  date,           -- nn
  sms_service 	  number(1),
  email_service   number(1),
  gubun           varchar2(11)   default 'M0101', --회원구분 (일반,홍보,관리자..) M0102 홍보
  cdate       	  timestamp default systimestamp,
  udate       	  timestamp default systimestamp
);


alter table member add constraint member_member_id_pk primary key(member_id);

alter table member add constraint member_gubun_fk foreign key(gubun) references code(code_id);

alter table member modify email constraint member_email_nn not null;
alter table member modify nickname constraint member_nickname_nn not null;
alter table member modify password constraint member_password_nn not null;
alter table member modify name constraint member_name_nn not null;
alter table member modify phone constraint member_phone_nn not null;
alter table member modify birthday constraint member_birthday_nn not null;

alter table member add constraint member_email_unique unique (email);
alter table member add constraint member_nickname_unique unique (nickname);



desc member;

select * from member;

------------ End Create Table MEMBER Query ------------
--......별칭,폰,생일,sns svc, email svc
insert into member (member_id,email,password,name,nickname,phone,birthday,sms_service,email_service,gubun)
    values(member_member_id_seq.nextval, 'test1@kh.com', '1234','홍길동', '테스터1','01011112222','1999-01-01',1,1,'M0101');
insert into member (member_id,email,password,name,nickname,phone,birthday,sms_service,email_service,gubun)
    values(member_member_id_seq.nextval, 'test2@kh.com', '1234','홍길서', '테스터2','01011113333','2001-03-03',1,1,'M0102');
insert into member (member_id,email,password,name,nickname,phone,birthday,sms_service,email_service,gubun)
    values(member_member_id_seq.nextval, 'admin1@kh.com', '1234','홍길남','관리자1', '01022223333','2009-04-04',1,1,'M01A1');
insert into member (member_id,email,password,name,nickname,phone,birthday,sms_service,email_service,gubun)
    values(member_member_id_seq.nextval, 'admin2@kh.com', '1234','홍길북','관리자2', '01033334444','2010-05-05',1,1,'M01A2');

insert into member (member_id,email,password,name,nickname,phone,birthday,sms_service,email_service,gubun)
    values(member_member_id_seq.nextval, 'test1@test.com', '1234', '김일번','퍼스트맨','01011112222','1999-01-01',1,1,'M0101');

commit;
