// Created by Andrew Stam
package com.example.myapp.models;

public class User {
    private Integer id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String dob;
    // Either GroupLeader or GroupMember
    private String role;
    private String email;

    // Create a new User
    public User(Integer id, String username, String password, String firstname, String lastname, String dob, String role, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.role = role;
        this.email = email;
    }

    // Create a new User with basic info
    public User(Integer id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = "";
        this.lastname = "";
        this.dob = "";
        this.role = role;
        this.email = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
