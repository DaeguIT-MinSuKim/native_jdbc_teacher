package native_jdbc_teacher.ds;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Hikari_DataSource {
	private static HikariConfig config;
	private static HikariDataSource ds;
	
	static {
		config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://localhost/mysql_study?useSSL=false"); //jdbc_url
        config.setUsername("user_mysql_study"); //database_username
        config.setPassword("rootroot"); //database_password
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
	}
	
	private Hikari_DataSource() {} //외부에서 new DataSource() 금지
	
	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
}
