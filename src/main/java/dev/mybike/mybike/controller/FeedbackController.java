package dev.mybike.mybike.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mybike.mybike.model.Feedback;
import dev.mybike.mybike.service.FeedbackService;

@RestController
@RequestMapping("api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/create")
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
        // Debugging log
        System.out.println("Received Feedback: " + feedback);

        // Save feedback
        Feedback savedFeedback = feedbackService.creatFeedback(feedback);
        
        return ResponseEntity.ok(savedFeedback);
    }

    @GetMapping("/{feedbackId}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable String feedbackId) {
        Feedback feedback = feedbackService.getFeedbackById(feedbackId);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<Feedback>> getFeedbackByRating(@PathVariable int rating) {
        List<Feedback> feedbacks = feedbackService.getFeedbackByRating(rating);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Feedback>> getFeedbackByDate(@PathVariable String date) {
        List<Feedback> feedbacks = feedbackService.getFeedbackByDate(date);
        return ResponseEntity.ok(feedbacks);
    }
}