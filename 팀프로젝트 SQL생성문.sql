DROP TABLE MEMBER;
DROP TABLE ARTICLE;
DROP TABLE REPLY;
DROP TABLE REACT;

DROP SEQUENCE SEQ_ANUM; 

--���⼭���� ����--

CREATE SEQUENCE SEQ_ANUM NOCACHE; -- ��� ��ȣ �ο� ������ 

CREATE TABLE member ( --ȸ�� ���� ���̺� 
    id varchar2(50),    -- ���̵�
    pwd varchar2(50),   -- ��й�ȣ
    name varchar2(50),  -- �̸�
    rep int default 0,  -- 0�� �Ϲ� ����
    pnum varchar2(50),-- �޴��� ��ȣ
    address varchar2(50), -- �ּ� 
    
    PRIMARY KEY (id)
);

CREATE TABLE ARTICLE ( --��� ���� ���̺� 

    title varchar2(50), --����
    writedate date, -- �ۼ���¥
    updatedate date,    -- ������¥
    content varchar2(200),  -- ����
    articlenum int,     -- ��� ��ȣ
    type int,       -- ��� ����
    reccount int,   -- ��õ ��
    hotissue int DEFAULT 0,     -- Ư�� ����
    img blob,    -- �̹���
    
    ID VARCHAR2(50) REFERENCES MEMBER(ID), -- �ۼ��� 
    
    PRIMARY KEY (articlenum)--��� ��ȣ�� PK��
);

CREATE TABLE REPLY (

    wdate date,     -- �ۼ� ��¥
    rcomment varchar2(100), -- ���
    good int,   -- ����(���ƿ�)
    bad int,     -- ����(�Ⱦ��)
    
    ID VARCHAR2(50) REFERENCES MEMBER(ID),
    articlenum int REFERENCES ARTICLE(articlenum)
);

CREATE TABLE REACT (
    r_type varchar(20),   -- ���� ����
    rcount int,  -- ���� ����
    
    articlenum int REFERENCES ARTICLE(articlenum) 
);