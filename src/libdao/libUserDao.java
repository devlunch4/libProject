package libdao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import libUtil.JDBCUtil;

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

	private static JDBCUtil jdbc = JDBCUtil.getInstance();
	
////스태틱 확인 ##userno 단일 사용시
//	public static String selectUser(String userno) {
//	String sql = "SELECT userno FROM libuser WHERE userno = " + userno;
//		return jdbc.selectOneMem(sql);
//	}

	// 회원로그인 조회시 사용자 조회
	public Map<String, Object> selectUser(String userno, Object password) {
		String sql = "SELECT userno FROM libuser WHERE userno = " + userno;
//		List<Object> param = new ArrayList<>();
//		param.add(userno);
//		param.add(password);
		// 해당 값 출력을 위해 jdbc 사용
		return jdbc.selectOne(sql);
	}

}
