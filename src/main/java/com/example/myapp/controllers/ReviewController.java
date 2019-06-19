// Created by Andrew Stam
package com.example.myapp.controllers;

import com.example.myapp.models.Review;
import com.example.myapp.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
