package com.capstone.jmt.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by macbookpro on 5/7/18.
 */
public class RefUserType {

    @JsonProperty("id")
    private int id;
    @JsonProperty("user_type")
    private String user_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
