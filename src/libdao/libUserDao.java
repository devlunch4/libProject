package libdao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import libUtil.JDBCUtil;
import libUtil.ScanUtil;
import libcontroller.libController;

public class libUserDao {

	private libUserDao() {
	}

	// libUserDao 인스턴스 생성
	private static libUserDao instance;

	public static libUserDao getInstance() {
		if (instance == null) {
			instance = new libUserDao();
		}
		return instance;
	}

	// jdbc 호출
	private static JDBCUtil jdbc = JDBCUtil.getInstance();

	// 회원로그인 조회시 회원테이블값 일치 확인 조회
	public Map<String, Object> selectUser(String userno, Object password) {
		String sql = "SELECT userno FROM libuser WHERE userno = " + userno;
		return jdbc.selectOne(sql);
	}

	// 책이 있는지 없는지와 대여 가능 여부 확인
	public Map<String, Object> selectUserbook(String rentisbnno, Object password) {
		String sql = "SELECT bookno FROM libbookinfo WHERE bookno = "
				+ rentisbnno + "AND rentyesno = 0";
		return jdbc.selectOne(sql);
	}

	// 회원 이름 가져오기 매칭확인 (회원검색>이름검색에서 확인가능)
	public Map<String, Object> selectUsernm(String uname) {
		String sql = "SELECT uname FROM libuser WHERE uname = '" + uname + "'";
		return jdbc.selectOne(sql);
	}

	// 회원 이름 가져오기 매칭확인 (회원검색>전화검색에서 확인가능)
	public Map<String, Object> selectUserPn(String uname1) {
		String sql = "SELECT uphone FROM libuser WHERE uphone = '" + uname1
				+ "'";
		return jdbc.selectOne(sql);
	}

	// 로그인한 회원의 정보 출력 FROM libuserservice.userEdit() 이전메소드의 회원정보 출력 다음
	// 수정을 위해 실행되는 메소드
	public int userModify() {
		// 이전 메소드(libboardDao.userInfo();) 이어지는 창 그래픽
		System.out.println("■ 1.주소수정        2.전화번호 수정           0.이전메뉴    ■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("입력창 >");
		int minput = ScanUtil.nextInt();

		String msql = null;
		String sql = null;

		// 접속중인 회원의 정보 연결 및 지정
		Object muserno = libController.Loginuserno.get("USERNO");
		switch (minput) {
		case 1:// 주소변경
			System.out.println("  ");
			System.out.println("__________________________________________ ");
			System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
			System.out
					.println("[회원전용]           주소수정                          Ⅰ - Ⅳ      ");
			System.out.println("------------------------------------------");
			System.out
					.println("■ 변경 하실 주소를 입력해주세요                                    ■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("입력창 >");

			// 입력값으로 수정
			msql = ScanUtil.nextLine();
			sql = "UPDATE libuser SET uaddress = '" + msql
					+ "' WHERE userno = '" + muserno + "'";
			return jdbc.update(sql);

		case 2:
			System.out.println("  ");
			System.out.println("__________________________________________ ");
			System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
			System.out
					.println("[회원전용]           주소수정                          Ⅰ - Ⅳ      ");
			System.out.println("------------------------------------------");
			System.out
					.println("■ 변경 하실 전화번호를 입력해주세요                                    ■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("입력창 >");
			msql = ScanUtil.nextLine();
			sql = "UPDATE libuser SET uphone = '" + msql + "' WHERE userno = '"
					+ muserno + "'";
			return jdbc.update(sql);

		case 0:
			break;

		default:
			break;
		}
		//
		//
		return 0;

	}

	// 관리자의 회원정보 수정 위 메소드 보다 권한이 추가
	public int userModifyAdmin() {
		System.out.println("  ");
		System.out.println("__________________________________________ ");
		System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
		System.out.println("[관리자]           회원정보 수정                      Ⅰ - Ⅳ      ");
		System.out.println("------------------------------------------");
		System.out.println("■ 1.이름수정    2.생년월일 수정    3.주소수정               ■");
		System.out.println("■ 4.전화번호 수정                        0.변경취소               ■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("입력창 >");
		
		 
		int minput = ScanUtil.nextInt();
		String msql = null;
		String sql = null;
		Object muserno = libController.Loginuserno.get("USERNO");
		switch (minput) {
		case 1: // 이름
			System.out.println("  ");
			System.out.println("__________________________________________ ");
			System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
			System.out.println("[관리자]           회원이름 수정                      Ⅰ - Ⅳ      ");
			System.out.println("------------------------------------------");
			System.out.println("■ 변경할 이름을 입력해주세요.                   ■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("입력창 >");
			msql = ScanUtil.nextLine();
			sql = "UPDATE libuser SET uname = '" + msql + "' WHERE userno = '"
					+ muserno + "'";
			return jdbc.update(sql);

		case 2: // 생년월일
			System.out.println("  ");
			System.out.println("__________________________________________ ");
			System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
			System.out.println("[관리자]           회원생일 수정                      Ⅰ - Ⅳ      ");
			System.out.println("------------------------------------------");
			System.out.println("■ 변경할 생년월일을 YYYYMMDD 양식으로 입력해주세요   ■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("입력창 >");
			msql = ScanUtil.nextLine();
			sql = "UPDATE libuser SET ubirth = TO_DATE('" + msql
					+ "', 'YYYYMMDD') WHERE userno = '" + muserno + "'";
			return jdbc.update(sql);

		case 3:// 주소변경
			System.out.println("  ");
			System.out.println("__________________________________________ ");
			System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
			System.out.println("[관리자]           회원주소 수정                      Ⅰ - Ⅳ      ");
			System.out.println("------------------------------------------");
			System.out.println("■ 변경할 주소 내용 입력해주세요                                    ■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("입력창 >");
			msql = ScanUtil.nextLine();
			sql = "UPDATE libuser SET uaddress = '" + msql
					+ "' WHERE userno = '" + muserno + "'";
			return jdbc.update(sql);

		case 4:
			System.out.println("  ");
			System.out.println("__________________________________________ ");
			System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
			System.out.println("[관리자]        회원전화번호 수정                      Ⅰ - Ⅳ      ");
			System.out.println("------------------------------------------");
			System.out.println("■ 변경할 전화번호를 입력해주세요                                  ■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("입력창 >");
			msql = ScanUtil.nextLine();
			sql = "UPDATE libuser SET uphone = '" + msql + "' WHERE userno = '"
					+ muserno + "'";
			return jdbc.update(sql);

		case 0:
			break;

		default:
			break;
		}
		//
		//
		return 0;

	}

	// 회원이 빌린 책중 반납기간 연장할 책을 선택하여 해당 책 증가
	public void userbookext() {
		System.out.println("  ");
		System.out.println("__________________________________________ ");
		System.out.println("■         xx 도서관 도서관리 프로그램        —  ▢  X ■ ");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ ");
		System.out.println("[관리자]           회원도서 연장                      Ⅰ - Ⅱ      ");
		System.out.println("------------------------------------------");
		System.out.println("■ 도서연장할 도서 대여번호 입력                                    ■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("입력창 >");
		int historyinput = ScanUtil.nextInt();
		Object userno = libController.Loginuserno.get("USERNO");
		// 연장 가능 여부 확인후 연장가능여부값 변경 및 반납예정일 10일 증가
		String historysql = "UPDATE libhistory SET extencan = 1, expectdate = (SELECT expectdate FROM libhistory WHERE historyno = "
				+ "'"
				+ historyinput
				+ "') + 10 "
				+ "WHERE historyno = "
				+ "'"
				+ historyinput
				+ "' AND userno = "
				+ "'"
				+ userno
				+ "' AND extencan = 0";

		// jdbc.update(historysql);

		// 연습용 만들어봄 (연장 유무 확인을 위해 출력 추가하여 실 사용)
//		System.err.print("선택 된 [" + historyinput + "] 번 도서");
		jdbc.historyupdate(historysql);
	}

	// 해당 도서가 대여테이블에 있는지 추출
	public Map<String, Object> selectrentbook(String inbookisbn, String password) {
		String sql = "SELECT bookno FROM libhistory WHERE bookno = '"
				+ inbookisbn + "'";
		return jdbc.selectOne(sql);

	}

}