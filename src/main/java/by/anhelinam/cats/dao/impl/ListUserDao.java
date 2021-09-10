package by.anhelinam.cats.dao.impl;

import by.anhelinam.cats.dao.UserDao;
import by.anhelinam.cats.entity.User;

import java.util.ArrayList;
import java.util.List;

public enum ListUserDao implements UserDao {
    INSTANCE;

    private final List<User> userList = new ArrayList<>();
    private int currentId = 0;

    @Override
    public List<User> getAll() {
        return userList;
    }

    @Override
    public User getOne(int id) {
        return userList.stream().filter(user -> user.getId() == id).findAny().orElse(null);
    }

    @Override
    public User signUp(String name, int age, String gender) {
        User user = new User(name, age, gender);
        user.setId(++currentId);
        userList.add(user);
        return user;
    }

    @Override
    public void delete(int id) {
        userList.removeIf(user -> user.getId() == id);
    }
}
