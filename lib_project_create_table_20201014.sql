/* 관리자 */
CREATE TABLE LIBADMIN (
	adminid VARCHAR2(100) NOT NULL, /* 관리자계정 */
	adminpw VARCHAR2(100) NOT NULL, /* 관리자비밀번호 */
	adminnm VARCHAR2(100), /* 관리자이름 */
	adddate DATE /* 생성일 */
);

COMMENT ON TABLE LIBADMIN IS '관리자';

COMMENT ON COLUMN LIBADMIN.adminid IS '관리자계정';

COMMENT ON COLUMN LIBADMIN.adminpw IS '관리자비밀번호';

COMMENT ON COLUMN LIBADMIN.adminnm IS '관리자이름';

COMMENT ON COLUMN LIBADMIN.adddate IS '생성일';

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

/* 회원 */
CREATE TABLE LIBUSER (
	userno VARCHAR2(100) NOT NULL, /* 회원번호 */
	uname VARCHAR2(100) NOT NULL, /* 회원이름 */
	ubirth DATE NOT NULL, /* 생년월일 */
	uaddress VARCHAR2(255), /* 주소 */
	uphone VARCHAR2(30), /* 전화번호 */
	uadddate DATE, /* 생성일 */
	adminid VARCHAR2(100) /* 관리자계정 */
);

COMMENT ON TABLE LIBUSER IS '회원';

COMMENT ON COLUMN LIBUSER.userno IS '회원번호';

COMMENT ON COLUMN LIBUSER.uname IS '회원이름';

COMMENT ON COLUMN LIBUSER.ubirth IS '생년월일';

COMMENT ON COLUMN LIBUSER.uaddress IS '주소';

COMMENT ON COLUMN LIBUSER.uphone IS '전화번호';

COMMENT ON COLUMN LIBUSER.uadddate IS '생성일';

COMMENT ON COLUMN LIBUSER.adminid IS '관리자계정';

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

/* 책정보 */
CREATE TABLE LIBBOOKINFO (
	bookno NUMBER(13) NOT NULL, /* ISBN번호 */
	title VARCHAR2(255), /* 제목 */
	author VARCHAR2(100), /* 저자 */
	publisher VARCHAR2(100), /* 출판사 */
	pdate DATE, /* 출간일 */
	adminid VARCHAR2(100), /* 관리자계정 */
	rentyesno NUMBER(1) NOT NULL /* 대여여부 */
);

COMMENT ON TABLE LIBBOOKINFO IS '책정보';

COMMENT ON COLUMN LIBBOOKINFO.bookno IS 'ISBN번호';

COMMENT ON COLUMN LIBBOOKINFO.title IS '제목';

COMMENT ON COLUMN LIBBOOKINFO.author IS '저자';

COMMENT ON COLUMN LIBBOOKINFO.publisher IS '출판사';

COMMENT ON COLUMN LIBBOOKINFO.pdate IS '출간일';

COMMENT ON COLUMN LIBBOOKINFO.adminid IS '관리자계정';

COMMENT ON COLUMN LIBBOOKINFO.rentyesno IS '대여여부';

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

/* 게시판 */
CREATE TABLE LIBBOARD (
	boardno NUMBER NOT NULL, /* 공지번호 */
	btitle VARCHAR2(255) NOT NULL, /* 공지제목 */
	bcontent VARCHAR2(255), /* 공지내용 */
	bwriter VARCHAR2(100) NOT NULL, /* 작성자 */
	bdate DATE, /* 작성일 */
	adminid VARCHAR2(100) NOT NULL /* 관리자계정 */
);

COMMENT ON TABLE LIBBOARD IS '게시판';

COMMENT ON COLUMN LIBBOARD.boardno IS '공지번호';

COMMENT ON COLUMN LIBBOARD.btitle IS '공지제목';

COMMENT ON COLUMN LIBBOARD.bcontent IS '공지내용';

COMMENT ON COLUMN LIBBOARD.bwriter IS '작성자';

COMMENT ON COLUMN LIBBOARD.bdate IS '작성일';

COMMENT ON COLUMN LIBBOARD.adminid IS '관리자계정';

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

/* 도서신청게시판 */
CREATE TABLE LIBAPPLYBOARD (
	findno NUMBER NOT NULL, /* 신청번호 */
	findcontext VARCHAR2(255), /* 신청도서정보 */
	userno VARCHAR2(100) NOT NULL, /* 회원번호 */
	finddate DATE, /* 신청일 */
	recomm NUMBER, /* 추천 */
	arrange NUMBER(1) /* 입고완료 */
);

COMMENT ON TABLE LIBAPPLYBOARD IS '도서신청게시판';

COMMENT ON COLUMN LIBAPPLYBOARD.findno IS '신청번호';

COMMENT ON COLUMN LIBAPPLYBOARD.findcontext IS '신청도서정보';

COMMENT ON COLUMN LIBAPPLYBOARD.userno IS '회원번호';

COMMENT ON COLUMN LIBAPPLYBOARD.finddate IS '신청일';

COMMENT ON COLUMN LIBAPPLYBOARD.recomm IS '추천';

COMMENT ON COLUMN LIBAPPLYBOARD.arrange IS '입고완료';

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

/* 대여내역 */
CREATE TABLE LIBHISTORY (
	historyno NUMBER NOT NULL, /* 내역번호 */
	userno VARCHAR2(100) NOT NULL, /* 회원번호 */
	bookno NUMBER(13), /* ISBN번호 */
	rentdate DATE, /* 대여일 */
	returndate DATE, /* 반납일 */
	expectdate DATE, /* 반납예상일 */
	extencan NUMBER(1) /* 연장가능유무 */
);

COMMENT ON TABLE LIBHISTORY IS '대여내역';

COMMENT ON COLUMN LIBHISTORY.historyno IS '내역번호';

COMMENT ON COLUMN LIBHISTORY.userno IS '회원번호';

COMMENT ON COLUMN LIBHISTORY.bookno IS 'ISBN번호';

COMMENT ON COLUMN LIBHISTORY.rentdate IS '대여일';

COMMENT ON COLUMN LIBHISTORY.returndate IS '반납일';

COMMENT ON COLUMN LIBHISTORY.expectdate IS '반납예상일';

COMMENT ON COLUMN LIBHISTORY.extencan IS '연장가능유무';

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