package libdao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import libUtil.JDBCUtil;
import libUtil.ScanUtil;
import libUtil.View;
import libcontroller.libController;

public class libBoardDao {

	// 기본 접속자 정보 변수 값 설정
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "hr";
	String password = "java";
//oracle
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	//
	// 외부인스턴스 호출
	private libUserDao libuserDao = libUserDao.getInstance();

	// 인스턴스 설정
	private libBoardDao() {
	}

	private static libBoardDao instance;

	public static libBoardDao getInstance() {
		if (instance == null) {
			instance = new libBoardDao();
		}
		return instance;
	}

	// jdbc 인스턴스 호출
	private JDBCUtil jdbc = JDBCUtil.getInstance();

	// 회원메뉴>도서검색 libUservice.userBookSearch() >>> 도서조회 현재 booksearch()
	public void bookboardsearch() {
		System.out.println("1.ISBN번호 \t2.제목 \t3.저자 \t4.출판사 \t0.이전화면");
		System.out.println("조회할 항목 선택>");
		int cname = ScanUtil.nextInt();

		if (cname == 0) {
			return;
		} else {
			System.out.print("검색할 값 입력>");

		}
		String fvalue = ScanUtil.nextLine();

		String sname = null;
		String ssname = null;
		switch (cname) {

		case 1:// ISBN번호 검색
			sname = "bookno";
			ssname = "ISBN번호";
			break;
		case 2:// 제목 검색
			sname = "title";
			ssname = "제목";
			fvalue = "'%" + fvalue + "%'";
			break;
		case 3: // 저자 검색
			sname = "author";
			ssname = "저자";
			fvalue = "'%" + fvalue + "%'";
			break;
		case 4: // 출판사 검색
			sname = "publisher";
			ssname = "출판사";
			fvalue = "'%" + fvalue + "%'";
			break;
		case 0:
			return;

		default:// 잘못된 입력
			System.out.println("잘못된 입력입니다");
			break;
		}

		try {
			// 위의 검색 항목에 따른 검색 값에 따른 sql쿼리문 작성 후 조회 출력
			con = DriverManager.getConnection(url, user, password);

			String sql = "SELECT bookno, title, author, publisher, pdate, adminid, rentyesno FROM libbookinfo WHERE "
					+ sname + " LIKE " + fvalue;

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			System.out.println("===도서 검색 항목 : " + ssname + ", 검색 값 : " + fvalue);

			while (rs.next()) {
				if (rs.getString("bookno") != null || rs.getString("title") != null || rs.getString("author") != null
						|| rs.getString("publisher") != null) {
					System.out.println("ISBN번호\t : " + rs.getString("bookno"));
					System.out.println("제목\t : " + rs.getString("title"));
					System.out.println("저자\t : " + rs.getString("author"));
					System.out.println("출판사\t : " + rs.getString("publisher"));
					System.out.println("출간일\t : " + rs.getDate("pdate"));

					// 대여여부 값 확인하여 변환 출력 안내
					// rentyesno 0이면 대여가능
					// rentyesno 1이면 대여중
					int rentyesno = rs.getInt("rentyesno");
					String ibookstr = null;
					if (rentyesno == 0) {
						ibookstr = "대여 가능";
					} else if (rentyesno == 1)
						ibookstr = "대여 중";
					System.out.println("대여여부\t : " + ibookstr);
					System.out.println("=====================");
					// 검색항목 출력 완료
				} else {
					System.out.println("입력값에 대한 검색된 도서가 없습니다.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
		
		return ;
	}

	// 도서신청게시판 데이터 가져오기/내보내기 사용 불가 ???
	// public List<Map<String, Object>> selectBoardList() {
	// String sql =
	// "SELECT findno, findcontext, userno, finddate, recomm, arrange FROM
	// libapplyboard ORDER BY findno";
	//
	// return jdbc.selectList(sql);
	// }

	// 도서신청게시판 데이터 출력2
	public void selectApplyBoard() {
		System.out.print("신청번호\t신청도서정보\t\t\t회원번호\t\t신청일\t\t추천\t입고완료");
		System.out.println("");
		try {
			con = DriverManager.getConnection(url, user, password);

			String sql = "SELECT findno, findcontext, userno, TO_CHAR(finddate, 'YYYYMMDD') finddate, recomm, arrange FROM libapplyboard ORDER BY findno";

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				System.out.print(rs.getInt("findno") + "\t" + rs.getString("findcontext") + "\t\t\t"
						+ rs.getString("userno") + "\t" + rs.getString("finddate") + "\t" + rs.getInt("recomm") + "\t"
						+ rs.getInt("arrange"));
				System.out.println("");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}

	// 신청게시판 글 등록
	public void insertApply() {
		System.out.println("신청글을 작성해주세요.20자 제한>>>");
		// 20자 제한은 추후 구현
		String applystr = ScanUtil.nextLine();

		System.out.println("입력문장 : " + applystr);

		Object userno1 = libController.Loginuserno.get("USERNO");
		System.out.println("자동입력작성자 : " + userno1);
		// recomm 추천 초기값0 추천할시 1씩 증가 (추후 구현)
		// arrange 초기값0=미입고 / 1변경시=입고 (추후 구현)
		int recomm = 0;
		int arrange = 0;

		// 신청게시판 글 등록 트라이캐치 회원번호널값 외래키이므로 입력는 번호로 입력. 출력조회시는 조인문을 사용하여 이름으로
		// 출력(미구현)
		try {
			con = DriverManager.getConnection(url, user, password);
			String sql = "INSERT INTO libapplyboard VALUES((select nvl(max(findno), 0) + 1 FROM libapplyboard), " + "'"
					+ applystr + "'" + ", '" + userno1 + "'" + ", SYSDATE, " + recomm + ", " + arrange + ")";
			ps = con.prepareStatement(sql);

			int insertapplyresult = ps.executeUpdate();

			if (0 < insertapplyresult) {
				System.out.println("신청글 등록 되었습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}

	// 신청게시판 글 삭제 해당 작성자(현 로그인자/관리자는 모두삭제 가능)만 가능
	public int deleteApply(int deleteno) {
		while (true) {
			System.out.println("삭제 하시겠습니까? Y/N >>>");
			String dcheck = ScanUtil.nextLine();
			// 삭제 확인 Y인 경우 트라이캐치 시행되어 삭제가됨
			if (dcheck.equals("y") || dcheck.equals("Y")) {
				try {
					con = DriverManager.getConnection(url, user, password);
					Object duserno = libController.Loginuserno.get("USERNO");
					String sql = "DELETE FROM libapplyboard " + "where findno = " + deleteno + " AND userno = " + "'"
							+ duserno + "'";

					ps = con.prepareStatement(sql);

					int result = ps.executeUpdate();
					//
					if (0 < result) {
						System.out.println("삭제완료 되었습니다..");
					} else if (0 == result) {
						System.out.println("자신이 작성한 글이 아닙니다. 삭제할수 없습니다.");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					if (rs != null)
						try {
							rs.close();
						} catch (Exception e) {
						}
					if (ps != null)
						try {
							ps.close();
						} catch (Exception e) {
						}
					if (con != null)
						try {
							con.close();
						} catch (Exception e) {
						}
				}
				break;
				// n을 입력하는 경우 이전메뉴인 도서신청 메뉴로 돌아간다.
			} else if (dcheck.equals("n") || dcheck.equals("N")) {
				return View.APPLYBOARDSHOW;
			} else {
				System.out.println("잘못된 입력 삭제 결정을 다시해주세요");
			}
		}
		// y/n에 따른 수행 완료후 도서신청 메뉴로 돌아간다
		return View.APPLYBOARDSHOW;
	}

	// 로그인한 회원의 정보 출력 FROM libuserservice.userEdit()
	public void userInfo() {
		System.out.println("===현재 회원 정보");
		try {
			// 로그인한 사람의 정보 조회 출력
			// 로그인 회원의 정보
			Object loginuser = libController.Loginuserno.get("USERNO");

			String sql = "SELECT userno, uname, ubirth, uaddress, uphone, uadddate FROM libuser WHERE userno = " + "'"
					+ loginuser + "'";

			// 위의 검색 항목에 따른 검색 값에 따른 sql쿼리문 작성 후 조회 출력
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				System.out.println("회원번호 : " + rs.getString("userno"));
				System.out.println("이름 : " + rs.getString("uname"));
				System.out.println("생년월일 : " + rs.getDate("ubirth"));
				System.out.println("주소 : " + rs.getString("uaddress"));
				System.out.println("전화번호 : " + rs.getString("uphone"));
				System.out.println("생성일 : " + rs.getDate("uadddate"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}

	}

	// 회원이 대여중인 책 조회 출력
	public int userrent() {

		try {
			// 로그인 회원의 정보
			Object loginuser = libController.Loginuserno.get("USERNO");

			// 로그인 회원의 대여정보 추출 쿼리
			// 내역번호,제목,저자,출판사,대여일,반납예정일,연장가능유무
			String sql = "SELECT a.historyno, b.title, b.publisher, a.rentdate, a.expectdate, a.extencan FROM libhistory a JOIN libbookinfo b ON a.bookno = b.bookno WHERE a.userno = "
					+ "'" + loginuser + "'";

			// 위의 검색 항목에 따른 검색 값에 따른 sql쿼리문 작성 후 조회 출력
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			System.out.print("대여내역번호\t제목\t\t출판사\t\t대여일\t\t반납예정일\t\t연장가능유무");
			System.out.println("");
			System.out.println("------------------------------------------------------------------------------------");

			while (rs.next()) {

				int extno = rs.getInt("extencan");
				String extstr = null;
				if (extno == 1) {
					extstr = "연장가능";
				} else if (extno == 0) {
					extstr = "연장불가";
				}

				System.out.print(rs.getString("historyno"));
				System.out.print("\t\t" + rs.getString("title"));
				System.out.print("\t\t" + rs.getString("publisher"));
				System.out.print("\t\t" + rs.getDate("rentdate"));
				System.out.print("\t" + rs.getDate("expectdate"));
				System.out.println("\t" + extstr);

				// System.out.println("");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
		// 도서연장 물어보기
		System.out.println("===");
		System.out.println("1.도서연장 \t0.이전화면 돌아가기");
		int extinput = ScanUtil.nextInt();
		switch (extinput) {
		case 1:
			// 도서 반납기간 연장 메소드 호출
			libuserDao.userbookext();
			break;

		case 0:
			System.out.println("정보수정 메인 화면으로 돌아갑니다.");
			break;
		default:
			System.out.println("잘못된입력입니다.");
			break;
		}

		return View.USERBOOKSEARCH;

	}

	// 공지 게시글 리스트로 추출
	public List<Map<String, Object>> selectNoticeU() {
		String noticereaduser = "SELECT boardno, substr(btitle,1,10)||'...' btitle, substr(bcontent,1,10)||'...' bcontent, bwriter, TO_CHAR(bdate,'YYYYMMDD') bdate FROM libboard ORDER BY boardno DESC";
		return jdbc.selectList(noticereaduser);
	}

	// 공지 게스글 중 입력 받은 게시글 번호의 정보만 출력
	public void readNBone(int readNBConno) {
		String onesql = "SELECT boardno, btitle, bcontent, bwriter, TO_CHAR(bdate,'YYYYMMDD') bdate FROM libboard WHERE boardno = "
				+ "'" + readNBConno + "'";
		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(onesql);
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("공지번호 : " + rs.getInt("boardno"));
				System.out.println("공지제목 : " + rs.getString("btitle"));
				System.out.println("공지내용 : " + rs.getString("bcontent"));
				System.out.println("공지작성자 : " + rs.getString("bwriter"));
				System.out.println("공지작성일 : " + rs.getString("bdate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}

	// 관리자 고고고고
	// 도서 추가 메소드 바로 시작 insert
	// 1책번호,2제목,3저자,4출판사,5출간일,6관리자계정,7대여초기 = 0 대여중=1,8도서등록일
	public void addbook() {
		System.err.println("1.ISBN번호 10자리를 입력해주세요>>>");
		String isbnno = ScanUtil.nextLine();
		System.err.println("2.제목을 입력해주세요>>>");
		String btitle = ScanUtil.nextLine();
		System.err.println("3.저자를 입력해주세요>>>");
		String bwriter = ScanUtil.nextLine();
		System.err.println("4.출판사를 입력해주세요>>>");
		String publisher = ScanUtil.nextLine();
		System.err.println("5.출간일 년월 [YYYYMM] 양식으로 입력해주세요>>> ");
		String pdate = ScanUtil.nextLine();
		// 6번 관리자는 자동입력됨.
		// libController.Loginadminno = admin; 사용
		Object adminno = libController.Loginadminno.get("ADMINID");
		// 7번은 지정 초기값 =0
		// 8번은 도서등록날짜 sql sysdate 사용.

		String addboosql = "INSERT INTO libbookinfo values(" + isbnno + ", '" + btitle + "', '" + bwriter + "',' "
				+ publisher + "', TO_DATE('" + pdate + "', 'YYYYMM'), '" + adminno + "', '0', SYSDATE)";

		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(addboosql);
			int result = ps.executeUpdate();
			if (0 < result) {
				System.out.println("등록이 완료되었습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}

	public void findbAdmin(String modbookid) {

		String bookinfosql = "SELECT bookno, title, author, publisher, pdate, rentyesno FROM libbookinfo WHERE bookno = '"
				+ modbookid + "'";
		try {
			con = DriverManager.getConnection(url, user, password);

			ps = con.prepareStatement(bookinfosql);
			rs = ps.executeQuery();

			while (rs.next()) {
				if (rs.getString("bookno") != null || rs.getString("title") != null || rs.getString("author") != null
						|| rs.getString("publisher") != null || rs.getString("pdate") != null
						|| rs.getString("rentyesno") != null) {
					int rentyesno = rs.getInt("rentyesno");
					String ibookstr = null;
					if (rentyesno == 0) {
						ibookstr = "대여 가능";
					} else if (rentyesno == 1)
						ibookstr = "대여 중";
					System.out.println("===검색된 도서 정보");
					System.out.println("ISBN번호\t\t제목\t\t\t저자\t출판사\t\t출간일\t\t대여여부");
					System.out.println(
							rs.getString("bookno") + "\t" + rs.getString("title") + "\t\t" + rs.getString("author")
									+ "\t" + rs.getString("publisher") + "\t" + rs.getDate("pdate") + "\t" + ibookstr);
					System.out.println("===도서 검색 완료");
					// 검색항목 출력 완료

				} else {
					System.out.println("입력값에 대한 검색된 도서가 없습니다.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}

	}

	// 도서정보 수정 메소드 참고 bookboardsearch() 참고
	public void udtfindbAdmin(String modbookid) {

		System.err.println("======위 출력된 정보가 확인 된다면 수정이 가능합니다.");
		System.out.println(">>>수정 가능한 항목");
		System.out.println("1.ISBN번호\t2.제목\t3.저자\t4.출판사\t5.출간일\t6.대여여부\t0.이전화면돌아가기");
		System.out.println("수정할 항목을 선택>?>");
		int mname = ScanUtil.nextInt();

		if (mname == 0) {
			return;
		} else {
			System.out.print("변경할 값 입력(*날짜의 경우 YYYYMM 양식)>>>");
		}
		String mvalue = ScanUtil.nextLine();
		String sname = null;
		String ssname = null;

		switch (mname) {

		case 1:// ISBN번호 수정
			sname = "bookno";
			ssname = "ISBN번호";
			mvalue = "'" + mvalue + "'";
			break;
		case 2:// 제목 수정
			sname = "title";
			ssname = "제목";
			mvalue = "'" + mvalue + "'";
			break;
		case 3: // 저자 수정
			sname = "author";
			ssname = "저자";
			mvalue = "'" + mvalue + "'";
			break;
		case 4: // 출판사 수정
			sname = "publisher";
			ssname = "출판사";
			mvalue = "'" + mvalue + "'";
			break;
		case 5: // 출간일 수정
			sname = "pdate";
			ssname = "출간일";
			mvalue = "TO_DATE('" + mvalue + "', 'YYYYMM')";
			break;

		//
		// case 6:
		// sname = "rentyesno";
		// ssname = "대여여부";
		// break;
		//
		case 0:
			break;

		default:// 잘못된 입력
			System.out.println("잘못된 입력입니다");
			break;
		}

		try {
			// 위의 검색 항목에 따른 검색 값에 따른 sql쿼리문 작성 후 조회 출력
			con = DriverManager.getConnection(url, user, password);

			String updatesql = "UPDATE libbookinfo SET " + sname + " = " + mvalue + "WHERE bookno = " + modbookid;
			ps = con.prepareStatement(updatesql);

			int result = ps.executeUpdate();
			if (0 < result) {
				System.out.println("수정이 완료되었습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}

	public void deletebook() {
		System.out.println("삭제할 도서의 ISBN번호 입력>>>");
		String deleteno = ScanUtil.nextLine();

		try {
			con = DriverManager.getConnection(url, user, password);

			String dsql = "DELETE FROM libbookinfo " + "WHERE bookno = " + deleteno;
			ps = con.prepareStatement(dsql);

			int result = ps.executeUpdate();
			if (0 < result) {
				System.out.println("ISBB번호 [" + deleteno + "] 도서의 삭제가 완료되었습니다.");
			}
		} catch (SQLException e) {
			System.out.println("!!!!! ISBB번호 [" + deleteno + "] 도서는 대여 이력 존재, 제약조건에 의한 오류코드 발생");
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
			System.out.print(">>>");
			System.err.print("에러코드가 보인다면 ");
			System.out.println(" ISBB번호 [" + deleteno + "] 도서는 대여 이력이 존재합니다");
		}

	}

	public void insertB() {
		System.out.println("===공지글등록");
		System.out.println("안내순서에 맞추어 내용을 입력해주세요>>>");
		// 1공지번호 2제목 3내용 -나머지 4작성자(조인활용) 5작성일 6관리자계정(입력만)
		// 1 공지번호 자동완성 입력됨
		// 2 공지제목 입력
		System.out.println("1.공지제목을 입력해주세요");
		String btitle = ScanUtil.nextLine();
		// 3 공지내용 입력
		System.out.println("2.공지내용을 입력해주세요");
		String bcontent = ScanUtil.nextLine();

		// 4 조인문을 이용하여 관리자 이름을 출력한다.
		// 5작성일 자동입력
		// 6관리자계정 컨트롤에서 따옴
		// libController.Loginadminno
		Object cadminin = libController.Loginadminno.get("ADMINID");

		String inputb = "INSERT INTO libboard (boardno, btitle, bcontent, bwriter, bdate,adminid) values("
				+ "(SELECT nvl(MAX(boardno),0)+1 FROM libboard), '" + btitle + "', " + "'" + bcontent + "', "
				+ "(SELECT adminnm FROM libadmin WHERE adminid = '" + cadminin + "'), " + "SYSDATE, '" + cadminin
				+ "')";

		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(inputb);
			int result = ps.executeUpdate();
			if (0 < result) {
				System.out.println("공지글 등록이 완료되었습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}

	// 공지글 수정 메소드 제목 날짜만 수정
	public void modB(int modno) {
		// 이전메소드에서 게시글 번호를 가져옴
		System.out.println("1.제목\t2.내용\t0.이전화면이동");
		System.out.println("수정할 항목 선택 입력>>>");
		int mmodno = ScanUtil.nextInt();
		if (mmodno == 0) {
			return;
		} else {
			System.out.println("변경 값 입력>>>");
		}
		// !!!!!!!!!!!!!!!!!!!
		String mvalue = ScanUtil.nextLine();
		String sname = null;
		String ssname = null;

		switch (mmodno) {
		case 1: // 제목수정
			sname = "btitle";
			ssname = "공지제목";
			break;
		case 2: // 내용수정
			sname = "bcontent";
			ssname = "공지내용";
			break;
		case 0: // 이전화면이동
			break;
		default:
			System.out.println("잘못된 공지항목 선택 입력입니다.");
			break;
		}

		try {
			// 로그인한 관리자 정보의 가져옴.
			Object cadminid = libController.Loginadminno.get("ADMINID");

			// 공지글 수정변경 호출
			con = DriverManager.getConnection(url, user, password);

			String upbql = "UPDATE libboard SET " + sname + " = '" + mvalue + "' WHERE boardno = " + modno
					+ " AND adminid = '" + cadminid + "'";
			ps = con.prepareStatement(upbql);

			int result = ps.executeUpdate();

			if (0 < result) {
				System.out.println("[" + ssname + "]의 내용이 [" + mvalue + "]로 바뀌었습니다.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}
		}
	}

	// 공지글 삭제 메소드
	public void deletebd(String deletebdsql) {
		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(deletebdsql);
			// ps.executeUpdate();

			int result = ps.executeUpdate();
			if (result > 0) {
				System.out.println("!!!선택한 공지글이 삭제 되었습니다.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {// 실행
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (ps != null)
				try {
					ps.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}

		}

	}

	public void deleteUser() {
		System.out.println("삭제 하시겠습니까? Y/N >>>");
		String dcheck = ScanUtil.nextLine();
		// 삭제 확인 Y인 경우 트라이캐치 시행되어 삭제가됨
		if (dcheck.equals("y") || dcheck.equals("Y")) {
			try {

				// 삭제될 회원번호 가져오기
				Object usernoselect = libController.Loginuserno.get("USERNO");
//ㄴ비
				String delusersql = "DELETE libuser WHERE userno = " + usernoselect;

				con = DriverManager.getConnection(url, user, password);
				ps = con.prepareStatement(delusersql);
				// ps.executeUpdate();
				int result = ps.executeUpdate();

				if (result > 0) {
					System.out.println(usernoselect + "의 회원 삭제가 완료되었습니다.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {// 실행
				if (rs != null)
					try {
						rs.close();
					} catch (Exception e) {
					}
				if (ps != null)
					try {
						ps.close();
					} catch (Exception e) {
					}
				if (con != null)
					try {
						con.close();
					} catch (Exception e) {
					}

			}

		} else {
			System.out.println("잘못된 입력, 삭제 되지 않았습니다.");
		}
	}

//책대여
	public void rentbookchkid() {
		System.out.println("===도서대여");

		// 회원번호가 있는지 없는지 유무확인
		System.out.println("1.회원번호를 입력해주세요");
		String userno = ScanUtil.nextLine();
		String password = null;

		// 회원테이블과 비교하여 테이블에 해당 정보 존재시 회원 로그인 완료
		Map<String, Object> user = libuserDao.selectUser(userno, password);

		if (user == null) {
			System.out.println("!!!등록된 해당 회원번호는 없습니다.");
		} else {
			rentbookchkbook(userno);
		}
	}

	// 도서번호 입력 및 도서번호가 있는지 확인후 대여 메소드
	public void rentbookchkbook(String userno) {
		System.out.println("2.도서번호를 입력해주세요");
		String rentisbnno = ScanUtil.nextLine();
		String password = null;
		// 도서테이블에 도서정보가 있는지 확인
		Map<String, Object> book = libuserDao.selectUserbook(rentisbnno, password);
		if (book == null) {
			System.out.println("!!!등록된 해당 도서는 없습니다.");
			return;
		} else {

			System.out.println("===대여정보 전송");
			//sql 인서트
			try {
				password = "java";
				con = DriverManager.getConnection(url, user, password);
				String sql = "INSERT INTO libhistory VALUES((select nvl(max(historyno), 0) + 1 FROM libhistory), '"
						+ userno + "', '" + rentisbnno + "', SYSDATE, null, SYSDATE+10,1)";
				ps = con.prepareStatement(sql);
				int insertrentresult = ps.executeUpdate();

				if (0 < insertrentresult) {
					System.out.println("신청글 등록 되었습니다.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (rs != null)
					try {
						rs.close();
					} catch (Exception e) {
					}
				if (ps != null)
					try {
						ps.close();
					} catch (Exception e) {
					}
				if (con != null)
					try {
						con.close();
					} catch (Exception e) {
					}
			}
		}
	}

	// 클래스말단 닫는 문 -아래
}
