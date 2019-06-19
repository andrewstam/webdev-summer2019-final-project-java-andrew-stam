// Created by Andrew Stam
package com.example.myapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @JoinTable(name = "user",
            joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")}
    )
    private Long userId;

    private String movieId;

    // Star rating, 1 to 5
    private Integer stars;

    @JsonIgnore
    private String reviewText;

    public Review() {
        this.reviewId = null;
        this.userId = null;
        this.movieId = null;
        this.reviewText = null;
        this.stars = null;
    }

    public Review(Long reviewId, Long userId, String movieId, String reviewText, Integer stars) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.movieId = movieId;
        this.reviewText = reviewText;
        this.stars = stars;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }
}
