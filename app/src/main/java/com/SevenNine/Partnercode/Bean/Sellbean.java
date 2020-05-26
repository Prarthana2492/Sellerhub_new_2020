package com.SevenNine.Partnercode.Bean;

public class Sellbean {

    String name,id,image;




    public Sellbean(String name, String id,String image) {
        this.name = name;
        this.id = id;
        this.image=image;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
