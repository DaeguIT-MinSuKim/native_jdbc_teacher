package native_jdbc_teacher.ds;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;

public class C3P0DataSource {
	private static DataSource dataSource;
	 
    static {
        try (InputStream is = ClassLoader.getSystemResourceAsStream("c3p0.properties")) {
            Properties prop = new Properties();
            prop.load(is);

            DataSource ds_unpooled =DataSources.unpooledDataSource (prop.getProperty("url"), prop);
            Map<String, Object> overrides = new HashMap<>();
            overrides.put("maxStatements", "200");
            overrides.put("maxPoolSize", new Integer(50));

            dataSource = DataSources.pooledDataSource(ds_unpooled, overrides);
        } catch (IOException | SQLException e) {
        }
    }
     
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
     
    private C3P0DataSource(){}

}
