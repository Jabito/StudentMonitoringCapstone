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
public class Parent implements Serializable{

    @JsonProperty("id")
    private String id;
    @JsonProperty("parentOf")
    private String parentOf;
    @JsonProperty("relationship")
    private String relationship;
    @JsonProperty("parentName")
    private String parentName;
    @JsonProperty("officeNo")
    private String officeNo;
    @JsonProperty("occupation")
    private String occupation;
    @JsonProperty("createdOn")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createdOn;
    @JsonProperty("smsNotif")
    private Boolean smsNotif;
    @JsonProperty("updatedOn")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date updatedOn;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("updatedBy")
    private String updatedBy;
    @JsonProperty("email")
    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getSmsNotif() {
        return smsNotif;
    }

    public void setSmsNotif(Boolean smsNotif) {
        this.smsNotif = smsNotif;
    }

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


    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getOfficeNo() {
        return officeNo;
    }

    public void setOfficeNo(String officeNo) {
        this.officeNo = officeNo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
