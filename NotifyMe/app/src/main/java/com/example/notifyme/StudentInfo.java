package com.example.notifyme;

import java.io.Serializable;

@SuppressWarnings("serial")


public class StudentInfo implements Serializable{
    private String username;
    private String email;
    private String branch;
    private String year;
    private String section;
    public StudentInfo(){

    }
    public StudentInfo(String mUsername,String mEmail,String mBranch,String mYear,String  mSection){
        username=mUsername;
        email=mEmail;
        branch=mBranch;
        year=mYear;
        section=mSection;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getBranch() {
        return branch;
    }

    public String getYear() {
        return year;
    }

    public String getSection() {
        return section;
    }
}

