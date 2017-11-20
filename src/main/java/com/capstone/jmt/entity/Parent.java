package com.capstone.jmt.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jabito on 25/08/2017.
 */
@Entity
public class Parent implements Serializable{

    @JsonProperty("id")
    private String id;
    @JsonProperty("parentOf")
    private String parentOf;
    @JsonProperty("relationship")
    private String relationship;
    @JsonProperty("parentFName")
    private String parentFName;
    @JsonProperty("parentLName")
    private String parentLName;
    @JsonProperty("contactNo")
    private String contactNo;
    @JsonProperty("occupation")
    private String occupation;
    @JsonProperty("createdOn")
    private Date createdOn;
    @JsonProperty("updatedOn")
    private Date updatedOn;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("updatedBy")
    private String updatedBy;

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentOf() {
        return parentOf;
    }

    public void setParentOf(String parentOf) {
        this.parentOf = parentOf;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getParentFName() {
        return parentFName;
    }

    public void setParentFName(String parentFName) {
        this.parentFName = parentFName;
    }

    public String getParentLName() {
        return parentLName;
    }

    public void setParentLName(String parentLName) {
        this.parentLName = parentLName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
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
}