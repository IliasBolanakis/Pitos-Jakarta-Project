package com.ilbolan.pitoswebproject.models.beans;

import java.io.Serializable;

/**
 * Pie bean needed for reading from Database
 */
public class Pie implements Serializable {
    private int id;
    private String name;
    private double price;
    private String fileName;
    private String ingredients;

    public Pie() {}

    public Pie(int id, String name, double price, String fileName, String ingredients) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.fileName = fileName;
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }


}
