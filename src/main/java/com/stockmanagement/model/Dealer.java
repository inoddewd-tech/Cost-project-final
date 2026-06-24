package com.stockmanagement.model;

public class Dealer {
    private int id;
    private String businessName;
    private String phone;
    private String area;

    public Dealer() {}
    public Dealer(int id, String businessName, String phone, String area) {
        this.id = id; this.businessName = businessName; this.phone = phone; this.area = area;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
}