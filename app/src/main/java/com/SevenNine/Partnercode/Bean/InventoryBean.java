package com.SevenNine.Partnercode.Bean;

public class InventoryBean {

    String prod_name,prod_desc,brand,quantity,amount,mrp,prod_icon,selling_icon;




    public InventoryBean(String prod_name, String prod_desc, String brand,String quantity,String amount,String mrp,String prod_icon,String selling_icon) {
        this.prod_name = prod_name;
        this.prod_desc = prod_desc;
        this.brand=brand;
        this.quantity=quantity;
        this.amount=amount;
        this.mrp=mrp;
        this.prod_icon=prod_icon;
        this.selling_icon=selling_icon;

    }

    public String getProd_name() {
        return prod_name;
    }

    public String getProd_desc() {
        return prod_desc;
    }

    public String getBrand() {
        return brand;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getAmount() {
        return amount;
    }

    public String getMrp() {
        return mrp;
    }

    public String getProd_icon() {
        return prod_icon;
    }

    public String getSelling_icon() {
        return selling_icon;
    }
}
