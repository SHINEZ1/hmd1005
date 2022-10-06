---------
--좋아요
---------
drop table good;
drop sequence good_good_id_seq; 

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
    
       
