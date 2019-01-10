package com.ask.vitevents.Classes;

/**
 * Created by suraj on 6/8/18.
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "event_table")
public class Event {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "event")
    private String idevent;


    private String eventname, eventdesc,
            eventtime, eventdate, venue, fee,
            schoolid, clubid, istrending, status,
            minteamsize, maxteamsize, eventtype, posterurl;


    public Event()
    {
        idevent =null;
        eventname = null; eventdesc = null;
        eventtime = null; eventdate = null; venue = null; fee = null;
        schoolid = null; clubid = null; istrending = null; status = null;
        minteamsize = null; maxteamsize = null; eventtype = null; posterurl = null;
    }

    public Event(@NonNull String idevent, String eventname,
                String eventdesc, String eventtime,
                String eventdate, String venue, String fee,
                String schoolid, String clubid, String istrending,
                String status, String minteamsize,
                String maxteamsize, String eventtype, String posterurl)
    {
        this.idevent = idevent;
        this.eventname = eventname;
        this.eventdesc = eventdesc;
        this.eventtime = eventtime;
        this.eventdate = eventdate;
        this.venue = venue;
        this.fee = fee;
        this.schoolid = schoolid;
        this.clubid = clubid;
        this.istrending = istrending;
        this.status = status;
        this.minteamsize = minteamsize;
        this.maxteamsize = maxteamsize;
        this.eventtype = eventtype;
        this.posterurl = posterurl;
    }

    @NonNull
    public String getIdevent() {
        return idevent;
    }

    public void setIdevent(@NonNull String idevent) {
        this.idevent = idevent;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getEventdesc() {
        return eventdesc;
    }

    public void setEventdesc(String eventdesc) {
        this.eventdesc = eventdesc;
    }

    public String getEventtime() {
        return eventtime;
    }

    public void setEventtime(String eventtime) {
        this.eventtime = eventtime;
    }

    public String getEventdate() {
        return eventdate;
    }

    public void setEventdate(String eventdate) {
        this.eventdate = eventdate;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public String getClubid() {
        return clubid;
    }

    public void setClubid(String clubid) {
        this.clubid = clubid;
    }

    public String getIstrending() {
        return istrending;
    }

    public void setIstrending(String istrending) {
        this.istrending = istrending;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMinteamsize() {
        return minteamsize;
    }

    public void setMinteamsize(String minteamsize) {
        this.minteamsize = minteamsize;
    }

    public String getMaxteamsize() {
        return maxteamsize;
    }

    public void setMaxteamsize(String maxteamsize) {
        this.maxteamsize = maxteamsize;
    }

    public String getEventtype() {
        return eventtype;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    public String getPosterurl() {
        return posterurl;
    }

    public void setPosterurl(String posterurl) {
        this.posterurl = posterurl;
    }
}