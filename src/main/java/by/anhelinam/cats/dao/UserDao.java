package by.anhelinam.cats.dao;

import by.anhelinam.cats.entity.User;

import java.util.List;

public interface UserDao {
    List<User> getAll();

    User getOne(int id);

    User signUp(String name, int age, String gender);

    void delete(int id);
}
