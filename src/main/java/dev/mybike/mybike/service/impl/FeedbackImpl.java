package dev.mybike.mybike.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.mybike.mybike.model.Feedback;
import dev.mybike.mybike.repository.FeedbackRepository;
import dev.mybike.mybike.service.FeedbackService;

@Service
public class FeedbackImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public Feedback addFeedback(String customerId, String feedback, int rating, String date) {
        Feedback feedbackObj = new Feedback();
        feedbackObj.setCustomerId(customerId);
        feedbackObj.setFeedback(feedback);
        feedbackObj.setRating(rating);
        feedbackObj.setDate(date);
        feedbackRepository.save(feedbackObj);
        return feedbackObj;
    }

    @Override
    public Feedback getFeedbackById(String feedbackId) {
        return feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new IllegalArgumentException("Feedback not found."));
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Override
    public List<Feedback> getFeedbackByRating(int rating) {
        return feedbackRepository.findByRating(rating);
    }

    @Override
    public List<Feedback> getFeedbackByDate(String date) {
        return feedbackRepository.findByDate(date);
    }

    @Override
    public List<Feedback> getFeedbackByRatingAndDate(int rating, String date) {
        return feedbackRepository.findByRatingAndDate(rating, date);
    }

}
