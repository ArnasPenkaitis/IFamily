package com.example.ifamily.Models;

public class NotesModel {

    public String authorid,familyid,text,authorname,noteid;
    public Boolean type,isdone;

    public NotesModel() {

    }

    public NotesModel(String authorid, String familyid, String text,Boolean type, String authorname,Boolean isdone) {
        this.authorid = authorid;
        this.familyid = familyid;
        this.text = text;
        this.type= type;
        this.authorname = authorname;
        this.isdone = isdone;
        this.noteid= "";
    }

    public String getNoteid() {
        return noteid;
    }

    public void setNoteid(String noteid) {
        this.noteid = noteid;
    }

    public Boolean getIsdone() {
        return isdone;
    }

    public void setIsdone(Boolean isdone) {
        this.isdone = isdone;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
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

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }
}
