package org.user_crud;


import org.user_crud.dao.UserDaoImpl;
import org.user_crud.entity.User;
import org.user_crud.service.UserService;
import org.user_crud.service.UserServiceImpl;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        UserService service = new UserServiceImpl(new UserDaoImpl());
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== USER MENU =====");
            System.out.println("1. Create User");
            System.out.println("2. Get User by ID");
            System.out.println("3. Update User");
            System.out.println("4. Delete User");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            try {
                switch (choice) {

                    case 1 -> {
                        System.out.print("Name: ");
                        String name = sc.nextLine();

                        System.out.print("Email: ");
                        String email = sc.nextLine();

                        System.out.print("Age: ");
                        int age = sc.nextInt();
                        sc.nextLine();

                        User user = new User();
                        user.setName(name);
                        user.setEmail(email);
                        user.setAge(age);

                        service.createUser(user);
                        System.out.println("User created successfully!");
                    }

                    case 2 -> {
                        System.out.print("ID: ");
                        int id = sc.nextInt();

                        User user = service.getUserById(id);
                        System.out.println(user != null ? user : "User not found");
                    }

                    case 3 -> {
                        System.out.print("ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Name: ");
                        String name = sc.nextLine();

                        System.out.print("Email: ");
                        String email = sc.nextLine();

                        System.out.print("Age: ");
                        int age = sc.nextInt();
                        sc.nextLine();

                        User user = new User();
                        user.setId(id);
                        user.setName(name);
                        user.setEmail(email);
                        user.setAge(age);

                        service.updateUser(user);
                        System.out.println("User updated successfully!");
                    }

                    case 4 -> {
                        System.out.print("ID: ");
                        int id = sc.nextInt();

                        service.deleteUser(id);
                        System.out.println("User deleted successfully!");
                    }

                    case 0 -> {
                        System.out.println("Exiting...");
                        sc.close();
                        System.exit(0);
                    }

                    default -> System.out.println("Invalid option!");
                }

            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
}