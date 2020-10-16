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
--관리자 추가 필수
INSERT INTO LIBADMIN VALUES ('ADMIN', 'ADMIN', '관리자이름', SYSDATE);
SELECT * FROM LIBADMIN;


--2회원테이블
--회원 추가 및 관리자는 관리자 테이블에 값이 있는 것이 들어가야함. 회원번호 10바리
INSERT INTO LIBUSER VALUES('1234567891', '회원1', SYSDATE, '서울', '01012345678', SYSDATE, 'ADMIN');
INSERT INTO LIBUSER VALUES('1234567892', '회원2', SYSDATE, '하와이', '01012345678', SYSDATE, 'ADMIN');
INSERT INTO LIBUSER VALUES('1234567893', '회원3', SYSDATE, '하와이', '01012345678', SYSDATE, 'ADMIN');
--회원 조회
SELECT * FROM LIBUSER;
DELETE LIBUSER;

--3도서정보
--도서정보추가 isbn 10자리로
INSERT INTO libbookinfo VALUES(1111112345,'제목1','저자1','출판사1',SYSDATE,'ADMIN',0,SYSDATE);
INSERT INTO libbookinfo VALUES(2222212345,'제목2','저자2','출판사2',SYSDATE,'ADMIN',1,SYSDATE); --대여중
--도서조회
SELECT * FROM libbookinfo;


--4대여내역테이블
--대여내역추가
INSERT INTO libhistory VALUES(1,'1234567891',1111112345, SYSDATE, null,SYSDATE + 10, 1);
INSERT INTO libhistory VALUES(2,'1234567891',2222212345, SYSDATE, null,SYSDATE + 10, 1);
--대여내역 조회
SELECT * FROM libhistory;

commit;
--5공지게시판테이블
--필수공지 3개이상 존재해야함
INSERT INTO libboard VALUES(1,'공지제목1','공지생성', 'ADMIN', SYSDATE,'ADMIN');
INSERT INTO libboard VALUES(2,'공지제목2','TEST2', 'ADMIN', SYSDATE,'ADMIN');
INSERT INTO libboard VALUES(3,'공지제목3','TEST3', 'ADMIN', SYSDATE,'ADMIN');
commit;

INSERT INTO libboard VALUES(4,'공지제목4','공지내용4', 'ADMIN', SYSDATE,'ADMIN');
INSERT INTO libboard VALUES(5,'공지제목5','공지내용5', 'ADMIN', SYSDATE,'ADMIN');

DELETE libboard;

commit;

select * FROM libboard;

--6도서신청게시판테이블
INSERT INTO libapplyboard VALUES(1,'도서신청글1','1234567891',SYSDATE, 0,0);
INSERT INTO libapplyboard VALUES(2,'도서신청글2','1234567891',SYSDATE, 0,0);
SELECT * FROM libapplyboard;

DELETE libapplyboard;

commit;


--타입조회
DESC libuser;
--회원 확인조회
SELECT * FROM libuser WHERE userno = 1234567891;