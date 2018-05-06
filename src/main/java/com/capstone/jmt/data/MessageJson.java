package com.capstone.jmt.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jabito on 16/11/2017.
 */
public class MessageJson implements Serializable{

    @JsonProperty("messageTypeId")
    private Integer messageTypeId;
    @JsonProperty("message")
    private String message;
    @JsonProperty("postedBy")
    private String postedBy;
    @JsonProperty("datePosted")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date datePosted;
    @JsonIgnore
    private String messageTarget;
    @JsonProperty("targetIds")
    private String[] targetIds;

    public void setMessageTarget(String messageTarget) {
        this.messageTarget = messageTarget;
    }

    public String getMessageTarget() {
        return messageTarget;
    }

    public String[] getTargetIds() {
        return targetIds;
    }

    public void setTargetIds(String[] targetIds) {
        this.targetIds = targetIds;
    }

    public Integer getMessageTypeId() {
        return messageTypeId;
    }

    public void setMessageTypeId(Integer messageTypeId) {
        this.messageTypeId = messageTypeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }
}
