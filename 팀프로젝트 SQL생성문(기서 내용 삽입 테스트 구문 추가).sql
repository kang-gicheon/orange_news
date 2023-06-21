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

    -- 추가 및 변경 속성들 230619(강철구)
    renum int,
    parentNum int,
    wdate varchar2(25),     -- 작성 날짜
    -- --
    rcomment varchar2(100), -- 댓글
    good int DEFAULT 0,   -- 반응(좋아요)
    bad int default 0,     -- 반응(싫어요)
    
    ID VARCHAR2(50) REFERENCES MEMBER(ID),
    articlenum int REFERENCES ARTICLE(articlenum)
);

CREATE SEQUENCE reply_seq
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 9999
       NOCYCLE
       NOCACHE
       NOORDER;

CREATE TABLE REACT (
    r_type varchar(50),   -- 반응 종류
    rcount int,  -- 반응 갯수
    
    articlenum int REFERENCES ARTICLE(articlenum) 
);


-- 임시 데이터 삽입

INSERT INTO MEMBER (id, pwd, name, rep, pnum, email) 
values ('testID', 'testpwd', 'testname', 0, '01012345678', 'test01@address.com');

INSERT INTO MEMBER (id, pwd, name, rep, pnum, email) 
values ('testID2', 'testpwd2', 'testname2', 0, '01059859845', 'test02@address.com');

INSERT INTO MEMBER (id, pwd, name, rep, pnum, email) 
values ('reporter1', 'repopwd', '기자1', 1, '01078784545', 'test03@address.com');

INSERT INTO ARTICLE (title, writedate, updatedate, content, articlenum, type, reccount, hotissue, img, id)
values('testTITLE2', sysdate, sysdate, 'testContent22', seq_anum.nextval, 4, 2, 0, 'sea3.png', 'reporter1');

INSERT INTO REPLY values (reply_SEQ.nextval, 0 ,  to_char(sysdate,'YYYY/MM/DD HH24:MI:SS'), '이 연예인 너무 극혐이에요', 0, 0, 'kang', 1);


INSERT INTO REACT (r_type, rcount, articlenum) values ('좋아요',0,1);

-- 임시 기사 데이터 삽입

INSERT INTO ARTICLE (title, writedate, updatedate, content, articlenum, type, reccount, hotissue, id)
values('기사1', sysdate, sysdate, '기사1내용 테스트', seq_anum.nextval, 1, 2, 0,'reporter1');

INSERT INTO ARTICLE (title, writedate, updatedate, content, articlenum, type, reccount, hotissue, id)
values('기사2', sysdate, sysdate, '기사2내용 테스트', seq_anum.nextval, 1, 2, 0,'reporter1');

INSERT INTO ARTICLE (title, writedate, updatedate, content, articlenum, type, reccount, hotissue, id)
values('기사3', sysdate, sysdate, '기사3내용 테스트', seq_anum.nextval, 1, 2, 0,'reporter1');

INSERT INTO ARTICLE (title, writedate, updatedate, content, articlenum, type, reccount, hotissue, id)
values('기사4', sysdate, sysdate, '기사4내용 테스트', seq_anum.nextval, 1, 2, 0,'reporter1');

INSERT INTO ARTICLE (title, writedate, updatedate, content, articlenum, type, reccount, hotissue, id)
values('기사5', sysdate, sysdate, '기사5내용 테스트', seq_anum.nextval, 1, 2, 0,'reporter1');

INSERT INTO ARTICLE (title, writedate, updatedate, content, articlenum, type, reccount, hotissue, id)
values('기사6', sysdate, sysdate, '기사6내용 테스트', seq_anum.nextval, 1, 2, 0,'reporter1');

INSERT INTO ARTICLE (title, writedate, updatedate, content, articlenum, type, reccount, hotissue, id)
values('기사7', sysdate, sysdate, '기사7내용 테스트', seq_anum.nextval, 1, 2, 0,'reporter1');

INSERT INTO ARTICLE (title, writedate, updatedate, content, articlenum, type, reccount, hotissue, id)
values('기사8', sysdate, sysdate, '기사8내용 테스트', seq_anum.nextval, 1, 2, 0,'reporter1');

INSERT INTO ARTICLE (title, writedate, updatedate, content, articlenum, type, reccount, hotissue, id)
values('기사9', sysdate, sysdate, '기사9내용 테스트', seq_anum.nextval, 1, 2, 0,'reporter1');

INSERT INTO ARTICLE (title, writedate, updatedate, content, articlenum, type, reccount, hotissue, id)
values('기사10', sysdate, sysdate, '기사10내용 테스트', seq_anum.nextval, 1, 2, 0,'reporter1');

INSERT INTO ARTICLE (title, writedate, updatedate, content, articlenum, type, reccount, hotissue, id)
values('기사11', sysdate, sysdate, '기사12내용 테스트', seq_anum.nextval, 1, 2, 0,'reporter1');

commit;