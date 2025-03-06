package quickCine.Sql;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlManager extends Sql {
	
	//(!) 싱글턴화 
	static SqlManager mManager;
	static { get(); }
	public static SqlManager get() {
		if(mManager == null) { mManager = new SqlManager(); }
		return mManager;
	}
	//(!!) 싱글턴화
	
	/**전체 영화 목록을 가져온다.*/
	public ArrayList<Integer> getMovie(){
		ArrayList<Integer> result = new ArrayList<Integer>();
		read(String.format("select * from MOVIE"));
		try {
			while(SqlResult.next()) {
				// 나머지 데이터는 여기서 표기되니 입력할 CODE만 List<Integer>에 입력해 가져간다.
				result.add(SqlResult.getInt("CODE"));
				// 나머지 데이터를 콘솔에 입력
				System.out.println( String.format("< %d > : %s ( %s ) ",
					// CODE 값을 내보낼경우 순서대로 나가지 않기에 result의 순서를 내보낸다.
					result.size()-1,
					SqlResult.getString("TITLE"),
					SqlResult.getString("GENRE")
				));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally { close(); }
		return result;
	}
	
	/**해당 영화CODE의 해당되는 상영 시간을 가져온다.*/
	public ArrayList<Integer> getTimes(int movie){
		ArrayList<Integer> result = new ArrayList<Integer>();
		read(String.format(
				"SELECT s.code, m.title, t.name, (t.seat - COALESCE(SUM(r.seat), 0)) AS seat, s.times FROM schedule s"
				+ " JOIN theater t ON s.theater = t.code"
				+ " JOIN movie m ON m.code = s.movie"
				+ " LEFT JOIN reservation r ON s.code = r.schedule"
				+ " WHERE s.movie = %d"
				+ " GROUP BY s.code, m.title, t.name, t.seat, s.times"
				+ " HAVING (t.seat - COALESCE(SUM(r.seat), 0)) != 0",
				movie
		));
		try {
			while (SqlResult.next()) {
				// 나머지 데이터는 여기서 표기되니 입력할 CODE만 List<Integer>에 입력해 가져간다.
				result.add(SqlResult.getInt("CODE"));
				//나머지 데이터를 입력
				System.out.println(String.format(
					"< %d > : %s ( %s : %s ) 남은 좌석: %d",
					// CODE 값을 내보낼경우 순서대로 나가지 않기에 result의 순서를 내보낸다.
					result.size()-1,
					SqlResult.getString("title"),
					SqlResult.getString("name"),
					SqlResult.getTimestamp("times").toString(),
					SqlResult.getInt("seat")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;		
	}
	/**입력된 인원수 만큼 좌석 수가 남아있는지 확인*/
	public int isSeat(int schedule, int num) {
		int result = -1;
		read(String.format(
				"SELECT CASE"
				+ " WHEN (t.seat - COALESCE(SUM(r.seat), 0) - %d) < 0 THEN -1" 
				+ " ELSE (t.seat - COALESCE(SUM(r.seat), 0) - %d) "
				+ " END AS seat FROM reservation r"
				+ " JOIN schedule s ON s.code = r.schedule"
				+ " JOIN theater t ON t.code = s.theater"
				+ " WHERE r.schedule = %d"
				+ " GROUP BY t.seat",
				num, num,
				schedule
		));
		try {
			while (SqlResult.next()) {
				result = SqlResult.getInt("seat");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		// 좌석수가 0 이하 일경우 음수값으로 보낸다.  
		return result == -1 ?-1 :num;
	}
	
	/**예약을 Sql에 입력하는 메서드*/
	public int setTicket(int schedule, int seat, String pw) {
		int code = hasCode("reservation"); 
		int result = 0;
		try {
			result = update(String.format(
				"INSERT INTO reservation ( code, password, seat, schedule ) VALUES ( %d, %s, %d, %d )",
				code, pw, seat, schedule
			));
			// 성공하지 않은 경우, 0을 내보낸다.
			if(result == 0) { code = 0; }
			
		} finally { close(); }
		return code;
	}
	
	/**예약의 CODE로 예약 정보를 가져온다.*/
	public boolean getTicket(int code) { return getTicket(code,""); }
	/**예약의 CODE로 패스워드와 같을 경우에 예약 정보를 가져온다.*/
	public boolean getTicket(int code,String pw) {
		// 해당 쿼리가 성공했는지 반환하는 값
		boolean result = false;
		String sql = String.format(
				"SELECT s.times, m.title, m.genre, t.name FROM reservation r"
				+ " JOIN schedule s ON s.code = r.schedule"
				+ " JOIN movie m ON m.code = s.movie"
				+ " JOIN theater t ON t.code = s.theater"
				+ " WHERE r.code = %d",
				code
		);
		// 패스워드를 입력했을경우 쿼리를 추가한다.
		if(!("".equals(pw))) { sql += String.format(" AND r.password = %s",pw); }
		read(sql);
		try {
			while (SqlResult.next()) {
				//패스워드로 입력시 검증만 하기에 정보는 보여주지 않는다.
				if("".equals(pw)) {
					System.out.println(String.format(
						"예약 번호 : %d\n"
						+"예약 시간 : < %s > %s\n"
						+"예약 영화 :  %s / %s ",
						code,
						SqlResult.getString("NAME"),
						SqlResult.getTimestamp("TIMES").toString(),
						SqlResult.getString("title"),
						SqlResult.getString("genre")
					));
				}
				// 반환 값이 있었기에 성공을 표기해준다.
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	/**코드와 패스워드값을 비교해 예약 데이터를 지운다.*/
	public boolean deleteTicket(int code, String pw) {
		// 해당 쿼리가 성공했는지 반환하는 값
		int result = 0;
		try {
			result = update(String.format(
				"DELETE FROM reservation WHERE code = %d and password = '%s'",
				code, pw
			));
		} finally { close(); }
		// 변환된것이 없을경우 false 있을경우 true
		return result > 0 ?true :false;
	}
	
	/**해당 테이블에 코드중 비어있는 정수를 찾아준다.*/
	private int hasCode(String table) {
		int result = 0;
		read(String.format("SELECT MIN(t1.code) + 1 AS code"
			+ " FROM %s t1"
			+ " LEFT JOIN %s t2 ON t1.code + 1 = t2.code"
			+ " WHERE t2.code IS NULL",
			table, table
		));
		try {
			while (SqlResult.next()) {
				result = SqlResult.getInt("code");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}	
}
