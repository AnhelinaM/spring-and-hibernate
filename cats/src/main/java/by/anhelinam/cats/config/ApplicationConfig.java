package by.anhelinam.cats.config;

import by.anhelinam.cats.controller.UserController;
import by.anhelinam.cats.dao.UserDao;
import by.anhelinam.cats.dao.impl.ListUserDao;
import by.anhelinam.cats.service.UserService;
import by.anhelinam.cats.service.impl.UserServiceImpl;

public abstract class ApplicationConfig {
    public static UserService getUserServise() {
        return UserServiceImpl.INSTANCE;
    }

    public static UserDao getUserDao() {
        return ListUserDao.INSTANCE;
    }

    public static UserController getUserController() {
        return UserController.INSTANCE;
    }
}
