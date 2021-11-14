package by.anhelinam.sql.config;

import by.anhelinam.sql.controller.StudentController;
import by.anhelinam.sql.dao.StudentDao;
import by.anhelinam.sql.dao.impl.StudentDaoImpl;
import by.anhelinam.sql.exception.ConnectionPoolException;
import by.anhelinam.sql.exception.RequestException;
import by.anhelinam.sql.exception.ValidationException;
import by.anhelinam.sql.pool.ConnectionPool;
import by.anhelinam.sql.pool.impl.CustomCP;
import by.anhelinam.sql.pool.impl.HikariCP;
import by.anhelinam.sql.service.StudentService;
import by.anhelinam.sql.service.impl.StudentServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public abstract class ApplicationConfig {
    private static final String APP_PROPERTIES = "application.properties";
    private static final String DB_PROPERTIES = "db.properties";
    private static final String DB_PROPERTY_URL_KEY = "url";
    private static final Properties dbProperties = new Properties();
    private static final Properties appProperties = new Properties();
    private static String dbURL;

    public static void initializeProperties() throws ConnectionPoolException {
        InputStream propertiesInputStream = ApplicationConfig.class.getClassLoader().getResourceAsStream(DB_PROPERTIES);
        if (propertiesInputStream != null) {
            try {
                dbProperties.load(propertiesInputStream);
                dbURL = dbProperties.getProperty(DB_PROPERTY_URL_KEY);
            } catch (IOException e) {
                throw new ConnectionPoolException("Failed to load database properties");
            }
        } else {
            throw new ConnectionPoolException("Failed to read database properties file");
        }
    }

    public static Properties getDbProperties() {
        return dbProperties;
    }

    public static String getDbURL() {
        return dbURL;
    }

    public static void run() throws ConnectionPoolException, ValidationException, SQLException, RequestException, InterruptedException {
        initializeProperties();
        ConnectionPool connectionPool;

        InputStream propertiesInputStream = ApplicationConfig.class.getClassLoader().getResourceAsStream(APP_PROPERTIES);
        if (propertiesInputStream != null) {
            try {
                appProperties.load(propertiesInputStream);
            } catch (IOException e) {
                throw new ConnectionPoolException("Failed to load application properties");
            }
        } else {
            throw new ConnectionPoolException("Failed to read application properties file");
        }

        if (CustomCP.INSTANCE.getClass().getSimpleName() == appProperties.getProperty("dependencies.connection-pool")) {
            connectionPool = CustomCP.INSTANCE;
        } else {
//        if (HikariCP.INSTANCE.getClass().getSimpleName() == dbProperties.getProperty("dependencies.connection-pool")){
            connectionPool = HikariCP.INSTANCE;
        }
        StudentDao studentDao = new StudentDaoImpl(connectionPool);
        StudentService studentService = new StudentServiceImpl(studentDao);
        StudentController studentController = new StudentController(studentService, connectionPool);
        studentController.run();
    }
}
