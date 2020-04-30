package clubmanage.util;

import java.sql.Connection;
import java.sql.SQLException;


public class DBUtil {
	private static final String jdbcUrl="jdbc:mysql://rm-bp179v2wrzfw32psrdo.mysql.rds.aliyuncs.com:3306/club_manage?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	private static final String dbUser="root";
	private static final String dbPwd="hky_123456";
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Connection getConnection() throws SQLException{
		return java.sql.DriverManager.getConnection(jdbcUrl, dbUser, dbPwd);
	}

	public static void main(String[] args){
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="INSERT INTO activity_category(category_name) VALUES('力')";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
