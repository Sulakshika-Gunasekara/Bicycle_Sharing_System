package dev.mybike.mybike.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "trips")
public class Trip {
    @Id
    private String id;
    private String riderId;
    private String bikeId;
    private String startStationId;
    private String endStationId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double tripCost;
    private boolean isCompleted;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setTripCost(double tripCost) {
        this.tripCost = tripCost;
    }

    public void setEndStationId(String dockingStationId) {
        this.endStationId = dockingStationId;
    }

    public void setCompleted(boolean b) {
        this.isCompleted = b;
    }

    public void setEndTime(LocalDateTime now) {
        this.endTime = now;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public void setStartStationId(String stationId) {
        this.startStationId = stationId;
    }

    public void setStartTime(LocalDateTime now) {
        this.startTime = now;
    }
}