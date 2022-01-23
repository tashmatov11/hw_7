package peaksoft;

import peaksoft.model.User;
import peaksoft.service.UserService;
import peaksoft.service.UserServiceImpl;
import peaksoft.util.Util;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    static Scanner console = new Scanner(System.in);

    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        while (true) {
            try {
                comandy();
                String a = console.nextLine();
                if (a.equals("-")) {
                    System.exit(0);
                }
                switch (a) {
                    case "1": {
                        if (!TableValidation()) {
                            System.out.println(ANSI_RED + "Таблица уже создана!!!" + ANSI_RESET);
                        } else {
                            userService.createUsersTable();
                        }
                        break;
                    }
                    case "2": {
                        if (TableValidation()) {
                            System.out.println(ANSI_RED + "Нет таблиц для удаления!!!" + ANSI_RESET);
                        } else {
                            userService.dropUsersTable();
                        }
                        break;
                    }
                    case "3": {
                        if (!TableValidation()) {
                            try {
                                System.out.print("Количество людей: ");
                                int n = console.nextInt();
                                console.nextLine();
                                for (int i = 1; i <= n; i++) {
                                    try {

                                        System.out.print("Введите имя: ");
                                        String name = console.nextLine();
                                        System.out.print("Введите Фамилию: ");
                                        String lastname = console.nextLine();
                                        System.out.print("Введите возраст: ");
                                        int age = console.nextInt();
                                        console.nextLine();
                                        User user1 = new User(name, lastname, (byte) age);
                                        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
                                    } catch (InputMismatchException e) {
                                        System.out.println(ANSI_RED + "Возраст должжен быть написан цифрами!!!" + ANSI_RESET);
                                        console.nextLine();
                                    }
                                }
                            } catch (InputMismatchException e) {
                                System.out.println(ANSI_RED + "Вы нерпавильно ввели количество!!!" + ANSI_RESET);
                                console.nextLine();
                            }
                        } else {
                            System.out.println(ANSI_RED + "Таблица ещё не создана!!!" + ANSI_RESET);
                        }
                        break;
                    }
                    case "4": {
                        if (TableValidation()) {
                            System.out.println(ANSI_RED + "Таблица ещё не создана!!!" + ANSI_RESET);
                        } else if (userService.getAllUsers().size() == 0) {
                            System.out.println(ANSI_RED + "Таблица пуста!!!" + ANSI_RESET);
                        } else {
                            System.out.print("Введите номер id для удаления: ");
                            try {
                                int q = console.nextInt();
                                console.nextLine();
                                userService.removeUserById(q);
                            } catch (InputMismatchException e) {
                                System.out.println(ANSI_RED + "Вы неправильно ввели id!!!" + ANSI_RESET);
                                console.nextLine();
                            }

                        }
                        break;
                    }
                    case "5": {
                        if(TableValidation()) {
                            System.out.println(ANSI_RED + "Таблица ещё не создана!!!" + ANSI_RESET);
                        }else if (userService.getAllUsers().size() == 0) {
                            System.out.println(ANSI_RED + "Таблица пуста!!!" + ANSI_RESET);
                        } else {
                            System.out.println(ANSI_YELLOW + "-------Все пользователи-------" + ANSI_RESET);
                            System.out.println(userService.getAllUsers());
                        }
                        break;
                    }
                    case "6": {
                        if (TableValidation()) {
                            System.out.println(ANSI_RED + "Таблица ещё не создана!!!" + ANSI_RESET);
                        }else if (userService.getAllUsers().size() == 0){
                            System.out.println(ANSI_RED + "Таблица пуста!!!" + ANSI_RESET);
                        }else {
                            userService.cleanUsersTable();
                        }
                        break;
                    }
                    default: {
                        System.out.println(ANSI_RED + "Такой кнопки не существует!!!" + ANSI_RESET);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println(ANSI_RED + "Нельзя вводить буквы!!!" + ANSI_RESET);
                console.nextLine();
            }
        }
    }

    public static void comandy() {
        System.out.println(ANSI_GREEN + "Нажмите 1 чтобы создать таблицу\n" +
                "Нажмите 2 чтобы удалить таблицу\n" +
                "Нажмите 3 чтобы добавить данные\n" +
                "Нажмите 4 чтобы удалить по id\n" +
                "Нажмите 5 чтобы вывести данные в таблице\n" +
                "Нажмите 6 чтобы очистить таблицу\n" +
                "Нажмите - чтобы завершить" + ANSI_RESET);
    }

    public static boolean TableValidation() {
        boolean check = false;
        DatabaseMetaData meta = null;
        try {
            meta = Util.connect().getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet resultSet = null;
        try {
            resultSet = meta.getTables(null, null, "users", new String[]{"TABLE"});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (!resultSet.next()) {
                check = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }
}
