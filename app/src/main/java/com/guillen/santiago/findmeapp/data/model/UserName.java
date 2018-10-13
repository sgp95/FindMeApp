package com.guillen.santiago.findmeapp.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;

import com.google.gson.annotations.SerializedName;

public class UserName {

    @ColumnInfo(name = "first_name")
    @SerializedName("first")
    private String first;
    @ColumnInfo(name = "last_name")
    @SerializedName("last")
    private String last;

    @Ignore
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
