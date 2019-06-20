// Created by Andrew Stam
package com.example.myapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinTable(name = "user",
            joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")}
    )
    private Long userId;

    private String text;

    @JsonIgnore
    @ManyToOne
    @JoinTable(name = "watchitem",
            joinColumns={@JoinColumn(name="watch_item", referencedColumnName="id")}
    )
    private WatchItem watchItem;

    public Comment(Long id, Long userId, String text) {
        this.id = id;
        this.userId = userId;
        this.text = text;
    }

    public Comment() {
        this.id = null;
        this.userId = null;
        this.text = null;
    }

    public Long getCommentId() {
        return id;
    }

    public void setCommentId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
