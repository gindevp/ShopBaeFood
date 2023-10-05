package com.example.shopbaefood.model.dto;

public class ApiResponse<T> {
    private T data;
    private String message;

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}