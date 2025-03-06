package dev.mybike.mybike.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.mybike.mybike.model.Feedback;
import dev.mybike.mybike.repository.FeedbackRepository;
import dev.mybike.mybike.service.FeedbackService;

@Service
public class FeedbackImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper
    
    @Override
    public Feedback creatFeedback(Feedback feedback) {
        try {
            // Convert incoming request to JSON and print it
            String jsonFeedback = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(feedback);
            System.out.println("Received Feedback: " + jsonFeedback);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedbackRepository.save(feedback);
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
