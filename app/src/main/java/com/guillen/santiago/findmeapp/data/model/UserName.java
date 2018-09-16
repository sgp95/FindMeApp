package com.guillen.santiago.findmeapp.data.model;

import com.google.gson.annotations.SerializedName;

public class UserName {

    @SerializedName("first")
    private String firstName;
    @SerializedName("last")
    private String lastName;

    public UserName() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
