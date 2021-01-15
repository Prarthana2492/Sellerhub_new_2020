package com.FarmPe.SellerHub.Bean;

public class Inventory_Static_Bean{

    String product_name,quantity,price,mrp;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public int getProduct_icon() {
        return product_icon;
    }

    public void setProduct_icon(int product_icon) {
        this.product_icon = product_icon;
    }

    public Inventory_Static_Bean(String product_name, String quantity, String price, String mrp, int product_icon) {
        this.product_name = product_name;
        this.quantity = quantity;
        this.price = price;
        this.mrp = mrp;
        this.product_icon = product_icon;
    }

    int product_icon;
}


