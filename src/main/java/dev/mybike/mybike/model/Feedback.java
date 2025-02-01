package dev.mybike.mybike.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "feedback")
public class Feedback {

    @Id
    private String id;

    private String feedback;
    private int rating;
    private String date;

}
