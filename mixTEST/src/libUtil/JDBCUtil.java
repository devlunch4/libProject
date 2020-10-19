package libUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtil {

	// 접속자
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String user = "hr";
	private String password = "java";

	private JDBCUtil() {
	}

	// 인스턴스 지정
	private static JDBCUtil instance;

	public static JDBCUtil getInstance() {
		if (instance == null) {
			instance = new JDBCUtil();
		}
		return instance;
	}

	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	// 회원 검색
	public String selectOneMem(String sql) {
		Map<String, Object> row = null;
		String rowstring = null;
		try {
			con = DriverManager.getConnection(url, user, password);

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			ResultSetMetaData md = rs.getMetaData();

			int columnCount = md.getColumnCount();

			while (rs.next()) {
				row = new HashMap<>();

				for (int i = 1; i <= columnCount; i++) {
					String key = md.getColumnName(i);
					Object value = rs.getObject(i);
					row.put(key, value);
					rowstring = (String) rs.getObject(i);
					;
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
		return rowstring;
		// return row;

	}

	//
	//
	//
	// sql에 맞춰서 한줄 가져오기
	public Map<String, Object> selectOne(String sql) {
		Map<String, Object> row = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			ResultSetMetaData md = rs.getMetaData();

			int columnCount = md.getColumnCount();

			while (rs.next()) {
				row = new HashMap<>();

				for (int i = 1; i <= columnCount; i++) {
					String key = md.getColumnName(i);
					Object value = rs.getObject(i);
					row.put(key, value);
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
		return row;
	}

	// sql(조건지정)에 맞춰서 한줄 가져오기
	public Map<String, Object> selectOne(String sql, List<Object> param) {
		Map<String, Object> row = null;

		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(sql);

			for (int i = 0; i < param.size(); i++) {
				ps.setObject(i + 1, param.get(i));
			}

			rs = ps.executeQuery();

			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();

			while (rs.next()) {
				row = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String key = md.getColumnName(i);
					Object value = rs.getObject(i);
					row.put(key, value);
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
		return row;
	}

	// sql에 맞추어 모든 항목 출력
	public List<Map<String, Object>> selectList(String sql) {
		List<Map<String, Object>> list = new ArrayList<>();

		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();

			while (rs.next()) {
				Map<String, Object> row = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String key = md.getColumnName(i);
					Object value = rs.getObject(i);
					row.put(key, value);
				}
				list.add(row);
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
		return list;
	}

	// sql(조건)에 맞추어 모든 항목 출력
	public List<Map<String, Object>> selectList(String sql, List<Object> param) {
		List<Map<String, Object>> list = new ArrayList<>();

		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(sql);

			// for (int i = 0; i < param.size(); i++) {
			// ps.setObject(i + 1, param.get(i));
			// }

			rs = ps.executeQuery();

			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();

			while (rs.next()) {
				Map<String, Object> row = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String key = md.getColumnName(i);
					Object value = rs.getObject(i);
					row.put(key, value);
				}
				list.add(row);
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
		return list;
	}

	public int update(String sql) {
		int result = 0;

		try {
			con = DriverManager.getConnection(url, user, password);

			ps = con.prepareStatement(sql);

			result = ps.executeUpdate();
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
		return result;

	}

	public int update(String sql, List<Object> param) {
		int result = 0;

		try {
			con = DriverManager.getConnection(url, user, password);

			ps = con.prepareStatement(sql);

			for (int i = 0; i < param.size(); i++) {
				ps.setObject(i + 1, param.get(i));
			}

			result = ps.executeUpdate();
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
		return result;
	}

	// 추가분
	public int historyupdate(String sql) {
		int result = 0;

		try {
			con = DriverManager.getConnection(url, user, password);

			ps = con.prepareStatement(sql);

			result = ps.executeUpdate();
			if (result > 0) {
				System.out.println("가 10일 연장되었습니다.");
			} else {
				System.out.println("는 이미 연장된 도서입니다.");
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
		return result;
	}

}