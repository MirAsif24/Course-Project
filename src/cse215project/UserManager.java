/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cse215project;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserManager {
   
    private static final int MAX_USERS = 100;
    private ArrayList<User> users;
    static int userCount=0;
    static String loggedInUserType;
    private String loggedInUserFullName;
    private int loggedInID;

    public UserManager() {
        users = new ArrayList<>();
    }

    public boolean signup(String username, String password, String userType) {
      
        loadUsersFromFile("users.txt");
        if (userCount >= MAX_USERS) 
        {    
        System.out.println("Cannot sign up. Maximum number of users reached.");
        return false;
        }

        int userID = generateUserID(userType);
        if (userID == -1) 
        {
        System.out.println("Error generating user ID. Signup failed.");
        return false;
        }

        User newUser = new User(username, password, userType, userID);
        users.add(newUser);
        userCount++;
        System.out.println("\t\t\t\tSignup successful! Your User ID is " + userID + "\n\n\n");
        saveUsersToFile("users.txt"); 
        return true;
    }

    private int generateUserID(String userType) {
        int baseID;
        switch (userType.toLowerCase()) {
            case "farmer":
                baseID = 2024;
                break;
            case "customer":
                baseID = 3024;
                break;
            case "retailer":
                baseID = 4024;
                break;
            default:
                return -1; // Invalid user type
        }
        return baseID + userCount;
    }

public void loadUsersFromFile(String fileName) {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        users.clear(); // Clear the list before loading users
        String line;
        
        while ((line = reader.readLine()) != null && userCount < MAX_USERS) {
            // Check and parse each line safely
            if (line.contains(": ")) {
                String[] usernameSplit = line.split(": ", 2);
                if (usernameSplit.length < 2) continue; // Skip malformed lines
                String username = usernameSplit[1];

                line = reader.readLine();
                String[] passwordSplit = line != null && line.contains(": ") ? line.split(": ", 2) : null;
                if (passwordSplit == null || passwordSplit.length < 2) continue; // Skip malformed lines
                String password = passwordSplit[1];

                line = reader.readLine();
                String[] usertypeSplit = line != null && line.contains(": ") ? line.split(": ", 2) : null;
                if (usertypeSplit == null || usertypeSplit.length < 2) continue; // Skip malformed lines
                String usertype = usertypeSplit[1];

                line = reader.readLine();
                String[] userIDSplit = line != null && line.contains(": ") ? line.split(": ", 2) : null;
                if (userIDSplit == null || userIDSplit.length < 2) continue; // Skip malformed lines
                int userID = Integer.parseInt(userIDSplit[1]);

                // Add the user only if all fields were successfully parsed
                users.add(new User(username, password, usertype, userID));
                userCount++;
                
                // Read the empty line between records if it exists
                reader.readLine();
            }
        }
        System.out.println("Users loaded from file successfully.");
    } catch (IOException e) {
        System.out.println("Error loading users from file: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.out.println("Error parsing user ID. Please check the file format.");
    }
}


    public void saveUsersToFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (User user : users) {
                writer.write(user.toString() + "\n\n");
            }
            System.out.println("Users saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        }
    }

    public boolean login(String username, int userId, String password) {
        loadUsersFromFile("users.txt");
        int attempts = 0;
        final int MAX_LOGIN_ATTEMPTS = 3;

        while (attempts < MAX_LOGIN_ATTEMPTS) {
//            System.out.println("\t\t\t\t\t\tHello, Welcome!!!");
//            System.out.println("\t\t\t\t\t\t     Login");
//
//            System.out.print("Enter username: ");
//            username = scanner.nextLine();
//            
//            System.out.print("Enter UserId: ");
//            userId = scanner.nextInt();
//            
//            scanner.nextLine();
//
//            System.out.print("Enter password: ");
//            password = scanner.nextLine();

            // Check admin credentials
            if (username.equals("admin") && password.equals("admin")) {
                System.out.println("\t\t\t\t\tAdmin Panel Logged In Successfully!!!");
                loggedInUserType = "Admin";
                return true;
                
            }

            // Check user credentials
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)&& user.getUserID()==userId) {
                    System.out.println("\t\t\t\t\t\tLogin successful!");
                    loggedInUserType = user.getUsertype();
                    loggedInID = user.getUserID();
                    return true;
                    
                }
            }

            attempts++;
            if (attempts < MAX_LOGIN_ATTEMPTS) {
                System.out.println("Invalid Password/User Name/User Id. You have " + (MAX_LOGIN_ATTEMPTS - attempts) + " attempt(s) remaining.");
            }
        }

        System.out.println("Too many failed attempts. Login failed.");
        return false;
        
    }
    
    
}
