package com.capstone.jmt.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserList implements Serializable {

    @JsonProperty("name")
    private String name;
    @JsonProperty("userId")
    private String userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
