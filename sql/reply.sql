---------
--댓글
---------
drop table reply;
drop sequence reply_reply_seq;

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
