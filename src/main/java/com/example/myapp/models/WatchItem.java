// Created by Andrew Stam
package com.example.myapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "watchitem")
public class WatchItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinTable(name = "movie_group",
            joinColumns={@JoinColumn(name="group_id", referencedColumnName="id")}
    )
    private Long groupId;

    private String movieId;

    private String watchDate;

//    A Comment keeps track of the WatchItem it belongs to
//    @JsonIgnore
//    @OneToMany
//    @ElementCollection
//    private List<Comment> comments;

    @JsonIgnore
    @ElementCollection
    private List<Long> attendingMemberIds;

    public WatchItem(Long id, Long groupId, String movieId, String watchDate, List<Long> attendingMemberIds) {
        this.id = id;
        this.groupId = groupId;
        this.movieId = movieId;
        this.watchDate = watchDate;
        this.attendingMemberIds = attendingMemberIds;
    }

    public WatchItem() {
        this.id = null;
        this.groupId = null;
        this.movieId = null;
        this.watchDate = null;
        this.attendingMemberIds = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(String watchDate) {
        this.watchDate = watchDate;
    }

    public List<Long> getAttendingMemberIds() {
        return attendingMemberIds;
    }

    public void setAttendingMemberIds(List<Long> attendingMemberIds) {
        this.attendingMemberIds = attendingMemberIds;
    }
}
