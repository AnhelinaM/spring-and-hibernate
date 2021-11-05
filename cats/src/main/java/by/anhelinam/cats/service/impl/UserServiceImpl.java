package by.anhelinam.cats.service.impl;

import by.anhelinam.cats.config.ApplicationConfig;
import by.anhelinam.cats.dao.UserDao;
import by.anhelinam.cats.entity.User;
import by.anhelinam.cats.exception.ValidationException;
import by.anhelinam.cats.service.UserService;

import java.util.List;

public enum UserServiceImpl implements UserService {
    INSTANCE();
    private final UserDao userDao;

    UserServiceImpl() {
        this.userDao = ApplicationConfig.getUserDao();
    }

    public List<User> getAll() {
        return userDao.getAll();
    }

    public User getOne(int id) throws ValidationException {
        if (id > 0) {
            return userDao.getOne(id);
        } else {
            throw new ValidationException("ID must be positive");
        }
    }

    public User signUp(String name, int age, String gender) throws ValidationException {
        if (name == null || age <= 0 || gender == null) {
            throw new ValidationException("Invalid data");
        }
        return userDao.signUp(name, age, gender);
    }

    public void delete(int id) throws ValidationException {
        if (id > 0) {
            userDao.delete(id);
        } else {
            throw new ValidationException("ID must be positive");
        }
    }
}
