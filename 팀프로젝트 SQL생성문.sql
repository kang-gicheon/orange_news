DROP TABLE REACT;
DROP TABLE REPLY;
DROP TABLE ARTICLE;
DROP TABLE MEMBER;


DROP SEQUENCE SEQ_ANUM; 

--여기서부터 실행--

CREATE SEQUENCE SEQ_ANUM NOCACHE; -- 기사 번호 부여 시퀀스 

CREATE TABLE member ( --회원 정보 테이블 
    id varchar2(50),    -- 아이디
    pwd varchar2(50),   -- 비밀번호
    name varchar2(50),  -- 이름
    rep int default 0,  -- 0이 일반 계정
    pnum varchar2(50),-- 휴대폰 번호
    email varchar2(50), -- 주소 
    
    PRIMARY KEY (id)
);

CREATE TABLE ARTICLE ( --기사 정보 테이블 

    title varchar2(50), --제목
    writedate date, -- 작성날짜
    updatedate date,    -- 수정날짜
    content varchar2(200),  -- 내용
    articlenum int,     -- 기사 번호
    type int,       -- 기사 종류
    reccount int,   -- 추천 수
    hotissue int DEFAULT 0,     -- 특종 여부
    img varchar2(100),    -- 이미지
    
    ID VARCHAR2(50) REFERENCES MEMBER(ID), -- 작성자 
    
    PRIMARY KEY (articlenum)--기사 번호를 PK로
);

CREATE TABLE REPLY (

    wdate date,     -- 작성 날짜
    rcomment varchar2(100), -- 댓글
    good int,   -- 반응(좋아요)
    bad int,     -- 반응(싫어요)
    
    ID VARCHAR2(50) REFERENCES MEMBER(ID),
    articlenum int REFERENCES ARTICLE(articlenum)
);

CREATE TABLE REACT (
    r_type varchar(50),   -- 반응 종류
    rcount int,  -- 반응 갯수
    
    articlenum int REFERENCES ARTICLE(articlenum) 
);


-- 임시 데이터 삽입

INSERT INTO MEMBER (id, pwd, name, rep, pnum, email) 
values ('testID', 'testpwd', 'testname', 0, '01012345678', 'test01@address');

INSERT INTO MEMBER (id, pwd, name, rep, pnum, email) 
values ('testID2', 'testpwd2', 'testname2', 0, '01059859845', 'testaddress2');

INSERT INTO MEMBER (id, pwd, name, rep, pnum, email) 
values ('reporter1', 'repopwd', '기자1', 1, '01078784545', 'testaddress3');

INSERT INTO ARTICLE (title, writedate, updatedate, content, articlenum, type, reccount, hotissue, img, id)
values('testTITLE2', sysdate, sysdate, 'testContent22', seq_anum.nextval, 4, 2, 0, 'sea3.png', 'reporter1');


INSERT INTO REACT (r_type, rcount, articlenum) values ('좋아요',0,1);
commit;