package com.capstone.jmt.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Jabito on 20/11/2017.
 */
public class RefGradeLevel implements Serializable{

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("gradeLevel")
    private String gradeLevel;

    public RefGradeLevel(){}
    public RefGradeLevel(String gradeLevel, int id) {
        this.id = id;
        this.gradeLevel = gradeLevel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }
}
