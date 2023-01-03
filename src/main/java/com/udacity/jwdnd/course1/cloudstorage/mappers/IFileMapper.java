package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.web.bind.annotation.RequestMapping;

@Mapper
public interface IFileMapper {
//    fileId INT PRIMARY KEY auto_increment,
//    filename VARCHAR,
//    contenttype VARCHAR,
//    filesize VARCHAR,
//    userid INT,
//    filedata BLOB,
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId},  #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);


}
