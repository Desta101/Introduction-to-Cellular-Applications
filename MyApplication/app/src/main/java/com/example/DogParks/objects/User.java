package com.example.DogParks.objects;

public class User {
    private  String uId;
    private  String firstName;
    private  String lastName;
    private  String picture;

    public User() {
    }

    public User(String uId, String firstName, String lastName, String picture) {
        this.uId = uId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
    }

    public String getuId() {
        return uId;
    }

    public User setuId(String uId) {
        this.uId = uId;
        return this;
    }
    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public User setPicture(String picture) {
        this.picture = picture;
        return this;
    }

}
