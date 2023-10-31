package com.example.shopbaefood.model.dto;



public class ChangeDTO {
    private Long id;
    private String avatar;
    private String name;
    private String phone;
    private String address;
    private String email;
    private String openTime;
    private String closeTime;

    public ChangeDTO() {

    }

    public ChangeDTO(Long id, String avatar, String name, String phone, String address, String email, String openTime,
                     String closeTime) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

}
