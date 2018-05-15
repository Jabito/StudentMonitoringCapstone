package com.capstone.jmt.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AddReportModel implements Serializable {

    @JsonProperty("studentId")
    private String studentId;
    @JsonProperty("guardianName")
    private String guardianName;
    @JsonProperty("message")
    private String message;
    @JsonProperty("dateOfIncident")
    private String dateOfIncident;
    @JsonProperty("caseOfIncident")
    private String caseOfIncident;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
