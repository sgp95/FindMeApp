package com.guillen.santiago.findmeapp.data.model;

import com.google.gson.annotations.SerializedName;

public class UserName {

    @SerializedName("first")
    private String first;
    @SerializedName("last")
    private String last;

    public UserName() {
    }

    public UserName(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
