// Created by Andrew Stam
package com.example.myapp.controllers;

import com.example.myapp.models.User;
import com.example.myapp.models.RoleType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    // Test Data of Users
    private User[] users = {
            new User(1L, "andrew", "stam", "Andrew", "Stam", "1980-10-22", RoleType.GroupLeader, "example@example.com"),
            new User(2L, "bob123", "pass", "Bob", "Smith", "1995-02-25", RoleType.GroupMember, "example1@example.com"),
            new User(3L, "mymember", "mypass", "Member", "LastName", "2001-07-31", RoleType.GroupLeader, "example2@example.com")
    };

    private User invalid = new User(null, null, null, RoleType.GroupMember);

    // Find all users stored
    @GetMapping("/api/users")
    public User[] findAllUsers() {
        return users;
    }

    // Find user by ID
    @GetMapping("/api/users/{uid}")
    public User findUserById(@PathVariable("uid") Long id) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        // Didn't find
        return invalid;
    }

    // Delete a User by their ID
    @DeleteMapping("/api/users/{userId}")
    public User[] deleteUser(@PathVariable("userId") int userId) {
        User u = null;
        for (int i = 0; i < users.length; i++) {
            User user = users[i];
            if (user.getId() == userId) {
                u = user;
            }
        }
        ArrayList<User> temp = new ArrayList<User>(Arrays.asList(users));
        temp.remove(u);
        User[] newList = new User[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            newList[i] = temp.get(i);
        }
        users = newList;
        return newList;
    }

    // Update all users
    @PostMapping("/api/users")
    public User[] updateUsers(@RequestBody User[] allUsers) {
        users = allUsers;
        return users;
    }

    // Update given user, return updated user
    @PostMapping("/api/users/{uid}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public User updateUser(@PathVariable("uid") Long id, @RequestBody User update) {
        for (int i = 0; i < users.length; i++) {
            User u = users[i];
            if (u.getId().equals(id)) {
                users[i] = update;
                return update;
            }
        }
        // Didn't update
        return null;
    }

    // Create a new user, return if success
    @PutMapping("/api/users")
    public boolean createUser(@RequestBody String[] credentials, HttpSession session) {
        // Convert string to enum
        RoleType role = RoleType.GroupMember;
        if (credentials[3].equals(RoleType.GroupLeader.toString())) {
            role = RoleType.GroupLeader;
        }
        // If this is a taken username, reject
        for (int i = 0; i < users.length; i++) {
            User u = users[i];
            if (u.getUsername().equals(credentials[1])) {
                return false;
            }
        }
        // Just have id, user/pass, and role
        User create = new User(Long.parseLong(credentials[0]), credentials[1], credentials[2], role);
        ArrayList<User> temp = new ArrayList<User>(Arrays.asList(users));
        temp.add(create);
        User[] newList = new User[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            newList[i] = temp.get(i);
        }
        users = newList;
        session.setAttribute("userId", create.getId());
        return true;
    }

    // Validate a login
    @PostMapping("/api/validate")
    public User validateUser(@RequestBody String[] credentials, HttpSession session) {
        // Just have id, user/pass, and role
        for (int i = 0; i < users.length; i++) {
            User u = users[i];
            // Username match
            if (u.getUsername().equals(credentials[0])) {
                // Validate password
                if (u.getPassword().equals(credentials[1])) {
                    // Set a cookie for logged in user
                    session.setAttribute("userId", u.getId());
                    return u;
                } else {
                    return invalid;
                }
            }
        }
        // Found no match, invalid credentials
        return invalid;
    }

    // Retrieve session attribute
    @GetMapping("/api/session/get/{attr}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public String getSessionAttr(@PathVariable("attr") String attr, HttpSession session) {
        return (String) session.getAttribute(attr);
    }
}
