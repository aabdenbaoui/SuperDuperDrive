package com.udacity.jwdnd.course1.cloudstorage.entities;

import java.sql.Blob;

public class File {
    private String fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Blob fileData;
    private Integer  userId;

    public File() {
    }

    public File(String fileId, String fileName, String contentType, String fileSize, Blob fileData, Integer  userId) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.fileData = fileData;
        this.userId = userId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Blob getFileData() {
        return fileData;
    }

    public void setFileData(Blob fileData) {
        this.fileData = fileData;
    }

    public Integer  getUserId() {
        return userId;
    }
}
