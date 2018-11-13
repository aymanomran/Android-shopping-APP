package com.example.ayman.raye7challenge;
/**
 * Created by AYMAN on 20/07/2018.
 */
public class Users {
    String name,image,longitude,latitude;

    public Users(String name, String image, String longitude, String latitude) {
        this.name = name;
        this.image = image;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
