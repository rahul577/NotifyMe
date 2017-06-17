package com.example.notifyme;

/**
 * Created by akshay on 8/6/17.
 */
import java.io.Serializable;

public class NotiFormat implements Serializable{
    private String sendername;
    private String title;
    private String content;
    private String branch;
    private String section;
    private String year;
    public NotiFormat(){
        sendername=null;

        title=null;
        content=null;
        branch=null;
        section=null;
        year=null;

    }

    public String getSendername() {
        return sendername;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }



    public String getBranch() {
        return branch;
    }

    public String getSection() {
        return section;
    }

    public String getYear() {
        return year;
    }

    public void setSendername(String sendername) {

        this.sendername = sendername;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
