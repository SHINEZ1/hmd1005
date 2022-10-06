--공연장 상세
drop table p_facility;

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

