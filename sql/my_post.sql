
drop table my_post;
drop sequence my_post_number_seq;

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
