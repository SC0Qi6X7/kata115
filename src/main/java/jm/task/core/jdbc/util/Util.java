package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Properties;

public final class Util {

    private static final String PROPERTIES_KEY = "dbconnect.properties";

    private static final String DRIVER_KEY = "db.driver";
    private static final String HIB_DRIVER_KEY = "db.hibdriver";
    private static final String HIB_DIALECT_KEY = "db.hibdialect";
    private static final String USER_KEY = "db.user";
    private static final String DATABASE_KEY = "db.database";

    private static SessionFactory sessionFactory;

    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        try(InputStream stream = Util.class.getClassLoader().getResourceAsStream(PROPERTIES_KEY) ) {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return DriverManager.getConnection(
                properties.getProperty(DRIVER_KEY)
                + properties.getProperty(DATABASE_KEY),
                properties.getProperty(USER_KEY), ""
        );
    }

    public static SessionFactory getSessionFactory() throws SQLException {
        if (sessionFactory == null) {
            Properties settings = new Properties();
            try(InputStream stream = Util.class.getClassLoader().getResourceAsStream(PROPERTIES_KEY) ) {
                settings.load(stream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Configuration configuration = new Configuration();

            settings.put(Environment.DRIVER, settings.getProperty(HIB_DRIVER_KEY));
            settings.put(Environment.URL, settings.getProperty(DRIVER_KEY) + settings.getProperty(DATABASE_KEY));
            settings.put(Environment.USER, settings.getProperty(USER_KEY));
            settings.put(Environment.DIALECT, settings.getProperty(HIB_DIALECT_KEY));
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.FORMAT_SQL, "true");
            configuration.setProperties(settings);

            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return  sessionFactory;
    }
}
