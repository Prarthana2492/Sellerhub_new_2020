package com.SevenNine.Partnercode.Bean;

public class AddTractorBean2 {

   private int image1;
    private String prod_name,id;


    public AddTractorBean2(int image1, String prod_name, String id) {

        this.image1 = image1;
        this.prod_name = prod_name;
        this.id = id;

    }


    public String getProd_name() {
        return prod_name;
    }

    public String getId() {
        return id;
    }

    public int getImage() {
        return image1;
    }


}
