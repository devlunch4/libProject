package libdao;

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
//스태틱 확인
	private static JDBCUtil jdbc = JDBCUtil.getInstance();

	public static String selectMemUser(String memId) {
		String sql = "SELECT userno FROM libuser WHERE userno = " + memId;
		//String param = new String();
		//param = memId;
		return jdbc.selectOneMem(sql);
	}

}
