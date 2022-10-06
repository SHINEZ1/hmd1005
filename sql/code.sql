-------
--코드
-------
drop table code;

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
