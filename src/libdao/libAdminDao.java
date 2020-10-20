package libdao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import libUtil.JDBCUtil;

public class libAdminDao {

	// jdbc 인스턴스 호출
	private JDBCUtil jdbc = JDBCUtil.getInstance();

	private libAdminDao() {
	}
	
	// libUserDao 인스턴스 생성
	private static libAdminDao instance;

	public static libAdminDao getInstance() {
		if (instance == null) {
			instance = new libAdminDao();
		}
		return instance;
	}

	// oracle
	// 관리자계졍 관련
	// 관리자계졍 관련
	// 관리자계졍 관련

	// 관리자계정 로그인시 관리자계정 일치 확인
	public Map<String, Object> selectAdmin(String adminid, String adminpw) {
		String sql = "SELECT adminid, adminpw FROM libadmin WHERE adminid = ? AND adminpw = ?";

		List<Object> param = new ArrayList<>();
		param.add(adminid);
		param.add(adminpw);
		return jdbc.selectOne(sql, param);
	}
}