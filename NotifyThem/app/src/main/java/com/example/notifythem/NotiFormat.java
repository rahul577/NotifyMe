package com.example.notifythem;

/**
 * Created by akshay on 8/6/17.
 */
import com.example.notifythem.data.NotifyContract;

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

    public void setBranchThroughInt(int branch) {

        if (branch == NotifyContract.NotifyEntry.BRANCH_COMPUTER_SCIENCE)
            this.branch = "Computer Science";
        if (branch == NotifyContract.NotifyEntry.BRANCH_INFORMATION_TECHNOLOGY)
            this.branch = "Information Technology";
        if (branch == NotifyContract.NotifyEntry.BRANCH_MECHANICAL)
            this.branch = "Mechanical";
        if (branch == NotifyContract.NotifyEntry.BRANCH_ELECTRICAL)
            this.branch = "Electrical and Electronics";
        if (branch == NotifyContract.NotifyEntry.BRANCH_ELECTRONICS)
            this.branch = "Electronics and Communication";
        if (branch == NotifyContract.NotifyEntry.BRANCH_BIOTECHNOLOGY)
            this.branch = "BioTechnology";
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setSectionThroughInt(int section){
        if(section== NotifyContract.NotifyEntry.SECTION_1)
            this.section="Section 1";
        if(section== NotifyContract.NotifyEntry.SECTION_2)
            this.section="Section 2";


    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setYearThroughInt(int year){
        if(year== NotifyContract.NotifyEntry.YEAR_1)
            this.year="First Year";
        if(year== NotifyContract.NotifyEntry.YEAR_2)
            this.year="Second Year";
        if(year== NotifyContract.NotifyEntry.YEAR_3)
            this.year="Third Year";
        if(year== NotifyContract.NotifyEntry.YEAR_4)
            this.year="Fourth Year";

    }
}
