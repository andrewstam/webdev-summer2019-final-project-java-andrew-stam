// Created by Andrew Stam
package com.example.myapp.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "watchitem")
public class WatchItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private String movieId;

    private String watchDate;

    private List<Comment> comments;

    private List<Long> attendingMemberIds;

    public WatchItem(Long itemId, String movieId, Strin gwatchDate, List<Comment> comments, List<Long> attendingMemberIds) {
        this.itemId = itemId;
        this.movieId = movieId;
        this.watchDate = watchDate;
        this.comments = comments;
        this.attendingMemberIds = attendingMemberIds;
    }

    public WatchItem() {
        this.itemId = null;
        this.movieId = null;
        this.watchDate = null;
        this.comments = null;
        this.attendingMemberIds = null;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Long> getAttendingMemberIds() {
        return attendingMemberIds;
    }

    public void setAttendingMemberIds(List<Long> attendingMemberIds) {
        this.attendingMemberIds = attendingMemberIds;
    }
}
