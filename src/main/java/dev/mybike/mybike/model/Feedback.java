package dev.mybike.mybike.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "feedback")
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    private String id;

    private String feedback;
    private int rating;
    private String date;

}
