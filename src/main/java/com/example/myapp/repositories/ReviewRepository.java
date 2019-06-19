// Created by Andrew Stam
package com.example.myapp.repositories;

import java.util.List;

import com.example.myapp.models.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    // Use JPA to find all reviews
    @Query(value = "select * from review", nativeQuery = true)
    public List<Review> findAllReviews();

    // Use JPA to find a review by its id, return that review
    @Query(value = "select * from review where review_id=:id", nativeQuery = true)
    public Review findReviewById(@Param("id") Long id);

    // Use JPA to find all reviews of a movie by its id, return those reviews (text only)
    @Query(value = "select review_text from review where movie_id=:id", nativeQuery = true)
    public List<String> findWrittenReviewsByMovieId(@Param("id") String id);

    // Use JPA to find all reviews of a movie by its id, return those reviews (stars only)
    @Query(value = "select stars from review where movie_id=:id", nativeQuery = true)
    public List<Integer> findStarReviewsByMovieId(@Param("id") String id);

    // Use JPA to find all reviews of a user by their id, return those reviews (text only)
    @Query(value = "select review_text from review where user_id=:id", nativeQuery = true)
    public List<String> findWrittenReviewsByUserId(@Param("id") Long id);

    // Use JPA to find all reviews of a user by their id, return those reviews (stars only)
    @Query(value = "select stars from review where user_id=:id", nativeQuery = true)
    public List<Integer> findStarReviewsByUserId(@Param("id") Long id);

    // Use JPA to find all reviews of a user by their id, return those reviews (ids only)
    @Query(value = "select id from review where user_id=:id", nativeQuery = true)
    public List<Long> findReviewIdsByUserId(@Param("id") Long id);

    // Use JPA to find the review of a movie by a user by their ids, return that review (text only)
    @Query(value = "select review_text from review where user_id=:uid and movie_id=:mid", nativeQuery = true)
    public String findWrittenReviewForMovieByUserId(@Param("uid") Long uid, @Param("mid") String mid);

    // Use JPA to find the list of movies a user has written reviews for
    @Query(value = "select movie_id from review where user_id=:uid", nativeQuery = true)
    public List<String> findUserMovieReivews(@Param("uid") Long uid);

    // Use JPA to add a review of a movie by a user by their ids, storing the text and star rating
    @Modifying
    @Transactional
    @Query(value = "insert into review (user_id, movie_id, review_text, stars) values (:uid, :mid, :txt, :stz)", nativeQuery = true)
    public void createReview(@Param("uid") Long uid, @Param("mid") String mid, @Param("txt") String text, @Param("stz") Integer stars);

    // Use JPA to delete a review of a movie by a user by their ids
    @Modifying
    @Transactional
    @Query(value = "delete from review where user_id=:uid and mid=:mid", nativeQuery = true)
    public void deleteReview(@Param("uid") Long uid, @Param("mid") String mid);
}
