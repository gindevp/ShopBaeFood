package com.example.shopbaefood.model.dto;

public class AccountRegisterDTO {
    private Long id;
    private String userName;
    private String password;
    private String email;
    private String name;
    private String phone;
    private String address;

    public AccountRegisterDTO() {
    }

    public AccountRegisterDTO( String userName, String password, String email, String name, String phone, String address) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
