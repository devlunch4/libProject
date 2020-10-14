package libdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import libUtil.JDBCUtil;

public class libBoardDao {

	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "PC01";
	String password = "oracle";

	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	private libBoardDao() {
	}

	private static libBoardDao instance;

	public static libBoardDao getInstance() {
		if (instance == null) {
			instance = new libBoardDao();
		}
		return instance;
	}

	private JDBCUtil jdbc = JDBCUtil.getInstance();

}
