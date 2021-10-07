package by.anhelinam.sql.config;

import by.anhelinam.sql.controller.StudentController;
import by.anhelinam.sql.dao.StudentDao;
import by.anhelinam.sql.dao.impl.SetStudentDao;
import by.anhelinam.sql.exception.ConnectionPoolException;
import by.anhelinam.sql.service.StudentService;
import by.anhelinam.sql.service.impl.StudentServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class ApplicationConfig {
    private static final String DB_PROPERTIES = "db.properties";
    private static final String DB_PROPERTY_URL_KEY = "url";
    private static final Properties dbProperties = new Properties();
    private static String dbURL;

    public static void initializeProperties() throws ConnectionPoolException {
        // получаем InputStream нашего файла конфигурации
        InputStream propertiesInputStream = ApplicationConfig.class.getClassLoader().getResourceAsStream(DB_PROPERTIES);
        if (propertiesInputStream != null) {
            try {
                // загружаем наши проперти
                dbProperties.load(propertiesInputStream);
                // url сохраним отдельно, т.к. его нам нужно будет использовать при подключении
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

    public static StudentService getStudentService() {
        return StudentServiceImpl.INSTANCE;
    }

    public static StudentDao getStudentDao() {
        return SetStudentDao.INSTANCE;
    }

    public static StudentController getStudentController() {
        return StudentController.INSTANCE;
    }
}
