/* ������ */
CREATE TABLE LIBADMIN (
	adminid VARCHAR2(100) NOT NULL, /* �����ڰ��� */
	adminpw VARCHAR2(100) NOT NULL, /* �����ں�й�ȣ */
	adminnm VARCHAR2(100), /* �������̸� */
	adddate DATE /* ������ */
);

COMMENT ON TABLE LIBADMIN IS '������';

COMMENT ON COLUMN LIBADMIN.adminid IS '�����ڰ���';

COMMENT ON COLUMN LIBADMIN.adminpw IS '�����ں�й�ȣ';

COMMENT ON COLUMN LIBADMIN.adminnm IS '�������̸�';

COMMENT ON COLUMN LIBADMIN.adddate IS '������';

CREATE UNIQUE INDEX PK_LIBADMIN
	ON LIBADMIN (
		adminid ASC
	);

ALTER TABLE LIBADMIN
	ADD
		CONSTRAINT PK_LIBADMIN
		PRIMARY KEY (
			adminid
		);

/* ȸ�� */
CREATE TABLE LIBUSER (
	userno VARCHAR2(100) NOT NULL, /* ȸ����ȣ */
	uname VARCHAR2(100) NOT NULL, /* ȸ���̸� */
	ubirth DATE, /* ������� */
	uaddress VARCHAR2(255), /* �ּ� */
	uphone NUMBER, /* ��ȭ��ȣ */
	uadddate DATE, /* ������ */
	adminid VARCHAR2(100) /* �����ڰ��� */
);

COMMENT ON TABLE LIBUSER IS 'ȸ��';

COMMENT ON COLUMN LIBUSER.userno IS 'ȸ����ȣ';

COMMENT ON COLUMN LIBUSER.uname IS 'ȸ���̸�';

COMMENT ON COLUMN LIBUSER.ubirth IS '�������';

COMMENT ON COLUMN LIBUSER.uaddress IS '�ּ�';

COMMENT ON COLUMN LIBUSER.uphone IS '��ȭ��ȣ';

COMMENT ON COLUMN LIBUSER.uadddate IS '������';

COMMENT ON COLUMN LIBUSER.adminid IS '�����ڰ���';

CREATE UNIQUE INDEX PK_LIBUSER
	ON LIBUSER (
		userno ASC
	);

ALTER TABLE LIBUSER
	ADD
		CONSTRAINT PK_LIBUSER
		PRIMARY KEY (
			userno
		);

/* å���� */
CREATE TABLE LIBBOOKINFO (
	bookno NUMBER(13) NOT NULL, /* ISBN��ȣ */
	title VARCHAR2(255), /* ���� */
	author VARCHAR2(100), /* ���� */
	publisher VARCHAR2(100), /* ���ǻ� */
	pdate DATE, /* �Ⱓ�� */
	adminid VARCHAR2(100), /* �����ڰ��� */
	rentyesno NUMBER(1) /* �뿩���� */
);

COMMENT ON TABLE LIBBOOKINFO IS 'å����';

COMMENT ON COLUMN LIBBOOKINFO.bookno IS 'ISBN��ȣ';

COMMENT ON COLUMN LIBBOOKINFO.title IS '����';

COMMENT ON COLUMN LIBBOOKINFO.author IS '����';

COMMENT ON COLUMN LIBBOOKINFO.publisher IS '���ǻ�';

COMMENT ON COLUMN LIBBOOKINFO.pdate IS '�Ⱓ��';

COMMENT ON COLUMN LIBBOOKINFO.adminid IS '�����ڰ���';

COMMENT ON COLUMN LIBBOOKINFO.rentyesno IS '�뿩����';

CREATE UNIQUE INDEX PK_LIBBOOKINFO
	ON LIBBOOKINFO (
		bookno ASC
	);

ALTER TABLE LIBBOOKINFO
	ADD
		CONSTRAINT PK_LIBBOOKINFO
		PRIMARY KEY (
			bookno
		);

/* �Խ��� */
CREATE TABLE LIBBOARD (
	boardno NUMBER NOT NULL, /* ������ȣ */
	btitle VARCHAR2(255) NOT NULL, /* �������� */
	bcontent VARCHAR2(255), /* �������� */
	boardwirter VARCHAR2(100), /* �ۼ��� */
	boarddate DATE, /* �ۼ��� */
	adminid VARCHAR2(100) /* �����ڰ��� */
);

COMMENT ON TABLE LIBBOARD IS '�Խ���';

COMMENT ON COLUMN LIBBOARD.boardno IS '������ȣ';

COMMENT ON COLUMN LIBBOARD.btitle IS '��������';

COMMENT ON COLUMN LIBBOARD.bcontent IS '��������';

COMMENT ON COLUMN LIBBOARD.boardwirter IS '�ۼ���';

COMMENT ON COLUMN LIBBOARD.boarddate IS '�ۼ���';

COMMENT ON COLUMN LIBBOARD.adminid IS '�����ڰ���';

CREATE UNIQUE INDEX PK_LIBBOARD
	ON LIBBOARD (
		boardno ASC
	);

ALTER TABLE LIBBOARD
	ADD
		CONSTRAINT PK_LIBBOARD
		PRIMARY KEY (
			boardno
		);

/* ������û�Խ��� */
CREATE TABLE LIBAPPLYBOARD (
	findno NUMBER NOT NULL, /* ��û��ȣ */
	findcontext VARCHAR2(255), /* ��û�������� */
	userno VARCHAR2(100), /* ȸ����ȣ */
	finddate DATE, /* ��û�� */
	recomm NUMBER, /* ��õ */
	arrange NUMBER(1) /* �԰��Ϸ� */
);

COMMENT ON TABLE LIBAPPLYBOARD IS '������û�Խ���';

COMMENT ON COLUMN LIBAPPLYBOARD.findno IS '��û��ȣ';

COMMENT ON COLUMN LIBAPPLYBOARD.findcontext IS '��û��������';

COMMENT ON COLUMN LIBAPPLYBOARD.userno IS 'ȸ����ȣ';

COMMENT ON COLUMN LIBAPPLYBOARD.finddate IS '��û��';

COMMENT ON COLUMN LIBAPPLYBOARD.recomm IS '��õ';

COMMENT ON COLUMN LIBAPPLYBOARD.arrange IS '�԰��Ϸ�';

CREATE UNIQUE INDEX PK_LIBAPPLYBOARD
	ON LIBAPPLYBOARD (
		findno ASC
	);

ALTER TABLE LIBAPPLYBOARD
	ADD
		CONSTRAINT PK_LIBAPPLYBOARD
		PRIMARY KEY (
			findno
		);

/* �뿩���� */
CREATE TABLE LIBHISTORY (
	historyno NUMBER NOT NULL, /* ������ȣ */
	userno VARCHAR2(100), /* ȸ����ȣ */
	bookno NUMBER(13), /* ISBN��ȣ */
	rentdate DATE, /* �뿩�� */
	returndate DATE, /* �ݳ��� */
	expectdate DATE, /* �ݳ������� */
	extencan NUMBER(1) /* ���尡������ */
);

COMMENT ON TABLE LIBHISTORY IS '�뿩����';

COMMENT ON COLUMN LIBHISTORY.historyno IS '������ȣ';

COMMENT ON COLUMN LIBHISTORY.userno IS 'ȸ����ȣ';

COMMENT ON COLUMN LIBHISTORY.bookno IS 'ISBN��ȣ';

COMMENT ON COLUMN LIBHISTORY.rentdate IS '�뿩��';

COMMENT ON COLUMN LIBHISTORY.returndate IS '�ݳ���';

COMMENT ON COLUMN LIBHISTORY.expectdate IS '�ݳ�������';

COMMENT ON COLUMN LIBHISTORY.extencan IS '���尡������';

CREATE UNIQUE INDEX PK_LIBHISTORY
	ON LIBHISTORY (
		historyno ASC
	);

ALTER TABLE LIBHISTORY
	ADD
		CONSTRAINT PK_LIBHISTORY
		PRIMARY KEY (
			historyno
		);

ALTER TABLE LIBUSER
	ADD
		CONSTRAINT FK_LIBADMIN_TO_LIBUSER
		FOREIGN KEY (
			adminid
		)
		REFERENCES LIBADMIN (
			adminid
		);

ALTER TABLE LIBBOOKINFO
	ADD
		CONSTRAINT FK_LIBADMIN_TO_LIBBOOKINFO
		FOREIGN KEY (
			adminid
		)
		REFERENCES LIBADMIN (
			adminid
		);

ALTER TABLE LIBBOARD
	ADD
		CONSTRAINT FK_LIBADMIN_TO_LIBBOARD
		FOREIGN KEY (
			adminid
		)
		REFERENCES LIBADMIN (
			adminid
		);

ALTER TABLE LIBAPPLYBOARD
	ADD
		CONSTRAINT FK_LIBUSER_TO_LIBAPPLYBOARD
		FOREIGN KEY (
			userno
		)
		REFERENCES LIBUSER (
			userno
		);

ALTER TABLE LIBHISTORY
	ADD
		CONSTRAINT FK_LIBUSER_TO_LIBHISTORY
		FOREIGN KEY (
			userno
		)
		REFERENCES LIBUSER (
			userno
		);

ALTER TABLE LIBHISTORY
	ADD
		CONSTRAINT FK_LIBBOOKINFO_TO_LIBHISTORY
		FOREIGN KEY (
			bookno
		)
		REFERENCES LIBBOOKINFO (
			bookno
		);