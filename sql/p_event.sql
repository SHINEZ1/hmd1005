--공연전시 공공데이터
drop table p_event;
drop sequence p_event_post_id_seq;

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

select t1.*
  from (select row_number() over (order by event_id desc) no, prfnm
          from p_event) t1
where t1.no between 3 and 8;
select count(*) from p_event;