package by.anhelinam.sql.controller;

import by.anhelinam.sql.config.ApplicationConfig;
import by.anhelinam.sql.exception.ConnectionPoolException;
import by.anhelinam.sql.exception.RequestException;
import by.anhelinam.sql.exception.ValidationException;
import by.anhelinam.sql.pool.ConnectionPool;
import by.anhelinam.sql.service.StudentService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public enum StudentController {
    INSTANCE;

    private final StudentService studentService;

    StudentController() {
        this.studentService = ApplicationConfig.getStudentService();
    }

    public static void main(String[] args) throws RequestException, ValidationException, ConnectionPoolException, SQLException, InterruptedException {
        ApplicationConfig.initializeProperties();
        StudentController.INSTANCE.run();
    }

    private void run() throws RequestException, ValidationException, SQLException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String request = scanner.nextLine();
            switch (request) {
                case "getAll":
                    System.out.println(studentService.getAll());
                    break;
                case "getOne":
                    long id = Long.parseLong(scanner.nextLine());
                    System.out.println(studentService.getOne(id));
                    break;
                case "addOne":
                    String name = scanner.nextLine();
                    Date birthday = Date.valueOf(scanner.nextLine());
                    int grade = Integer.parseInt(scanner.nextLine());
                    System.out.println(studentService.addOne(name, birthday, grade));
                    break;
                case "updateOne":
                    id = Long.parseLong(scanner.nextLine());
                    name = scanner.nextLine();
                    birthday = Date.valueOf(scanner.nextLine());
                    grade = Integer.parseInt(scanner.nextLine());
                    System.out.println(studentService.updateOne(id, name, birthday, grade));
                    break;
                case "delete":
                    id = Long.parseLong(scanner.nextLine());
                    studentService.delete(id);
                    break;
                case "exit":
                    ConnectionPool.INSTANCE.closePool();
                    return;
                default:
                    throw new RequestException("There is no such request");
            }
        }
    }
}
