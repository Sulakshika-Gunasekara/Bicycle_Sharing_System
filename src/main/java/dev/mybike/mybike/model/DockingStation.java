package dev.mybike.mybike.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "docking_station")
public class DockingStation {

    @Id
    private String id;
    private String stationId;
    private String name;
    private double latitude;
    private double longitude;
    private int availableBikes;
    private int emptyDocks;
    private boolean isActive;

}
