// Created by Andrew Stam
package com.example.myapp.controllers;

import com.example.myapp.models.User;
import com.example.myapp.models.RoleType;
import com.example.myapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    // An invalid User
    private User invalid = new User(null, null, null, RoleType.GroupMember);

    // Get data from the database
    @Autowired
    private UserRepository repository;

    // Find all users stored
    @GetMapping("/api/users")
    public List<User> findAllUsers() {
        // Block all passwords from being sent
        List<User> ret = new ArrayList<>();
        System.out.println("SIZE :: " + repository.findAllUsers().size());
        for (User u : repository.findAllUsers()) {
            ret.add(u.safeCopy());
        }
        return ret;
    }

    // Find newest user (largest ID)
    @GetMapping("/api/users/new")
    public User findNewestUser() {
        List<User> list = repository.findAllUsers();
        User newest = list.get(0);
        for (User u : list) {
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
        return repository.findUserById(id).safeCopy();
    }

    // Find a user's followers by the user's ID
    @GetMapping("/api/users/{uid}/followers")
    public List<User> findFollowers(@PathVariable("uid") Long id) {
        // Block all passwords from being sent
        List<User> ret = new ArrayList<>();
        for (Long fid : repository.findUserFollowers(id)) {
            ret.add(repository.findUserById(fid).safeCopy());
        }
        return ret;
    }

    // Add to a user's followers list by the user's ID
    @PostMapping("/api/users/{uid}/followers")
    public void addFollower(@PathVariable("uid") Long id, @RequestBody Long addId) {
        // If not a follower already
        if (!repository.findUserFollowers(id).contains(addId)) {
            repository.addUserFollower(id, addId);
        }
    }

    // Find a user's following by the user's ID
    @GetMapping("/api/users/{uid}/following")
    public List<User> findFollowing(@PathVariable("uid") Long id) {
        // Block all passwords from being sent
        List<User> ret = new ArrayList<>();
        for (Long fid : repository.findUserFollowing(id)) {
            ret.add(repository.findUserById(fid).safeCopy());
        }
        return ret;
    }

    // Add to a user's following list by the user's ID, return the new list
    @PostMapping("/api/users/{uid}/following")
    public void addFollowing(@PathVariable("uid") Long id, @RequestBody Long addId) {
        // If not following already
        if (!repository.findUserFollowing(id).contains(addId)) {
            repository.addUserFollowing(id, addId);
        }
    }

    // Find a user's favorites list by the user's ID
    @GetMapping("/api/users/{uid}/favorites")
    public List<String> findFavorites(@PathVariable("uid") Long id) {
        return repository.findUserFavorites(id);
    }

    // Add to a user's favorites list by the user's ID
    @PostMapping("/api/users/{uid}/favorites")
    public void addFavorite(@PathVariable("uid") Long id, @RequestBody String fav) {
        // Add to end of the list, never need double quotes
        if (fav.contains("\"")) {
            // Will always be at beginning and end
            fav = fav.substring(1, fav.length() - 1);
        }
        // Skip duplicates
        if (!repository.findUserFavorites(id).contains(fav)) {
            repository.addUserFavorite(id, fav);
        }
    }

    // Remove from a user's favorites list by the user's ID and the given favorite
    @DeleteMapping("/api/users/{uid}/favorites")
    public void removeFavorite(@PathVariable("uid") Long id, @RequestBody String fav) {
        // Remove any double quotes, always at beginning and end
        if (fav.contains("\"")) {
            // Will always be at beginning and end
            fav = fav.substring(1, fav.length() - 1);
        }
        repository.removeUserFavorite(id, fav);
    }

    // Delete a User by their ID
    @DeleteMapping("/api/users/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        repository.deleteById(userId);
    }

    // Update all users
    @PostMapping("/api/users")
    public void updateUsers(@RequestBody List<User> allUsers) {
        // Reset database to only have the given users
        for (User u : repository.findAllUsers()) {
            repository.deleteById(u.getId());
        }
        for (User u : allUsers) {
            repository.save(u);
        }
    }

    // Update given user, return updated user - password never sent or modified
    @PostMapping("/api/users/{uid}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public User updateUser(@PathVariable("uid") Long id, @RequestBody User update) {
        User u = repository.findUserById(id);
        u.set(update);
        return repository.save(u);
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
        for (User u : repository.findAllUsers()) {
            if (u.getUsername().equals(credentials[1])) {
                return false;
            }
        }
        // Just have id, user/pass, and role
        User create = new User(Long.parseLong(credentials[0]), credentials[1], credentials[2], role);
        repository.save(create);
        return true;
    }

    // Validate a login
    @PostMapping("/api/validate")
    public User validateUser(@RequestBody String[] credentials, HttpSession session) {
        User validate = repository.findUserByUsername(credentials[0]);
        // Validate password
        if (validate != null && validate.getPassword().equals(credentials[1])) {
            // Set a cookie for logged in user
            session.setAttribute("userId", validate.getId());
            // Return a copy of the user, password field removed for security
            return validate.safeCopy();
        } else {
            // Found no match, invalid credentials
            return invalid;
        }
    }

    // Retrieve session attribute
    @GetMapping("/api/session/get/{attr}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public String getSessionAttr(@PathVariable("attr") String attr, HttpSession session) {
        return (String) session.getAttribute(attr);
    }
}
