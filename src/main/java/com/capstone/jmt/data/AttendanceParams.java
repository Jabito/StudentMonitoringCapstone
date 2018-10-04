package com.capstone.jmt.data;

public class AttendanceParams {

    private String dateFrom;
    private String dateTo;
    private int dateYear;
    private String searchString;

    public AttendanceParams() {
        this.dateFrom = "";
        this.dateTo = "";
        this.searchString = "";
    }

    public String getDateFrom() {
        return dateFrom;
    }
    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public int getDateYear() {
        return dateYear;
    }

    public void setDateYear(int dateYear) {
        this.dateYear = dateYear;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
