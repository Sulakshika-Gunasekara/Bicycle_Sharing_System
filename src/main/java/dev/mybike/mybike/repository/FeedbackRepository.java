package dev.mybike.mybike.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import dev.mybike.mybike.model.Feedback;

public interface FeedbackRepository extends MongoRepository<Feedback, String> {

    List<Feedback> findByRating(int rating);

    List<Feedback> findByDate(String date);

    List<Feedback> findByRatingAndDate(int rating, String date);

}
