// Created by Andrew Stam
package com.example.myapp.controllers;

import com.example.myapp.models.User;
import com.example.myapp.models.RoleType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    // Test Data of Users
    private User[] users = {
            new User(1L, "andrew", "stam", "Andrew", "Stam", "22 October 1980", RoleType.GroupLeader, "example@example.com"),
            new User(2L, "bob123", "pass", "Bob", "Smith", "25 February 1995", RoleType.GroupMember, "example1@example.com"),
            new User(3L, "mymember", "mypass", "Member", "LastName", "31 July 2001", RoleType.GroupLeader, "example2@example.com")
    };

    private User invalid = new User(null, null, null, null);

    // Store the users as a List of Users
    private List<User> userArrayList = new ArrayList<User>(Arrays.asList(users));

    // Find all users stored
    @GetMapping("/api/users")
    public User[] findAllUsers() {
        return users;
    }

    // Delete a User by their ID
    @DeleteMapping("/api/users/{userId}")
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
    @PostMapping("/api/users")
    public List<User> updateUsers(@RequestBody User[] allUsers) {
        users = allUsers;
        userArrayList = new ArrayList<User> (Arrays.asList(users));
        return userArrayList;
    }

    // Create a new user
    @PutMapping("/api/users")
    public void createUser(@RequestBody String[] credentials) {
        // Convert string to enum
        RoleType role = RoleType.GroupLeader;
        if (credentials[3].equals(RoleType.GroupMember.toString())) {
            role = RoleType.GroupMember;
        }
        // Just have id, user/pass, and role
        User create = new User(Long.parseLong(credentials[0]), credentials[1], credentials[2], role);
        userArrayList.add(create);
    }

    // Validate a login
    @PostMapping("/api/validate")
    public User validateUser(@RequestBody String[] credentials) {
        // Just have id, user/pass, and role
        for (User u : userArrayList) {
            // Username match
            if (u.getUsername().equals(credentials[0])) {
                // Validate password
                return u.getPassword().equals(credentials[1]) ? u : invalid;
            }
        }
        // Found no match, invalid credentials
        return invalid;
    }
}
