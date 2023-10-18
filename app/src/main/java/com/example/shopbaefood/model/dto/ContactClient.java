package com.example.shopbaefood.model.dto;

import java.util.Arrays;

public class ContactClient {
    private int id;
    private String name, username, phone, address, email;
    private byte[] avartar;

    public ContactClient() {
    }

    public ContactClient(int id, String name, String username, String phone, String address, String email, byte[] avartar) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.avartar = avartar;
    }
    public ContactClient(String name, String username, String phone, String address, String email, byte[] avartar) {
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.avartar = avartar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getAvartar() {
        return avartar;
    }

    public void setAvartar(byte[] avartar) {
        this.avartar = avartar;
    }

    @Override
    public String toString() {
        return "ContactClient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", avartar=" + Arrays.toString(avartar) +
                '}';
    }
}
