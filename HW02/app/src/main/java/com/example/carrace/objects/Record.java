package com.example.carrace.objects;
/**
 * shimon Desta 203670286
 * HW01 - Car Game
 **/
public class Record {

    private String name;
    private int score;
    private double latitude = 0.0;
    private double longitude= 0.0;

    public Record() {
        latitude =32.08326705623331;
        longitude=34.82129553531872;
    }

    public String getName() {
        return name;
    }

    public Record setName(String name) {
        this.name = name;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Record setScore(int score) {
        this.score = score;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public Record setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Record setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }
}