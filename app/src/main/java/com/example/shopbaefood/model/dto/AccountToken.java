package com.example.shopbaefood.model.dto;

import com.example.shopbaefood.model.Merchant;
import com.example.shopbaefood.model.User;

import java.util.Arrays;

public class AccountToken {
    private Long id;
    private String username;
    private String token;
    private String[] roles;
    private Merchant merchant;
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ResponseToken{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", roles=" + Arrays.toString(roles) +
                ", merchant=" + merchant +
                ", user=" + user +
                '}';
    }
}
