package com.udacity.jwdnd.course1.cloudstorage.entities;

import java.sql.Blob;

public class File {
    private long fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Blob fileData;
    private User user;

}
