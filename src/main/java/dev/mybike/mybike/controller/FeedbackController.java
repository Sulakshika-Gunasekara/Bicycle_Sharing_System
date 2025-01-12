package dev.mybike.mybike.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mybike.mybike.model.Feedback;
import dev.mybike.mybike.service.FeedbackService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("api/feedback")
@ControllerAdvice
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/addFeedback")
    public ResponseEntity<Feedback> addFeedback(@RequestBody Feedback feedback) {
        feedbackService.addFeedback(feedback.getFeedback(), feedback.getRating(),
                feedback.getDate());
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/{feedbackId}")
    public ResponseEntity<Feedback> getBikeById(@PathVariable String feedbackId) {
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
        List<Feedback> feedbacks = (List<Feedback>) feedbackService.getFeedbackByRating(rating);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Feedback>> getFeedbackByDate(@PathVariable String date) {
        List<Feedback> feedbacks = (List<Feedback>) feedbackService.getFeedbackByDate(date);
        return ResponseEntity.ok(feedbacks);
    }

}
