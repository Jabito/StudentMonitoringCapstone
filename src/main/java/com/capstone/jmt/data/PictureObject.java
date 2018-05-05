package com.capstone.jmt.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.Lob;
import java.io.Serializable;

/**
 * Created by Jabito on 29/01/2018.
 */
public class PictureObject implements Serializable{

    @JsonProperty("studentId")
    private String studentId;

    @JsonProperty("fileId")
    private String fileId;

    @JsonProperty("contentType")
    private String contentType;

    @JsonProperty("originalFileName")
    private String originalFileName;

    @JsonProperty("fileSuffix")
    private String fileSuffix;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }
}
