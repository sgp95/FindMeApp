package com.guillen.santiago.findmeapp.view.model;

import org.json.JSONObject;

public class BeaconAttachment {
    //{   "room": "0102",   "type": "laboratory" }
    private String room;
    private String type;

    public BeaconAttachment() {
    }

    public static BeaconAttachment fromJson(JSONObject jsonObject){
        BeaconAttachment attachment = new BeaconAttachment();
        try {
            attachment.setRoom(jsonObject.getString("room"));
            attachment.setType(jsonObject.getString("type"));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return attachment;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
