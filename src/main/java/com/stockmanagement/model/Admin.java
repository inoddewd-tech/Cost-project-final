package com.stockmanagement.model;

public class Admin extends User {
    private String adminLevel; 

    public Admin() { super(); }
    public Admin(int id, String username, String email, String adminLevel) {
        super(id, username, email, "ADMIN");
        this.adminLevel = adminLevel;
    }
    public String getAdminLevel() { return adminLevel; }
    public void setAdminLevel(String adminLevel) { this.adminLevel = adminLevel; }
}
