package com.capstone.jmt.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class AttendanceRow implements Serializable {

    @JsonProperty("date")
    @JsonFormat(pattern = "MMM dd, yyyy")
    private Date date;
    @JsonProperty("attendanceCount")
    private int attendanceCount;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    public void setAttendanceCount(int attendanceCount) {
        this.attendanceCount = attendanceCount;
    }
}
