package com.FarmPe.SellerHub.Bean;

public class MainVerticalBean {

    String name;
    String id;

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    String image1;
    int image;




    public MainVerticalBean(String name, String id, String image1,int image) {
        this.name = name;
        this.id = id;
        this.image1=image1;
        this.image=image;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
