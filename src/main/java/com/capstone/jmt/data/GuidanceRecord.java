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
    @JsonProperty("guidanceId")
    private String guidance;
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
    @JsonProperty("studentName")
    private String studentName;
    @JsonProperty("gradeLevel")
    private String gradeLevel;
    @JsonProperty("sectionName")
    private String sectionName;
    @JsonProperty("gradeAndSection")
    private String gradeAndSection;
    @JsonProperty("violation")
    private String violation;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("middleName")
    private String middleName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuidance() {
        return guidance;
    }

    public void setGuidance(String guidance) {
        this.guidance = guidance;
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

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getGradeAndSection() {
        return gradeAndSection;
    }

    public void setGradeAndSection(String gradeAndSection) {
        this.gradeAndSection = gradeAndSection;
    }

    public String getViolation() {
        return violation;
    }

    public void setViolation(String violation) {
        this.violation = violation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
