// Created by Andrew Stam
package com.example.myapp.models;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @JoinTable(name = "user",
            joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")}
    )
    private Long userId;

    private String text;

    public Comment(Long commentId, Long userId, String text) {
        this.commentId = commentId;
        this.userId = userId;
        this.text = text;
    }

    public Comment() {
        this.commentId = null;
        this.userId = null;
        this.text = null;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
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
