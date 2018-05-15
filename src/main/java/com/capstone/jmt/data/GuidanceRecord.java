package com.capstone.jmt.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jabito on 29/01/2018.
 */
public class GuidanceRecord implements Serializable{

    @JsonProperty("id")
    private String id;
    @JsonProperty("studentId")
    private String studentId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("createdOn")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createdOn;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("updatedOn")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date updatedOn;
    @JsonProperty("updatedBy")
    private String updatedBy;
    @JsonProperty("caseOfIncident")
    private String caseOfIncident;
    @JsonProperty("dateOfIncident")
    private String dateOfIncident;
    @JsonProperty("nameOfGuardian")
    private String nameOfGuardian;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCaseOfIncident() {
        return caseOfIncident;
    }

    public void setCaseOfIncident(String caseOfIncident) {
        this.caseOfIncident = caseOfIncident;
    }

    public String getDateOfIncident() {
        return dateOfIncident;
    }

    public void setDateOfIncident(String dateOfIncident) {
        this.dateOfIncident = dateOfIncident;
    }

    public String getNameOfGuardian() {
        return nameOfGuardian;
    }

    public void setNameOfGuardian(String nameOfGuardian) {
        this.nameOfGuardian = nameOfGuardian;
    }
}
