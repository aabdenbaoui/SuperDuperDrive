package com.udacity.jwdnd.course1.cloudstorage.entities;

import java.sql.Blob;
import java.util.Arrays;

public class File {
    private Integer  fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private byte[] fileData;
    private Integer  userId;

    public File() {
    }

    public File(Integer fileId, String fileName, String contentType, String fileSize, byte[] fileData, Integer userId) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.fileData = fileData;
        this.userId = userId;
    }

    public Integer  getFileId() {
        return fileId;
    }

    public void setFileId(Integer  fileId) {
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

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public Integer  getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", fileSize='" + fileSize + '\'' +
//                ", fileData=" + Arrays.toString(fileData) +
                ", userId=" + userId +
                '}';
    }
}
