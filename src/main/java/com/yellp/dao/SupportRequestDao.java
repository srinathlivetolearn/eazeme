package com.yellp.dao;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;

@Component
@Entity
@Table(name="SUPPORT_REQUEST")
public class SupportRequestDao implements Serializable {
    @Column(name = "requestid",nullable = false,updatable = false)
    @Id
    private String requestId;

    @Column(name = "userid",nullable = false)
    private String userId;

    @Column(name = "customername",nullable = false)
    private String customerName;

    @NotBlank
    private String contact;

    @Column(name = "status",nullable = false)
    private Integer status;

    private String description;

    private String referrer;

    private Timestamp requestTime;

    private Integer tPlusMin;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public Timestamp getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Timestamp requestTime) {
        this.requestTime = requestTime;
    }

    public Integer gettPlusMin() {
        return tPlusMin;
    }

    public void settPlusMin(Integer tPlusMin) {
        this.tPlusMin = tPlusMin;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
