// Created by Andrew Stam
package com.example.myapp.controllers;

import com.example.myapp.models.Review;
import com.example.myapp.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ReviewController {
    // Get data from the database
    @Autowired
    private ReviewRepository repository;

    // Find all reviews stored
    @GetMapping("/api/reviews")
    public List<Review> findAllReviews() {
        return repository.findAllReviews();
    }

    // Find a review by its unique ID
    @GetMapping("/api/reviews/{id}")
    public Review findReviewById(@PathVariable("id") Long id) {
        return repository.findReviewById(id);
    }

    // Find all reviews of a movie by its id, return those reviews (text only)
    @GetMapping("/api/reviews/{id}/movie/text")
    public List<String> findWrittenReviewsByMovieId(@PathVariable("id") String id) {
        return repository.findWrittenReviewsByMovieId(id);
    }

    // Find all reviews of a movie by its id, return those reviews (stars only)
    @GetMapping("/api/reviews/{id}/movie/stars")
    public List<Integer> findStarReviewsByMovieId(@PathVariable("id") String id) {
        return repository.findStarReviewsByMovieId(id);
    }

    // Find all reviews of a user by their id, return those reviews (text only)
    @GetMapping("/api/reviews/{id}/user/text")
    public List<String> findWrittenReviewsByUserId(@PathVariable("id") Long id) {
        return repository.findWrittenReviewsByUserId(id);
    }

    // Find all reviews of a user by their id, return those reviews (stars only)
    @GetMapping("/api/reviews/{id}/user/stars")
    public List<Integer> findStarReviewsByUserId(@PathVariable("id") Long id) {
        return repository.findStarReviewsByUserId(id);
    }

    // Find average star rating of a user by their id
    @GetMapping("/api/reviews/{id}/avg")
    public Double findStarAverageByUserId(@PathVariable("id") Long id) {
        List<Integer> ratings = repository.findStarReviewsByUserId(id);
        double avg = 0;
        // No ratings, no average
        if (ratings.size() == 0) {
            return 0.;
        }
        for (Integer r : ratings) {
            avg += r;
        }
        avg /= ratings.size();
        return avg;
    }

    // Find all reviews of a user by their id, return those reviews (ids only)
    @GetMapping("/api/reviews/{id}/user/ids")
    public List<Long> findReviewIdsByUserId(@PathVariable("id") Long id) {
        return repository.findReviewIdsByUserId(id);
    }

    // Find the review of a movie by a user by their ids, return that review (text only)
    @GetMapping("/api/reviews/{mid}/{uid}/text")
    public String findWrittenReviewForMovieByUserId(@PathVariable("mid") String mid, @PathVariable("uid") Long uid) {
        // If this user has a review for the movie
        if (repository.findUserMovieReivews(uid).contains(mid)) {
            return repository.findWrittenReviewForMovieByUserId(uid, mid);
        } else {
            return "";
        }
    }

    // Find the review of a movie by a user by their ids, return that review (text only)
    @GetMapping("/api/reviews/{mid}/{uid}/stars")
    public Integer findStarsForMovieByUserId(@PathVariable("mid") String mid, @PathVariable("uid") Long uid) {
        // If this user has a review for the movie already
        if (repository.findUserMovieReivews(uid).contains(mid)) {
            return repository.findStarsForMovieByUserId(uid, mid);
        } else {
            // default
            return 1;
        }
    }

    // Add a review of a movie by a user by their ids, storing the text and star rating. Returns if added
    @PostMapping("/api/reviews/{mid}/{uid}/{stars}")
    public boolean createReview(@PathVariable("uid") Long uid, @PathVariable("mid") String mid, @RequestBody String text, @PathVariable("stars") Integer stars) {
        // If this user doesn't have a review for the movie already
        if (!repository.findUserMovieReivews(uid).contains(mid)) {
            repository.createReview(uid, mid, text, stars);
            return true;
        }
        return false;
    }

    // Update a star rating of a movie by a user by their ids
    @PutMapping("/api/reviews/{mid}/{uid}/stars")
    public void updateStars(@PathVariable("uid") Long uid, @PathVariable("mid") String mid, @RequestBody Integer stars) {
        // If this user has a review for the movie already
        if (repository.findUserMovieReivews(uid).contains(mid)) {
            repository.updateStars(uid, mid, stars);
        } else {
            // Create review, one doesn't exist
            repository.createReview(uid, mid, null, stars);
        }
    }

    // Update a star rating of a movie by a user by their ids
    @PutMapping("/api/reviews/{mid}/{uid}/text")
    public void updateText(@PathVariable("uid") Long uid, @PathVariable("mid") String mid, @RequestBody String text) {
        // If this user has a review for the movie already
        if (repository.findUserMovieReivews(uid).contains(mid)) {
            repository.updateText(uid, mid, text);
        } else {
            // Create review, one doesn't exist
            repository.createReview(uid, mid, text, 0);
        }
    }

    // Delete a review of a movie by a user by their ids, return if was deleted successfully
    @DeleteMapping("/api/reviews/{mid}/{uid}")
    public boolean deleteReview(@PathVariable("uid") Long uid, @PathVariable("mid") String mid) {
        // If this user does have a review for the movie already
        if (repository.findUserMovieReivews(uid).contains(mid)) {
            repository.deleteReview(uid, mid);
            return true;
        }
        return false;
    }
}
