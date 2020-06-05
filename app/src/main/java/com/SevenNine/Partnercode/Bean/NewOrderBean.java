package com.SevenNine.Partnercode.Bean;

public class NewOrderBean {

    String prod_name,CreatedOn,prod_img,TxnId,Amount,Quantity,Uom,productInfo,mode,firstname,SellingCategoryName,Products_Icon,SellingListName,brand,prod_desc;

    public NewOrderBean(String prod_name, String CreatedOn, String prod_img, String TxnId, String Amount, String Quantity, String Uom, String productInfo, String mode, String firstname,String SellingCategoryName,String Products_Icon,String SellingListName,String brand,String prod_desc) {

        this.prod_name = prod_name;
        this.CreatedOn = CreatedOn;
        this.prod_img = prod_img;
        this.TxnId = TxnId;
        this.Amount = Amount;
        this.Quantity = Quantity;
        this.Uom = Uom;
        this.productInfo = productInfo;
        this.mode = mode;
        this.firstname = firstname;
        this.SellingCategoryName = SellingCategoryName;
        this.Products_Icon = Products_Icon;
        this.SellingListName = SellingListName;
        this.brand = brand;
        this.prod_desc = prod_desc;
    }

    public String getProd_name() {
        return prod_name;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public String getProd_img() {
        return prod_img;
    }

    public String getTxnId() {
        return TxnId;
    }

    public String getAmount() {
        return Amount;
    }

    public String getQuantity() {
        return Quantity;
    }

    public String getUom() {
        return Uom;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public String getMode() {
        return mode;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSellingCategoryName() {
        return SellingCategoryName;
    }

    public String getProducts_Icon() {
        return Products_Icon;
    }

    public String getSellingListName() {
        return SellingListName;
    }

    public String getBrand() {
        return brand;
    }

    public String getProd_desc() {
        return prod_desc;
    }
}
