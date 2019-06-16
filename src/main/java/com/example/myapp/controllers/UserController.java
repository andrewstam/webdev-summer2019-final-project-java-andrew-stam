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
            new User(1L, "andrew", "stam", "Andrew", "Stam", "1980-10-22", RoleType.GroupLeader, "example@example.com", null, null, null),
            new User(2L, "bob123", "pass", "Bob", "Smith", "1995-02-25", RoleType.GroupMember, "example1@example.com", null, null, null),
            new User(3L, "mymember", "mypass", "Member", "LastName", "2001-07-31", RoleType.GroupLeader, "example2@example.com", null, null, null)
    };

    private User invalid = new User(null, null, null, RoleType.GroupMember);

    // Find all users stored
    @GetMapping("/api/users")
    public User[] findAllUsers() {
        // Block all passwords from being sent
        User[] ret = new User[users.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = users[i].safeCopy();
        }
        return ret;
    }

    // Find newest user (largest ID)
    @GetMapping("/api/users/new")
    public User findNewestUser() {
        User newest = users[0];
        for (User u : users) {
            if (u.getId() > newest.getId()) {
                newest = u;
            }
        }
        // Block password from being sent
        return newest.safeCopy();
    }

    // Find user by ID
    @GetMapping("/api/users/{uid}")
    public User findUserById(@PathVariable("uid") Long id) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                // Don't send password
                return u.safeCopy();
            }
        }
        // Didn't find
        return invalid;
    }

    // Find a user's followers by the user's ID
    @GetMapping("/api/users/{uid}/followers")
    public User[] findFollowers(@PathVariable("uid") Long id) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                // Don't send passwords
                User[] followers = new User[u.getFollowers().length];
                for (int i = 0; i < followers.length; i++) {
                    followers[i] = u.getFollowers()[i].safeCopy();
                }
                return followers;
            }
        }
        // Didn't find
        return null;
    }

    // Find a user's following by the user's ID
    @GetMapping("/api/users/{uid}/following")
    public User[] findFollowing(@PathVariable("uid") Long id) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                // Don't send passwords
                User[] following = new User[u.getFollowing().length];
                for (int i = 0; i < following.length; i++) {
                    following[i] = u.getFollowing()[i].safeCopy();
                }
                return following;
            }
        }
        // Didn't find
        return null;
    }

    // Find a user's favorites list by the user's ID
    @GetMapping("/api/users/{uid}/favorites")
    public String[] findFavorites(@PathVariable("uid") Long id) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                return u.getFavoriteIds();
            }
        }
        // Didn't find
        return null;
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
            // Don't send passwords
            newList[i] = temp.get(i).safeCopy();
        }
        users = newList;
        return newList;
    }

    // Update all users
    @PostMapping("/api/users")
    public User[] updateUsers(@RequestBody User[] allUsers) {
        // Block all passwords
        User[] ret = new User[users.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = users[i].safeCopy();
        }
        return ret;
    }

    // Update given user, return updated user - password never sent or modified
    @PostMapping("/api/users/{uid}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public User updateUser(@PathVariable("uid") Long id, @RequestBody User update) {
        for (int i = 0; i < users.length; i++) {
            User u = users[i];
            if (u.getId().equals(id)) {
                // Password stays on backend, untouched
                update.setPassword(u.getPassword());
                users[i] = update;
                // Don't send password to frontend
                return update.safeCopy();
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
        for (User u : users) {
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
        for (User u : users) {
            // Username match
            if (u.getUsername().equals(credentials[0])) {
                // Validate password
                if (u.getPassword().equals(credentials[1])) {
                    // Set a cookie for logged in user
                    session.setAttribute("userId", u.getId());
                    // Return a copy of the user, password field removed for security
                    return u.safeCopy();
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
