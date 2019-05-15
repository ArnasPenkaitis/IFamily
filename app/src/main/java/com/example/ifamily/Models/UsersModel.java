package com.example.ifamily.Models;

public class UsersModel {

    public String name,familyId,image;
    public Double lattitude,longtitude;

    public UsersModel(){}

    public UsersModel(String name, String familyId, String image, Double lattitude, Double longtitude) {
        this.name = name;
        this.familyId = familyId;
        this.image = image;
        this.lattitude = lattitude;
        this.longtitude = longtitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public void setLattitude(Double lattitude) {
        this.lattitude = lattitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }
}
