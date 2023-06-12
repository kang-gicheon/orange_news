CREATE table member (

    id varchar2(50) PRIMARY KEY,    -- 아이디
    pwd varchar2(50),   -- 비밀번호
    name varchar2(50),  -- 이름
    rep int default 0,  -- 0이 일반 계정
    pnum varchar2(50),-- 휴대폰 번호
    address varchar2(50) -- 주소 
);

drop table article;

CREATE table article (

    title varchar2(50), --제목
    writedate date, -- 작성날짜
    updatedate date,    -- 수정날짜
    content varchar2(200),  -- 내용
    articlenum int,     -- 기사 번호
    type int,       -- 기사 종류
    reccount int,   -- 추천 수
    hotissue int DEFAULT 0,     -- 특종 여부
    img blob    -- 이미지
   
);

drop table reply;

create table reply (

    wdate date,     -- 작성 날짜
    rcomment varchar2(100), -- 댓글
    good int,   -- 반응(좋아요)
    bad int     -- 반응(싫어요)

);

create table react (

    type varchar(20),   -- 반응 종류
    rcount int  -- 반응 갯수

);
