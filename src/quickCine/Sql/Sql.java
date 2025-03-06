package quickCine.Sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sql {
	private User user = new User();
	private Connection connect = null;
	private Statement query = null;
	public ResultSet SqlResult = null;
	
	/**Sql 접속*/
	private void connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connect = DriverManager.getConnection(user.url,user.id,user.pw);
			query = connect.createStatement();
		} 
		catch(SQLException e) { e.printStackTrace(); } 
		catch(ClassNotFoundException e) { e.printStackTrace(); }
	}
	
	/**sql의 데이터를 가져온다.*/
	public void read(String sql) {
	    connect();
	    try {
	    	SqlResult = query.executeQuery(sql);
	    } 
	    catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**sql의 데이터 상태를 변환*/
	public int update(String sql)
	{
		int result = 0;
		connect();
		try { result = query.executeUpdate(sql); } 
		catch (SQLException e) { e.printStackTrace(); }
		return result;
	}

	/**sql 접속을 닫는다.*/
	public void close() {
		try {
			if (SqlResult != null) { SqlResult.close(); }
			if (query != null) { query.close(); }
			if (connect != null) { connect.close(); }
		} 
		catch (Exception e) { e.printStackTrace(); } 
		finally {
			SqlResult = null;
			query = null;
			connect = null;
		}
	}
}