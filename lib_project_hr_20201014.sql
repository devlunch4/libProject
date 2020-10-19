--모든테이블삭제 여러번 실행한다.
DROP TABLE LIBADMIN;
DROP TABLE LIBUSER;
DROP TABLE libbookinfo;
DROP TABLE libhistory;
DROP TABLE libboard;
DROP TABLE libapplyboard;
--커밋
commit;

--1관리자 테이블
--관리자 추가 필수 1관리자계정명2관리자비밀번호2관리자이름2생성일
INSERT INTO LIBADMIN VALUES ('ADMIN', 'ADMIN', '관리자1', SYSDATE);
INSERT INTO LIBADMIN VALUES ('admin', 'admin', '관리자2', SYSDATE);
INSERT INTO LIBADMIN VALUES ('a', 'a', '관리자3', SYSDATE);
--관리자조회 SELECT * FROM LIBADMIN;

--2회원테이블
--회원 추가 및 관리자는 관리자 테이블에 값이 있는 것이 들어가야함. 회원번호는 10자리로
--1회원번호2회원이름3생년월일4주소5전화번호6생성일7관리자계정
INSERT INTO LIBUSER VALUES('1234567891', '회원1', TO_DATE('19900101', 'YYYYMMDD'), '서울', '01012345678', SYSDATE, 'ADMIN');
INSERT INTO LIBUSER VALUES('1234567892', '회원2', TO_DATE('19900202', 'YYYYMMDD'), '하와이', '01012345678', SYSDATE, 'ADMIN');
INSERT INTO LIBUSER VALUES('1234567893', '회원3', TO_DATE('19900303', 'YYYYMMDD'), '괌', '01012345678', SYSDATE, 'ADMIN');
--회원 조회 SELECT * FROM LIBUSER;

--3도서정보
--도서정보추가 isbn 10자리로
--1-isbn번호2제목3저자4출판사5출간일6관리자계정7대여여부(초기값0)8등록한관리자계정
INSERT INTO libbookinfo VALUES(1111112345,'제목1','저자1','출판사1',SYSDATE,'ADMIN',0,TO_DATE('20201010', 'YYYYMMDD'));
INSERT INTO libbookinfo VALUES(2222212345,'제목2','저자2','출판사2',SYSDATE,'ADMIN',0,TO_DATE('20201010', 'YYYYMMDD')); 
INSERT INTO libbookinfo VALUES(3333312345,'제목3','저자3','출판사3',SYSDATE,'ADMIN',1,TO_DATE('20201010', 'YYYYMMDD')); --대여중
INSERT INTO libbookinfo VALUES(4444412345,'제목4','저자4','출판사4',SYSDATE,'ADMIN',1,TO_DATE('20201010', 'YYYYMMDD')); --대여중
--도서정보 조회 SELECT * FROM libbookinfo;
commit;
--4대여내역테이블
--내역번호2회원번호3isbn번호4대여일5반납일6반납예상일7연장가능유무(초기값0)/0일경우 연장가능
--대여내역추가
INSERT INTO libhistory VALUES(1,'1234567891',3333312345, TO_DATE('20201011', 'YYYYMMDD'), null,TO_DATE('20201011', 'YYYYMMDD') + 10, 0);
INSERT INTO libhistory VALUES(2,'1234567891',4444412345, TO_DATE('20201011', 'YYYYMMDD'), null,TO_DATE('20201011', 'YYYYMMDD') + 10, 0);
--대여 내역 조회 SELECT * FROM libhistory;

--5공지게시판테이블
--필수공지 3개이상 존재해야함
INSERT INTO libboard VALUES(1,'공지제목1','공지생성', 'ADMIN', TO_DATE('20200101', 'YYYYMMDD'),'ADMIN');
INSERT INTO libboard VALUES(2,'공지제목2','공지테스트', 'ADMIN', TO_DATE('20200101', 'YYYYMMDD'),'ADMIN');
INSERT INTO libboard VALUES(3,'공지제목3','공지테스트2', 'ADMIN', TO_DATE('20200101', 'YYYYMMDD'),'ADMIN');
--공지 조회 SELECT * FROM libboard;


--6도서신청게시판테이블
INSERT INTO libapplyboard VALUES(1,'도서신청글1','1234567891',TO_DATE('20200101', 'YYYYMMDD'), 0,0);
INSERT INTO libapplyboard VALUES(2,'도서신청글2','1234567891',TO_DATE('20200101', 'YYYYMMDD'), 0,0);
INSERT INTO libapplyboard VALUES(3,'도서신청글3','1234567892',TO_DATE('20200101', 'YYYYMMDD'), 0,0);
--도서신청게시판 조회 SELECT * FROM libapplyboard;
commit;
-- 기본세팅끝

--아래에 연습걸기

SELECT * FROM libhistory
