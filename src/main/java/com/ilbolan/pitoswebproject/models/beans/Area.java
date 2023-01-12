package com.ilbolan.pitoswebproject.models.beans;

/**
 * Area bean needed for reading from Database
 */
public class Area {

    private int id;
    private String description;
    public Area(){}

    Area(int id, String descr){
        this.id = id;
        this.description = descr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descr) {
        this.description = descr;
    }
}
