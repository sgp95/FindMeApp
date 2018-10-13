package com.guillen.santiago.findmeapp.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "current_user")
public class User {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "image_url")
    private String imageUrl;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "contact_phone")
    private String contactPhone;
    @Embedded
    private UserName name;

    @Ignore
    public User() {
    }

    public User(String email, String imageUrl, String type, String contactPhone, UserName name) {
        this.email = email;
        this.imageUrl = imageUrl;
        this.type = type;
        this.contactPhone = contactPhone;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public UserName getName() {
        return name;
    }

    public void setName(UserName name) {
        this.name = name;
    }
}
