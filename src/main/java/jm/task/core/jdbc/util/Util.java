package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.Driver;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Properties;

public final class Util {

    private static final String PROPERTIES_KEY = "dbconnect.properties";

    private static final String DRIVER_KEY = "db.driver";
    private static final String USER_KEY = "db.user";
    private static final String DATABASE_KEY = "db.database";

    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        try(InputStream stream = Util.class.getClassLoader().getResourceAsStream(PROPERTIES_KEY) ) {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return DriverManager.getConnection(
                properties.getProperty(DRIVER_KEY) + properties.getProperty(DATABASE_KEY)
                , properties.getProperty(USER_KEY), ""
        );
    }
}
