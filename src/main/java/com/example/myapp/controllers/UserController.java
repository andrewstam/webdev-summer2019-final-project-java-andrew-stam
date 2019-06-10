// Created by Andrew Stam
package com.example.myapp.controllers;

import com.example.myapp.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {
    // Test Data of Users
    private User[] users = {
            new User(1, "andrew", "stam", "Andrew", "Stam", "22 October 1980", "GroupLeader", "example@example.com"),
            new User(1, "bob123", "pass", "Bob", "Smith", "25 February 1995", "GroupMember", "example1@example.com"),
            new User(1, "mymember", "mypass", "Member", "LastName", "31 July 2001", "GroupMember", "example2@example.com")
    };

    // Store the users as a List of Users
    List<User> userArrayList = new ArrayList<User>(Arrays.asList(users));

    // Find all users stored
    @GetMapping("/users")
    public User[] findAllUsers() {
        return users;
    }

    // Delete a User by their ID
    @DeleteMapping("/users/{userId}")
    public List<User> deleteUser(@PathVariable("userId") int userId) {
        User u = null;
        for (User user : userArrayList) {
            if (user.getId() == userId) {
                u = user;
            }
        }
        userArrayList.remove(u);
        return userArrayList;
    }

    // Update all users
    @PostMapping("/users")
    public List<User> updateUsers(@RequestBody User[] allUsers) {
        users = allUsers;
        userArrayList = new ArrayList<User> (Arrays.asList(users));
        return userArrayList;
    }
}
