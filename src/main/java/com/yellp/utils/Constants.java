package com.yellp.utils;

public enum Constants {
    API_KEY_USER_ID("Api key user id");

    private String value;

    Constants(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
