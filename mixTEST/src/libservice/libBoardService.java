package libservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import libUtil.JDBCUtil;
import libUtil.ScanUtil;
import libUtil.View;
import libcontroller.libController;
import libdao.libBoardDao;

public class libBoardService {
	// 기본 접속자 정보 변수 값 설정
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "hr";
	String password = "java";

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	// jdbc 호출
	private static JDBCUtil jdbc = JDBCUtil.getInstance();

	private libBoardService() {
	}

	// 인스턴스 설정
	private static libBoardService instance;

	public static libBoardService getInstance() {
		if (instance == null) {
			instance = new libBoardService();
		}
		return instance;
	}

	// libBoardDao 인스턴스 호출
	private libBoardDao libboardDao = libBoardDao.getInstance();

	// 테이블 보기 메소드 미사용????
	// public int boardList() {
	// List<Map<String, Object>> boardList = libboardDao.selectbookinfo();
	// for (int i = 0; i < boardList.size(); i++) {
	// System.out.println(boardList.get(i).get("bookno"));
	// }
	// return View.BOARD_LIST;
	// }

	// 도서 신청 게시판 보기 >> 출력후 등록 // 만약 삭제구현시 작성자 아이디가 해당 접속자글만 삭제가능함
	public int applyBoardShow() {
//		System.out.println("===도서신청게시판");

		// 도서신청게시판 조회 출력
		libboardDao.selectApplyBoard();

		System.out.println("  ");
		System.out.println("__________________________________________ ");
		System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
		System.out.println("[회원전용]           도서신청                           Ⅰ - Ⅱ      ");
		System.out.println("------------------------------------------");
		System.out.println("■ 1.도서신청 등록       2.신청도서 삭제      0.이전메뉴   ■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("입력창 >");

		int applyinput = ScanUtil.nextInt();
		switch (applyinput) {
		case 1:
//			System.out.println("==도서신청글 등록");
			libboardDao.insertApply();

			return View.APPLYBOARDSHOW;

		case 2:
			System.out.println("  ");
			System.out.println("__________________________________________ ");
			System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
			System.out.println("[회원전용]       도서신청글 삭제                        Ⅰ - Ⅲ     ");
			System.out.println("------------------------------------------");
			System.out.println("■ 삭제할 신청글 번호를 입력해주세요                              ■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("입력창 >");
			int deleteno = ScanUtil.nextInt();

			libboardDao.deleteApply(deleteno);

			return View.APPLYBOARDSHOW;

		case 0:
			System.out.println("이전화면이동");
			// 도서신청 메뉴이동(도서신청게시글 확인)
			return View.APPLYBOARDSHOW;

		default:
			break;
		}
		// 도서검색으로 이동
		return View.USERBOOKSEARCH;
	}

	// 신청일
	// 공지글 출력 3개까지
	public int readNoticeboard() {
		// 공지게시글 리스트로 추출 공지글은 무조건 3개 이상 존재하고있음.
		List<Map<String, Object>> readNoticeList = libboardDao.selectNoticeU();

		System.out.println("  ");
		System.out.println("__________________________________________ ");
		System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
		System.out.println("[회원전용]           공지게시판                        Ⅰ - Ⅱ      ");
		System.out.println("------------------------------------------");
		System.out.println("    \t       최근 공지 게시글 3건만 보여집니다    ");
		System.out.println("------------------------------------------");
		System.out.println("번호\t제목\t\t     작성자             게시일");
		for (int i = 0; i < 3; i++) {
			System.out.print(" "+readNoticeList.get(i).get("BOARDNO"));
			System.out.print("\t" + readNoticeList.get(i).get("BTITLE"));
			System.out.print("   " + readNoticeList.get(i).get("BWRITER"));
			System.out.println("\t  " + readNoticeList.get(i).get("BDATE"));
		}
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
		System.out.println("■ 조회할 게시글 번호를 입력해주세요                              ■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("입력창 >");
		return View.USERREADBOARD;
	}

	// 조회번호를 입력받아 해당 게시글 출력
	public int readNBCon() {
		// 조회할 공지게시글 번호 입력 받기
//		System.out.println("내용 확인 할 게시글 번호 입력>");
		int readNBConno = ScanUtil.nextInt();

		// 입력받은 게시글 출력
		libboardDao.readNBone(readNBConno);

		return View.USERREADBOARD;
	}

	// 대출중인 도서 조회
	public void rentbookchk() {
		System.out.println("===대출중 도서 조회");
		try {
			con = DriverManager.getConnection(url, user, password);
			// 조인문을 활용하여 정보 출력 테이블 (libbookinfo/libhistory)
			// 회원번호,대여내역번호,isbn번호,제목(일부분만),반납일예상일,연장가능유무
			String rentchksql = "SELECT a.userno, a.historyno, a.bookno, substr(b.title,1,5) title, "
					+ "TO_CHAR(a.expectdate,'YYYYMMDD') expectdate, a.extencan FROM libhistory a JOIN libbookinfo b ON a.bookno = b.bookno WHERE rentyesno = 1 ORDER BY a.userno, a.historyno";
			ps = con.prepareStatement(rentchksql);
			rs = ps.executeQuery();
			System.out.println("회원번호\t\t내역번호\tISBN번호\t\t제목(5글자까지)\t\t반납예상일\t\td연장가능여부");
			System.out.println(
					"----------------------------------------------------------------------------------------");
			while (rs.next()) {
				System.out.println(rs.getInt("userno") + "\t" + rs.getString("historyno") + "\t"
						+ rs.getString("bookno") + "\t" + rs.getString("title") + "\t\t\t" + rs.getString("expectdate")
						+ "\t\t" + rs.getString("extencan"));
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

	// 관리자계정 2-1-5 관리자로그인-도서관리-도서조회(월별)
	// 현재 입고된 도서의 수량과 전체 도서중 최근 입력된 도서 일부분만 출력 JDBC2
	public void selectbookinfo() {
		// 전체 도서 수량을 출력.
		String allbinfosql = "SELECT * FROM libbookinfo";

		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(allbinfosql);
			rs = ps.executeQuery();

			int bcount = 0;
			while (rs.next()) {
				bcount++;
			}
			System.out.println("저장된 도서 정보 수 : " + bcount);

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

	// 관리자 도서관리에서 도서조회에서 월별 조회 메소드
	public void selectmonthinput() {
		System.out.println("===해당 월에 등록된 도서 조회");
		System.out.println("조회할 년도 'YYYY'형식으로 입력해주세요>>");
		String yinput = ScanUtil.nextLine();
		System.out.println("조회할 월 'MM'형식으로 입력해주세요>>");
		String minput = ScanUtil.nextLine();

		String monthsql = "SELECT bookno, title, author, publisher, TO_CHAR(pdate,'YYYYMMDD') pdate, "
				+ "rentyesno, addbkdate FROM libbookinfo WHERE TO_CHAR(addbkdate,'YYYYMM') = " + "'" + yinput + minput
				+ "' ORDER BY pdate";

		int mcount = 0;

		System.out.println("===[" + yinput + "년 " + minput + "월] 에 등록된 도서 조회");

		System.out.println("ISBN번호\t\t제목\t\t저자\t출판사\t등록일");

		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(monthsql);
			rs = ps.executeQuery();

			while (rs.next()) {
				mcount++;
				System.out.println(rs.getString("bookno") + "\t" + rs.getString("title") + "\t\t"
						+ rs.getString("author") + "\t" + rs.getString("publisher") + "\t" + rs.getString("pdate"));
			}

			System.out.println("===[" + mcount + "]건의 [" + yinput + "년 " + minput + "월] 에 등록된 도서");
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

	// 공지글 출력 메소드 FROM noticectrl
	public void selectnotice() {
		// 공지내용 한정자 적용 필요 @
		String noticesql = "SELECT boardno, btitle, bcontent, bwriter, bdate FROM libboard ORDER BY boardno";
		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(noticesql);
			rs = ps.executeQuery();

			System.out.println("공지번호\t공지제목\t\t공지내용\t\t\t작성자\t작성일");

			while (rs.next()) {
				System.out.println(rs.getString("boardno") + "\t" + rs.getString("btitle") + "\t\t"
						+ rs.getString("bcontent") + "\t" + rs.getString("bwriter") + "\t" + rs.getDate("bdate"));
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

	// 관리자의 신규회원 등록
	public void userinfoaddin(String addusersql) {
		// 신규회원등록
		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(addusersql);
			int result = ps.executeUpdate();
			if (0 < result) {
				System.out.println("신규 회원 등록이 완료되었습니다.");
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

	// 회원 검색에서 이름검색으로 된 회원 출력
	public void viewNserch(String usernminput) {
		System.out.println("====이름 검색 [" + usernminput + "] 님의 정보");
//회원번호,이름,생년월일,주소,전화번호,생성일,,대여번호,대여중인책,반납예정일,
		System.out.println("회원번호 \t\t 이름 \t 생년월일 \t\t 주소 \t 전화번호 \t\t 생성일 \t\t 대여번호 \t 대여중인책isbn \t 반납예정일 \t 연장완료");
		try {
			con = DriverManager.getConnection(url, user, password);
			String sql = "SELECT u.userno, u.uname, TO_CHAR(u.ubirth,'YYYYMMDD') ubirth, u.uaddress, "
					+ "u.uphone, TO_CHAR(u.uadddate,'YYYYMMDD') uadddate, "
					+ "h.historyno, h.bookno, TO_CHAR(h.expectdate,'YYYYMMDD') expectdate, "
					+ "DECODE(h.extencan,1,'연장가능',0,'연장불가') extencan " + "FROM libuser u, libhistory h "
					+ "WHERE u.userno(+) = h.userno AND u.uname = '" + usernminput + "'";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {

				System.out.println(rs.getString("userno") + "\t" + rs.getString("uname") + "\t" + rs.getString("ubirth")
						+ "\t" + rs.getString("uaddress") + "\t" + rs.getString("uphone") + "\t"
						+ rs.getString("uadddate") + "\t" + rs.getString("historyno") + "\t" + rs.getString("bookno")
						+ "\t" + rs.getString("expectdate") + "\t" + rs.getString("extencan") + "\t");
			}
			System.out.println("===============================================");
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

	//회원관리 >>전화번호 조회를 통한 
	public void viewPserch(String userphinput) {
		System.out.println("===전화번호 ["+userphinput+"] 소유자의 정보");
		System.out.println("회원번호 \t\t 이름 \t 생년월일 \t\t 주소 \t 전화번호 \t\t 생성일 \t\t 대여번호 \t 대여중인책isbn \t 반납예정일 \t 연장완료");
		try {
			con = DriverManager.getConnection(url, user, password);

			String sql = "SELECT u.userno, u.uname, TO_CHAR(u.ubirth,'YYYYMMDD') ubirth,"
					+ " u.uaddress, u.uphone, TO_CHAR(u.uadddate,'YYYYMMDD') uadddate,"
					+ " h.historyno, h.bookno, TO_CHAR(h.expectdate,'YYYYMMDD') expectdate,"
					+ " DECODE(h.extencan, 1, '연장가능', 0, '연장불가') extencan" + " FROM libuser u, libhistory h"
					+ " WHERE u.userno(+) = h.userno AND u.uphone = '" + userphinput + "'";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {

				System.out.println(rs.getString("userno") + "\t" + rs.getString("uname") + "\t" + rs.getString("ubirth")
						+ "\t" + rs.getString("uaddress") + "\t" + rs.getString("uphone") + "\t"
						+ rs.getString("uadddate") + "\t" + rs.getString("historyno") + "\t" + rs.getString("bookno")
						+ "\t" + rs.getString("expectdate") + "\t" + rs.getString("extencan") + "\t");
			}
			System.out.println("===============================================");
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

	// 관리자의 회원관리 조회에서의 모든 회원 조회
	public void viewAserch() {
		System.out.println("===모든 회원 정보");
		System.out.println("회원번호 \t\t 이름 \t 생년월일 \t\t 주소 \t 전화번호 \t\t 생성일 \t\t 대여번호 \t 대여중인책isbn \t 반납예정일 \t 연장완료");
		try {
			con = DriverManager.getConnection(url, user, password);

			String sql = "SELECT u.userno, u.uname, TO_CHAR(u.ubirth,'YYYYMMDD') ubirth,"
					+ " u.uaddress, u.uphone, TO_CHAR(u.uadddate,'YYYYMMDD') uadddate,"
					+ " h.historyno, h.bookno, TO_CHAR(h.expectdate,'YYYYMMDD') expectdate,"
					+ " DECODE(h.extencan, 1, '연장가능', 0, '연장불가') extencan" + " FROM libuser u, libhistory h"
					+ " WHERE u.userno(+) = h.userno";

			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {

				System.out.println(rs.getString("userno") + "\t" + rs.getString("uname") + "\t" + rs.getString("ubirth")
						+ "\t" + rs.getString("uaddress") + "\t" + rs.getString("uphone") + "\t"
						+ rs.getString("uadddate") + "\t" + rs.getString("historyno") + "\t" + rs.getString("bookno")
						+ "\t" + rs.getString("expectdate") + "\t" + rs.getString("extencan") + "\t");
			}
			System.out.println("===============================================");
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
		
	

}// 클래스 말단
