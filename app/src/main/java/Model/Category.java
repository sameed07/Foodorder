package com.example.sameedshah.foodorderserver.Model;

public class Category {

    private String Name;
    private String image;

    public Category() {
    }

    public Category(String Name, String image) {
        this.Name = Name;
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
