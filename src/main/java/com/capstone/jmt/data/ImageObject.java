package com.capstone.jmt.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;

public class ImageObject implements Serializable {

    @JsonProperty("id")
    private int id;

    @JsonProperty("requestId")
    private int requestId;


    @JsonProperty("fileId")
    private String fileId;


    @JsonProperty("contentType")
    private String contentType;


    @JsonProperty("originalFileName")
    private String originalFileName;


    @JsonProperty("fileSuffix")
    private String fileSuffix;

    @JsonProperty("uri")
    private URI uri;

    @JsonProperty("url")
    private URL url;

    @JsonProperty("fileNameNoSuffix")
    private String fileNameNoSuffix;


    @JsonProperty("content")
    private byte[] content;


    @JsonProperty("requestCode")
    private String requestCode;

    @JsonProperty("transactionId")
    private String transactionId;


    @JsonProperty("fileMedia")
    private byte[] fileMedia;


    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public byte[] getFileMedia() {
        return fileMedia;
    }

    public void setFileMedia(byte[] fileMedia) {
        this.fileMedia = fileMedia;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
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

    public String getFileNameNoSuffix() {
        return fileNameNoSuffix;
    }

    public void setFileNameNoSuffix(String fileNameNoSuffix) {
        this.fileNameNoSuffix = fileNameNoSuffix;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
