package com.example.shopbaefood.model.dto;

public class ProductForm {
    private Long id;
    private String name;
    private String shortDescription;
    private Double oldPrice;
    private Double newPrice;
    private String image;
    private int quantity;

    public ProductForm(String name, String shortDescription, Double oldPrice, Double newPrice, String image, int quantity) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.image = image;
        this.quantity = quantity;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Double newPrice) {
        this.newPrice = newPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
