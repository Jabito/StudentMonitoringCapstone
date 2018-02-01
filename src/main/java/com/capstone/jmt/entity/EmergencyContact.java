package com.capstone.jmt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jabito on 25/08/2017.
 */
@Entity
public class EmergencyContact implements Serializable {

    @JsonProperty("id")
    private String id;
    @JsonProperty("contactOf")
    private String contactOf;
    @JsonProperty("contactName")
    private String contactName;
    @JsonProperty("relationship")
    private String relationship;
    @JsonProperty("contactNo")
    private String contactNo;
    @JsonProperty("address")
    private String address;
    @JsonProperty("createdOn")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createdOn;
    @JsonProperty("updatedOn")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date updatedOn;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("updatedBy")
    private String updatedBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactOf() {
        return contactOf;
    }

    public void setContactOf(String contactOf) {
        this.contactOf = contactOf;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
}
