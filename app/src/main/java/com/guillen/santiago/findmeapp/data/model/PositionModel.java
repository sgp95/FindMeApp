package com.guillen.santiago.findmeapp.data.model;

public class PositionModel {

    private String beaconId;
    private String roomName;
    private Double distance;
    private String floorLevel;

    public PositionModel() {
    }

    public PositionModel(String beaconId, String roomName, Double distance, String floorLevel) {
        this.beaconId = beaconId;
        this.roomName = roomName;
        this.distance = distance;
        this.floorLevel = floorLevel;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(String beaconId) {
        this.beaconId = beaconId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Double getDisntance() {
        return distance;
    }

    public void setDisntance(Double distance) {
        this.distance = distance;
    }

    public String getFloorLevel() {
        return floorLevel;
    }

    public void setFloorLevel(String floorLevel) {
        this.floorLevel = floorLevel;
    }
}
