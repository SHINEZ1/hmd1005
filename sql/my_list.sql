
drop table my_list;
drop sequence my_list_number_seq;

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
