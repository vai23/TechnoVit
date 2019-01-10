package com.ask.vitevents.Classes;

public class AddedTeamClass {
    String name, id, size;
    public AddedTeamClass(String id, String name, String size) {
        this.name = name;
        this.id = id;
        this.size = size;
    }

    public AddedTeamClass() {
        this.name = null;
        this.id = null;
        this.size = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return id;
    }

    public void setID(String front) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}

