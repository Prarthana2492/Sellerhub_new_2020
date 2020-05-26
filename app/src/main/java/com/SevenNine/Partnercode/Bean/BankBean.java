package com.SevenNine.Partnercode.Bean;

public class BankBean {

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    public String getIfsccode() {
        return ifsccode;
    }

    public void setIfsccode(String ifsccode) {
        this.ifsccode = ifsccode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    String bankname;
    String name;
    String acc_no;
    String ifsccode;
    String area;
    String state;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    String district;
    String id;

    public String getStateid() {
        return stateid;
    }

    public void setStateid(String stateid) {
        this.stateid = stateid;
    }

    public String getDistrictid() {
        return districtid;
    }

    public void setDistrictid(String districtid) {
        this.districtid = districtid;
    }

    String stateid;
    String districtid;

    public BankBean(String bankname,String name, String acc_no,String ifsccode, String area, String state,String district,String id,String stateid,String districtid) {

        this.bankname = bankname;
        this.name = name;
        this.acc_no = acc_no;
        this.ifsccode = ifsccode;
        this.area = area;
        this.state = state;
        this.district = district;
        this.id = id;
        this.stateid = stateid;
        this.districtid = districtid;
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
