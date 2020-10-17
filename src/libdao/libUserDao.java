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

	// 로그인한 회원의 정보 출력 FROM libuserservice.userEdit() 이전메소드의 회원정보 출력 다음
	// 수정을 위해 실행되는 메소드
	public int userModify() {
		System.out.println("===수정 가능한 항목 선택");
		System.out.println("1.주소 \t2.전화번호 \t0.이전메뉴돌아가기");
		int minput = ScanUtil.nextInt();
		String msql = null;
		String sql = null;
		Object muserno = libController.Loginuserno.get("USERNO");
		switch (minput) {
		case 1:// 주소변경
			System.out.println("변경할 주소 내용 입력해주세요>>");
			msql = ScanUtil.nextLine();
			sql = "UPDATE libuser SET uaddress = '" + msql
					+ "' WHERE userno = '" + muserno + "'";
			return jdbc.update(sql);

		case 2:
			System.out.println("변경할 전화번호를 입력해주세요>>");
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
		System.out.println("도서연장할 도서 대여번호 입력>>");
		int historyinput = ScanUtil.nextInt();
		Object userno = libController.Loginuserno.get("USERNO");
		// 연장 가능 여부 확인후 연장가능여부값 변경 및 반납예정일 10일 증가
		String historysql = "UPDATE libhistory SET extencan = 0, expectdate = (SELECT expectdate FROM libhistory WHERE historyno = "
				+ "'"
				+ historyinput
				+ "') + 10 "
				+ "WHERE historyno = "
				+ "'"
				+ historyinput
				+ "' AND userno = "
				+ "'"
				+ userno
				+ "' AND extencan = 1";

		// jdbc.update(historysql);

		// 연습용 만들어봄 (연장 유무 확인을 위해 출력 추가하여 실 사용)
		System.err.print("선택 된 [" + historyinput+"] 번 도서");
		jdbc.historyupdate(historysql);
	}

}