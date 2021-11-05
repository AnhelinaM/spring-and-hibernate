package by.anhelinam.cats.service;

import by.anhelinam.cats.entity.User;
import by.anhelinam.cats.exception.ValidationException;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getOne(int id) throws ValidationException;

    User signUp(String name, int age, String gender) throws ValidationException;

    void delete(int id) throws ValidationException;
}
