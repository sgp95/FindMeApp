package com.guillen.santiago.findmeapp.data.model;

public class BeaconModel {
    private String id;
    private String roomNumber;
    private String roomName;
    private String floorLevel;
    private String description;
    private Double patientDistance;

    public BeaconModel() {
        patientDistance = 0.0;
    }

    public BeaconModel(String id, String roomNumber, String roomName, String floorLevel, String description, Double distance) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomName = roomName;
        this.floorLevel = floorLevel;
        this.description = description;
        this.patientDistance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getFloorLevel() {
        return floorLevel;
    }

    public void setFloorLevel(String floorLevel) {
        this.floorLevel = floorLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPatientDistance() {
        return patientDistance;
    }

    public void setPatientDistance(Double patientDistance) {
        this.patientDistance = patientDistance;
    }
}
