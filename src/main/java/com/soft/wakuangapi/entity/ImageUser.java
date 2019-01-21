package com.soft.wakuangapi.entity;

public class ImageUser {
    private String name;
    private String base64;

    public ImageUser() {
    }

    public ImageUser(String name, String base64) {
        this.name = name;
        this.base64 = base64;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
