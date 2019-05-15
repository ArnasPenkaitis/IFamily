package com.example.ifamily.Models;

public class EventsModel {
    public String familyid,text,date;

    public EventsModel(){

    }

    public EventsModel(String familyid, String text, String date) {
        this.familyid = familyid;
        this.text = text;
        this.date = date;
    }

    public String getFamilyid() {
        return familyid;
    }

    public void setFamilyid(String familyid) {
        this.familyid = familyid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
