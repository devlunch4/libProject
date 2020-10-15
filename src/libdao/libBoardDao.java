package libdao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		System.out.println("libBoardDao.booksearch() 상태 조회할 항목 선택>");
		int cname = ScanUtil.nextInt();

		if (cname == 0) {
			return;
		} else {
			System.out.println("검색내용>");
			System.out.println("");
		}
		String fvalue = ScanUtil.nextLine();

		String sname = null;
		switch (cname) {

		case 1:// ISBN번호 검색
			sname = "bookno";
			break;
		case 2:// 제목 검색
			sname = "title";
			fvalue = "'%" + fvalue + "%'";
			break;
		case 3: // 저자 검색
			sname = "author";
			fvalue = "'%" + fvalue + "%'";
			break;
		case 4: // 출판사 검색
			sname = "publisher";
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

			while (rs.next()) {
				System.out.println("ISBN번호\t : " + rs.getString("bookno"));
				System.out.println("제목\t : " + rs.getString("title"));
				System.out.println("저자\t : " + rs.getString("author"));
				System.out.println("출판사\t : " + rs.getString("publisher"));
				System.out.println("출간일\t : " + rs.getDate("pdate"));
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

			String sql = "SELECT findno, findcontext, userno, finddate, recomm, arrange FROM libapplyboard ORDER BY findno";

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
			// 삭제 확인 Y인 경우 트라이캐치 시행되어 삭제가됨.
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

}
