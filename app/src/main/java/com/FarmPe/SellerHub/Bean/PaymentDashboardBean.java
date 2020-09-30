package com.FarmPe.SellerHub.Bean;

public class PaymentDashboardBean {

    String item_cost,delivery_charges,total_amount,total_transactions,netbank;
    String isIFSCCodeExist, stateId, districtId;

    public PaymentDashboardBean(String item_cost, String delivery_charges, String total_amount, String total_transactions, String netbank) {

        this.item_cost = item_cost;
        this.delivery_charges = delivery_charges;
        this.total_amount = total_amount;
        this.total_transactions = total_transactions;
        this.netbank = netbank;
    }

    public String getItem_cost() {
        return item_cost;
    }

    public String getDelivery_charges() {
        return delivery_charges;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public String getTotal_transactions() {
        return total_transactions;
    }

    public String getNetbank() {
        return netbank;
    }

    public String getIsIFSCCodeExist() {
        return isIFSCCodeExist;
    }

    public String getStateId() {
        return stateId;
    }

    public String getDistrictId() {
        return districtId;
    }
}