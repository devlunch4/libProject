-- 관리자 추가 필수
INSERT INTO LIBADMIN VALUES ('ADMIN', 'ADMIN', '관리자', SYSDATE);

-- 회원 추가 및 관리자는 관리자 테이블에 값이 있는 것이 들어가야함.
INSERT INTO LIBUSER VALUES(12345678900, '회원1', SYSDATE, '서울', 01012345678, SYSDATE, 'ADMIN');

--조회
SELECT userno FROM libuser WHERE userno = 12345678900;
--조회
SELECT * FROM libuser;

--커밋
commit;

--타입조회
DESC libuser;