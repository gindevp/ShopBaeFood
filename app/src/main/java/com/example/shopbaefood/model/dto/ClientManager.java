package com.example.shopbaefood.model.dto;

public class ClientManager {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String avatar;
    private String openTime;
    private String closeTime;
    private String status;

    public ClientManager(Long id, String name, String phone, String address, String avatar, String openTime,
                         String closeTime, String status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
