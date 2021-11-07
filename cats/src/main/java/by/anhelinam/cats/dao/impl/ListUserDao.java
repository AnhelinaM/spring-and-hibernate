package by.anhelinam.cats.dao.impl;

import by.anhelinam.cats.dao.UserDao;
import by.anhelinam.cats.entity.User;

import java.util.ArrayList;
import java.util.List;

public enum ListUserDao implements UserDao {
    INSTANCE;

    private List<User> userList = new ArrayList<>();
    private int currentId = 0;

    public List<User> getAll() {
        return userList;
    }

    public User getOne(int id) {
        return userList.stream().filter(user -> user.getId() == id).findAny().orElse(null);
    }

    public User signUp(String name, int age, String gender) { //вообще не помню, какой там метод
        User user = new User(name, age, gender);
        user.setId(++currentId);
        userList.add(user);
        return user;
    }

    public void delete(int id) {
        userList.removeIf(user -> user.getId() == id);
    }
}
