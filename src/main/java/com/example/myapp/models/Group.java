// Created by Andrew Stam
package com.example.myapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "movie_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinTable(name = "user",
            joinColumns={@JoinColumn(name="leader_id", referencedColumnName="id")}
    )
    private Long leaderId;

//    Has ManyToMany relationship, stored in User.java
//    @JsonIgnore
//    @ManyToMany
//    @ElementCollection
//    private List<User> members;

//    A WatchItem keeps track of its Group
//    @JsonIgnore
//    @OneToMany
//    @ElementCollection
//    private List<WatchItem> watchItems;

    public Group(Long id, Long leaderId) {
        this.id = id;
        this.leaderId = leaderId;
    }

    public Group() {
        this.id = null;
        this.leaderId = null;
    }

    public Long getGroupId() {
        return id;
    }

    public void setGroupId(Long id) {
        this.id = id;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }
}
