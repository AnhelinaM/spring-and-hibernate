package by.anhelinam.cats.controller;

import by.anhelinam.cats.config.ApplicationConfig;
import by.anhelinam.cats.exception.RequestException;
import by.anhelinam.cats.exception.ValidationException;
import by.anhelinam.cats.service.UserService;

import java.util.Scanner;

public enum UserController {
    INSTANCE();

    private final UserService userService;

    UserController() {
        this.userService = ApplicationConfig.getUserServise();
    }

    public static void main(String[] args) throws RequestException, ValidationException {
        UserController.INSTANCE.run();
    }

    private void run() throws RequestException, ValidationException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String request = scanner.nextLine();
            switch (request) {
                case "getAll":
                    System.out.println(userService.getAll());
                    break;
                case "getOne":
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.println(userService.getOne(id));
                    break;
                case "signUp":
                    String name = scanner.nextLine();
                    int age = Integer.parseInt(scanner.nextLine());
                    String gender = scanner.nextLine();
                    System.out.println(userService.signUp(name, age, gender));
                    break;
                case "delete":
                    id = Integer.parseInt(scanner.nextLine());
                    userService.delete(id);
                    break;
                case "exit":
                    return;
                default:
                    throw new RequestException("There is no such request");
            }
        }
    }

}
