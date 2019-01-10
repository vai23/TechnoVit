package com.ask.vitevents.Classes;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "tshirt_table")
public class Tshirt {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name= "idtshirt")
    private String idtshirt;

    private String tshirtname, tshirtfront, tshirtback, tshirtside, tshirtdesc, tshirtprice;

    public Tshirt() {
        idtshirt = null;
        tshirtname = null;
        tshirtfront = null;
        tshirtback = null;
        tshirtside = null;
        tshirtdesc = null;
        tshirtprice =null;
    }

    public Tshirt(@NonNull String idtshirt, String tshirtname,
                  String tshirtfront, String tshirtback,
                  String tshirtside, String tshirtdesc, String tshirtprice) {
        this.idtshirt = idtshirt;
        this.tshirtname = tshirtname;
        this.tshirtfront = tshirtfront;
        this.tshirtback = tshirtback;
        this.tshirtside = tshirtside;
        this.tshirtdesc = tshirtdesc;
        this.tshirtprice = tshirtprice;
    }

    @NonNull
    public String getIdtshirt() {
        return idtshirt;
    }

    public void setIdtshirt(@NonNull String idtshirt) {
        this.idtshirt = idtshirt;
    }

    public String getTshirtname() {
        return tshirtname;
    }

    public void setTshirtname(String tshirtname) {
        this.tshirtname = tshirtname;
    }

    public String getTshirtfront() {
        return tshirtfront;
    }

    public void setTshirtfront(String tshirtfront) {
        this.tshirtfront = tshirtfront;
    }

    public String getTshirtback() {
        return tshirtback;
    }

    public void setTshirtback(String tshirtback) {
        this.tshirtback = tshirtback;
    }

    public String getTshirtside() {
        return tshirtside;
    }

    public void setTshirtside(String tshirtside) {
        this.tshirtside = tshirtside;
    }

    public String getTshirtdesc() {
        return tshirtdesc;
    }

    public void setTshirtdesc(String tshirtdesc) {
        this.tshirtdesc = tshirtdesc;
    }

    public String getTshirtprice() {
        return tshirtprice;
    }

    public void setTshirtprice(String tshirtprice) {
        this.tshirtprice = tshirtprice;
    }
}
