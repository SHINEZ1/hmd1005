
drop table my_comment;
drop sequence my_comment_number_seq;

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
