package com.ask.vitevents.Classes;

public class EventsDetails {

    private String eventname,school,desc;
    private int Thumbnail;

    public EventsDetails() {
    }

    public EventsDetails(String eventname, String school, String desc, int thumbnail) {
        this.eventname = eventname;
        this.school = school;
        this.desc = desc;
        Thumbnail = thumbnail;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
