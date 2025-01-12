package dev.mybike.mybike.service;

import java.util.List;

import dev.mybike.mybike.model.Feedback;

public interface FeedbackService {

    Feedback addFeedback(String feedback, int rating, String date);

    Feedback getFeedbackById(String feedbackId);

    List<Feedback> getAllFeedbacks();

    List<Feedback> getFeedbackByRating(int rating);

    List<Feedback> getFeedbackByDate(String date);

    List<Feedback> getFeedbackByRatingAndDate(int rating, String date);

}
