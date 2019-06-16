// Created by Andrew Stam
package com.example.myapp.models;

public class User {
    private Long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String dob;
    // Either GroupLeader or GroupMember
    private RoleType role;
    private String email;

    private User[] following;
    private User[] followers;
    private String[] favoriteIds;

    // Create a new User with the given attributes
    public User(Long id, String username, String password, String firstname, String lastname,
                String dob, RoleType role, String email, User[] following, User[] followers, String[] favoriteIds) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.role = role;
        this.email = email;
        this.following = following;
        this.followers = followers;
        this.favoriteIds = favoriteIds;
    }

    // Create a new User with basic info
    public User(Long id, String username, String password, RoleType role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = "";
        this.lastname = "";
        this.dob = "";
        this.role = role;
        this.email = "";
        this.following = new User[0];
        this.followers = new User[0];
        this.favoriteIds = new String[0];
    }

    // Create a new basic User, used for POST requests
    public User() {
        this.id = null;
        this.username = null;
        this.password = null;
        this.firstname = null;
        this.lastname = null;
        this.dob = null;
        this.role = null;
        this.email = null;
        this.following = new User[0];
        this.followers = new User[0];
        this.favoriteIds = new String[0];
    }

    // Return a copy of this object with password hidden for security
    public User safeCopy() {
        return new User(id, username, "HIDDEN", firstname, lastname, dob, role, email, following, followers, favoriteIds);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User[] getFollowing() {
        return following;
    }

    public void setFollowing(User[] following) {
        this.following = following;
    }

    public User[] getFollowers() {
        return followers;
    }

    public void setFollowers(User[] followers) {
        this.followers = followers;
    }

    public String[] getFavoriteIds() {
        return favoriteIds;
    }

    public void setFavoriteIds(String[] favoriteIds) {
        this.favoriteIds = favoriteIds;
    }
}
