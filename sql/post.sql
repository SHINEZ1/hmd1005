-------
--게시판
-------

drop table post;
drop sequence post_post_id_seq;

--시퀀스
create sequence post_post_id_seq;

create table post(
    post_id      number(10),            --게시글 번호
    pcategory   varchar2(11),           --분류카테고리
    title       varchar2(150),          --제목
    email       varchar2(50),           --email
    nickname    varchar2(30),           --별칭
    hit         number(5) default 0,    --조회수
    pcontent    clob,                   --본문
    ppost_id     number(10),            --부모 게시글번호
    pgroup      number(10),             --답글그룹
    step        number(3) default 0,    --답글단계
    pindent     number(3) default 0,    --답글들여쓰기
    status      char(1),                --답글상태  (삭제: 'D', 임시저장: 'I')
    cdate       timestamp default systimestamp,  --생성일시
    udate       timestamp default systimestamp   --수정일시
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

select * from post;