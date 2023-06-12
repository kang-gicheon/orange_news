CREATE table member (

    id varchar2(50) PRIMARY KEY,    -- ���̵�
    pwd varchar2(50),   -- ��й�ȣ
    name varchar2(50),  -- �̸�
    rep int default 0,  -- 0�� �Ϲ� ����
    pnum varchar2(50),-- �޴��� ��ȣ
    address varchar2(50) -- �ּ� 
);

drop table article;

CREATE table article (

    title varchar2(50), --����
    writedate date, -- �ۼ���¥
    updatedate date,    -- ������¥
    content varchar2(200),  -- ����
    articlenum int,     -- ��� ��ȣ
    type int,       -- ��� ����
    reccount int,   -- ��õ ��
    hotissue int DEFAULT 0,     -- Ư�� ����
    img blob    -- �̹���
   
);

drop table reply;

create table reply (

    wdate date,     -- �ۼ� ��¥
    rcomment varchar2(100), -- ���
    good int,   -- ����(���ƿ�)
    bad int     -- ����(�Ⱦ��)

);

create table react (

    type varchar(20),   -- ���� ����
    rcount int  -- ���� ����

);
