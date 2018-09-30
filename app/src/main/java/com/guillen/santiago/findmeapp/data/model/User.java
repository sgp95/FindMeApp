package com.guillen.santiago.findmeapp.data.model;

public class User {
    private String email;
    private String imageUrl;
    private String type;
    private String contactPhone;
    private UserName name;

    public User() {
    }

    public User(String email, String imageUrl, String type, String contactPhone, UserName name) {
        this.email = email;
        this.imageUrl = imageUrl;
        this.type = type;
        this.contactPhone = contactPhone;
        this.name = name;
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
