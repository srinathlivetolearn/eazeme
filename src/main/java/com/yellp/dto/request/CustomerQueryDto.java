package com.yellp.dto.request;

public class CustomerQueryDto {
    private String name;
    private String contact;
    private String description;
    private int tPlusMinutes;
    private Long requestedAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int gettPlusMinutes() {
        return tPlusMinutes;
    }

    public void settPlusMinutes(int tPlusMinutes) {
        this.tPlusMinutes = tPlusMinutes;
    }

    public Long getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Long requestedAt) {
        this.requestedAt = requestedAt;
    }
}
