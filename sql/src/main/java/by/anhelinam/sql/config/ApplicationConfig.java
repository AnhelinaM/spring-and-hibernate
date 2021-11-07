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
    private static final String DB_PROPERTIES = "db.properties";
    private static final String DB_PROPERTY_URL_KEY = "url";
    private static final Properties dbProperties = new Properties();
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

    public static StudentService getStudentService() {
        return StudentServiceImpl.INSTANCE;
    }

    public static StudentDao getStudentDao() {
        return StudentDaoImpl.INSTANCE;
    }

    public static StudentController getStudentController() {
        return StudentController.INSTANCE;
    }

    public static ConnectionPool getConnectionPool() {
        // надо что-то с этим сделать
        ConnectionPool connectionPool;
        if (CustomCP.INSTANCE.getClass().getSimpleName() == dbProperties.getProperty("dependencies.connection-pool")) {
            connectionPool = CustomCP.INSTANCE;
        } else {
//        if (HikariCP.INSTANCE.getClass().getSimpleName() == dbProperties.getProperty("dependencies.connection-pool")){
            connectionPool = HikariCP.INSTANCE;
        }
        return connectionPool;
    }

    public static void run() throws ConnectionPoolException, ValidationException, SQLException, RequestException, InterruptedException {
        initializeProperties();
//        понятия не имею, про какие ты объекты
//        а ещё я не создавала новые пропертис
//        "за создание всех служебных объектов отвечает ApplicationConfig" аааа, потом ответит, я не понимаю, как это
//        про интерфейс у меня тоже вопросы (1-2 пункт)
//        "Проверить, что везде используются интерфейсы" ну я заменила всё это случайно на гет из аппликейшенконфига
//        "можно переписать ApplicationConfig" да, нужно, но сначала вопрос про служебные объекты
        getStudentController().run();
//        итог я поняла, если эта штука заработает, то даже получилось
    }
}
