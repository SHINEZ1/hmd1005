-------
--코드
-------

create table code(
    code_id     varchar2(11),       --코드
    decode      varchar2(30),       --코드명
    discript    clob,               --코드설명
    pcode_id    varchar2(11),       --상위코드
    useyn       char(1) default 'Y',            --사용여부 (사용:'Y',미사용:'N')
    cdate       timestamp default systimestamp,         --생성일시
    udate       timestamp default systimestamp          --수정일시
);
--기본키
alter table code add Constraint code_code_id_pk primary key (code_id);

--외래키
--alter table code drop constraint post_pcode_id_fk;
-- alter table code add constraint post_pcode_id_fk foreign key(pcode_id) references code(code_id);

--제약조건
alter table code modify decode constraint code_decode_nn not null;
alter table code modify useyn constraint code_useyn_nn not null;
alter table code add constraint code_useyn_ck check(useyn in ('Y','N'));

--샘플데이터 of code
insert into code (code_id,decode,pcode_id,useyn) values ('B01','게시판',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0101','공연전시','B01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0102','홍보','B01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0103','후기','B01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0104','문화지도','B01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M01','회원구분',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M0101','일반','M01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M0102','홍보','M01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M01A1','관리자1','M01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M01A2','관리자2','M01','Y');

-------------------------------------------------------------------------------------------

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


-------------------------------------------------------------------------------------------
-------
--게시판
-------

--시퀀스
create sequence post_post_id_seq;

create table post(
    post_id      number(10),         --게시글 번호
    pcategory   varchar2(11),       --분류카테고리
    title       varchar2(150),      --제목
    email       varchar2(50),       --email
    nickname    varchar2(30),       --별칭
    hit         number(5) default 0,          --조회수
    pcontent    clob,               --본문
    ppost_id     number(10),         --부모 게시글번호
    pgroup      number(10),         --답글그룹
    step        number(3) default 0,          --답글단계
    pindent     number(3) default 0,          --답글들여쓰기
    status      char(1),               --답글상태  (삭제: 'D', 임시저장: 'I')
    cdate       timestamp default systimestamp,         --생성일시
    udate       timestamp default systimestamp          --수정일시
);


--기본키
alter table post add Constraint post_post_id_pk primary key (post_id);

--외래키
--alter table post drop constraint post_bcategory_fk;
alter table post add constraint post_pcategory_fk foreign key(pcategory) references code(code_id);
-- alter table post add constraint post_ppost_id_fk foreign key(ppost_id) references post(post_id);
alter table post add constraint post_email_fk foreign key(email) references member(email);
--alter table post drop constraint post_email_fk;
--제약조건
alter table post modify pcategory constraint post_pcategory_nn not null;
alter table post modify title constraint post_title_nn not null;
alter table post modify email constraint post_email_nn not null;
alter table post modify nickname constraint post_nickname_nn not null;
alter table post modify pcontent constraint post_pcontent_nn not null;


insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기1','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기2','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기3','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기4','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기5','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기6','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기7','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기8','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기9','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기10','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기11','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기12','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기13','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기14','test1@kh.com','글쓴이1',3,'잼씀');


-------------------------------------------------------------------------------------------

create sequence my_comment_number_seq;

create table my_comment (
  cmt_number number(10),    -- 댓글번호 [기본키]
  cmt_user_id number(8),    -- 작성자 ID [외래키 memeber's id]
  board_type varchar2(20),  -- 게시판 종류
  cmt_index varchar2(100),  -- 댓글 내용
  cmt_date DATE,            -- 작성된 날짜
  cmt_url varchar2(100)     -- 댓글 작성된 글 주소
);

alter table my_comment add constraint my_comment_number_pk primary key(cmt_number);

alter table my_comment add constraint my_comment_user_id_fk foreign key(cmt_user_id) references member(member_id);

alter table my_comment modify board_type constraint my_comment_board_type_nn not null;
alter table my_comment modify cmt_index constraint my_comment_cmt_index_nn not null;
alter table my_comment modify cmt_date constraint my_comment_cmt_date_nn not null;
alter table my_comment modify cmt_url constraint my_comment_cmt_url_nn not null;


-------------------------------------------------------------------------------------------

create sequence my_list_number_seq;

create table my_list (
  list_number number(10),   -- 좋아오 누른 글 번호 [기본키]
  list_user_id number(8),   -- 좋아요 누른 유저 ID [외래키 member's ID]
  thumbnail varchar2(100),  -- 썸네일
  title varchar2(50),       -- 글 제목
  start_date DATE,          -- 시작일
  end_date DATE,            -- 종료일
  genre varchar2(10),       -- 장르
  is_like number(1),        -- 좋아요 유무 (1or0 true false)
  list_url varchar2(100)    -- 글 주소
);

alter table my_list add constraint my_list_number_pk primary key(list_number);

alter table my_list add constraint my_list_user_id_fk foreign key(list_user_id) references member(member_id);

alter table my_list modify title constraint my_list_title_nn not null;
alter table my_list modify start_date constraint my_list_start_date_nn not null;
alter table my_list modify end_date constraint my_list_end_date_nn not null;
alter table my_list modify genre constraint my_list_genre_nn not null;
alter table my_list modify list_url constraint my_list_url_nn not null;



-------------------------------------------------------------------------------------------

create sequence my_post_number_seq;

create table my_post (
  post_number number(10),   -- 글번호 [기본키]
  post_user_id number(8),   -- 작성자 ID [외래키 memeber's ID]
  board_type varchar2(20),  -- 게시판 종류
  title varchar2(100),      -- 글 제목
  post_date DATE,           -- 작성된 날짜
  post_url varchar2(100)    -- 작성글 주소
);

alter table my_post add constraint my_post_number_pk primary key(post_number);

alter table my_post add constraint my_post_user_id_fk foreign key(post_user_id) references member(member_id);

alter table my_post modify board_type constraint my_post_board_type_nn not null;
alter table my_post modify title constraint my_post_title_nn not null;
alter table my_post modify post_date constraint my_post_date_nn not null;
alter table my_post modify post_url constraint my_post_url_nn not null;

alter table my_post add constraint my_post_url_unique unique (post_url);



-------------------------------------------------------------------------------------------

create table p_facility(
    mt10id	    VARCHAR2(10),   --	pk, fk, 공연시설ID
    fcltynm	    VARCHAR2(100),  --	fk, 공연시설명
    mt13cnt	    VARCHAR2(5),    --	공연장 수
    fcltychartr	VARCHAR2(30),   --	시설특성
    seatscale	VARCHAR2(10),   --	5	객석 수
    telno	    VARCHAR2(15),   --	전화번호
    relateurl	VARCHAR2(100),  --	홈페이지
    adres	    VARCHAR2(120),  --	주소
    opende	    VARCHAR2(6),    --	개관연도
    la	        VARCHAR2(20),   --	위도
    lo	        VARCHAR2(25)    --	경도

);
alter table p_facility add constraint p_facility_mt10id_pk primary key(mt10id);


-------------------------------------------------------------------------------------------

create sequence p_event_post_id_seq;

create table p_event(
    event_id        NUMBER(10),     --  내부관리용 ID
    mt20id	        VARCHAR2(10),   --  pk, not null	12	공연ID +
    prfnm	        VARCHAR2(100),  --	공연명 +
    prfpdfrom	    VARCHAR2(10),   --	공연시작일 +
    prfpdto	        VARCHAR2(10),   --	공연종료일 +
    fcltynm	        VARCHAR2(100),  --	공연시설명(공연장명) +
    prfcast	        VARCHAR2(100),  --	공연출연진 +
    prfcrew	        VARCHAR2(30),   --	공연제작진 +
    prfruntime	    VARCHAR2(20),   --	공연 런타임 +
    prfage	        VARCHAR2(20),   --	공연 관람 연령 +
    entrpsnm	    VARCHAR2(50),	--  제작사 +
    pcseguidance	VARCHAR2(80),	--  티켓가격 +
    poster	        VARCHAR2(100),  --	포스터이미지경로
    sty	            CLOB,		    --  줄거리
    genrenm	        VARCHAR2(10),   --	공연 장르명 +
    prfstate	    VARCHAR2(20),   --	공연상태 +
    openrun	        VARCHAR2(1),    --	오픈런
    styurl1	        VARCHAR2(100),  --	소개이미지1
    styurl2	        VARCHAR2(100),  --	소개이미지2
    styurl3	        VARCHAR2(100),  --	소개이미지3
    styurl4	        VARCHAR2(100),  --	소개이미지4
    mt10id	        VARCHAR2(10),   --	공연시설ID
    dtguidance	    VARCHAR2(100)   --	공연시간 +
);
alter table p_event add constraint p_event_mt20id_pk primary key(mt20id);
alter table p_event add constraint p_event_mt10id_fk foreign key(mt10id) references p_facility(mt10id);

-------------------------------------------------------------------------------------------
---------
--댓글
---------

create sequence reply_reply_seq;

create table reply(
  reply_id        number(10),
  p_post_id       number(10),
  bcategory       varchar2(11),
  email           varchar2(50),
  nickname        varchar2(30),
  rcontent        varchar2(100),
  cdate           timestamp default systimestamp,
  udate           timestamp default systimestamp
); 

--기본키
alter table reply add constraint reply_reply_id_pk primary key(reply_id);

--외래키
alter table reply add constraint reply_p_post_id_fk
    foreign key(p_post_id) references post(post_id);
alter table reply add constraint reply_email_fk
    foreign key(email) references member(email);
--제약조건



-------------------------------------------------------------------------------------------

--시퀀스
create sequence uploadfile_uploadfile_id_seq;
---------
--첨부파일
---------
create table uploadfile(
    uploadfile_id   number(10),      --파일아이디
    code            varchar2(11),    --분류코드
    rid             varchar2(10),    --참조번호(게시글번호등)
    store_filename  varchar2(100),   --서버보관파일명
    upload_filename varchar2(100),   --업로드파일명(유저가 업로드한파일명)
    fsize           varchar2(45),    --업로드파일크기(단위byte)
    ftype           varchar2(100),   --파일유형(mimetype)
    cdate           timestamp default systimestamp, --등록일시
    udate           timestamp default systimestamp  --수정일시
);
--기본키
alter table uploadfile add constraint uploadfile_uploadfile_id_pk primary key(uploadfile_id);

--외래키
alter table uploadfile add constraint uploadfile_uploadfile_id_fk
    foreign key(code) references code(code_id);

--제약조건
alter table uploadfile modify code constraint uploadfile_code_nn not null;
alter table uploadfile modify rid constraint uploadfile_rid_nn not null;
alter table uploadfile modify store_filename constraint uploadfile_store_filename_nn not null;
alter table uploadfile modify upload_filename constraint uploadfile_upload_filename_nn not null;
alter table uploadfile modify fsize constraint uploadfile_fsize_nn not null;
alter table uploadfile modify ftype constraint uploadfile_ftype_nn not null;


-------------------------------------------------------------------------------------------
---------
--좋아요
---------
create sequence good_good_id_seq; 

create table good(
  good_id       number(10),
  p_post_id     number(10),
  cdate           timestamp default systimestamp,
  udate           timestamp default systimestamp
);

--기본키
alter table good add constraint good_good_id_pk primary key(good_id);

--외래키
alter table good add constraint good_p_post_id_fk
    foreign key(p_post_id) references post(post_id);
    
      
------------------------------------------------------------------------------------------- 
commit;