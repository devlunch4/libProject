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

	//
	//
	//
	//
	//
	// 책정보 호출 ???미사용?
	// public List<Map<String, Object>> selectbookinfo() {
	// String sql =
	// "SELECT bookno, title, author,publisher, pdate,adminid,rentyesno FROM libbookinfo";
	//
	// return jdbc.selectList(sql);
	// }

	//
	//
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
			System.out
					.println("===도서 검색 항목 : " + ssname + ", 검색 값 : " + fvalue);

			while (rs.next()) {
				if (rs.getString("bookno") != null
						|| rs.getString("title") != null
						|| rs.getString("author") != null
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
	}

	// 도서신청게시판 데이터 가져오기/내보내기 사용 불가 ???
	// public List<Map<String, Object>> selectBoardList() {
	// String sql =
	// "SELECT findno, findcontext, userno, finddate, recomm, arrange FROM libapplyboard ORDER BY findno";
	//
	// return jdbc.selectList(sql);
	// }

	// 도서신청게시판 데이터 출력2
	public void selectApplyBoard() {
		System.out.print("신청번호\t신청도서정보\t\t회원번호\t\t신청일\t\t추천\t입고완료");
		System.out.println("");
		try {
			con = DriverManager.getConnection(url, user, password);

			String sql = "SELECT findno, findcontext, userno, TO_DATE(TO_CHAR(finddate, 'YYYYMMDD'),'YYYYMMDD') finddate, recomm, arrange FROM libapplyboard ORDER BY findno";

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				System.out.print(rs.getInt("findno") + "\t"
						+ rs.getString("findcontext") + "\t\t\t"
						+ rs.getString("userno") + "\t"
						+ rs.getDate("finddate") + "\t" + rs.getInt("recomm")
						+ "\t" + rs.getInt("arrange"));
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
			String sql = "INSERT INTO libapplyboard VALUES((select nvl(max(findno), 0) + 1 FROM libapplyboard), "
					+ "'"
					+ applystr
					+ "'"
					+ ", '"
					+ userno1
					+ "'"
					+ ", SYSDATE, " + recomm + ", " + arrange + ")";
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
					String sql = "DELETE FROM libapplyboard "
							+ "where findno = " + deleteno + " AND userno = "
							+ "'" + duserno + "'";

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
		System.out.println("==로그인한 회원 정보");
		try {
			// 로그인한 사람의 정보 조회 출력
			// 로그인 회원의 정보
			Object loginuser = libController.Loginuserno.get("USERNO");

			String sql = "SELECT userno, uname, ubirth, uaddress, uphone, uadddate FROM libuser WHERE userno = "
					+ "'" + loginuser + "'";

			// 위의 검색 항목에 따른 검색 값에 따른 sql쿼리문 작성 후 조회 출력
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				System.out.println("회원번호 : " + rs.getString("userno"));
				System.out.println("이     름 : " + rs.getString("uname"));
				System.out.println("생년월일 : " + rs.getDate("ubirth"));
				System.out.println("주     소 : " + rs.getString("uaddress"));
				System.out.println("전화번호 : " + rs.getString("uphone"));
				System.out.println("생 성  일 : " + rs.getDate("uadddate"));
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
			System.out
					.println("------------------------------------------------------------------------------------");

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
		System.out.println("1.도서연장 0.이전화면 돌아가기");
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

	// 공지 게시글중 번호 입력 받아 추출 및 프린트

}
