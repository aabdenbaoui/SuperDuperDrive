package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.mappers.IFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    @Autowired
    IFileMapper fileMapper;

    @Autowired
    AuthenticationService authenticationService;

    public int createFile(MultipartFile file) throws IOException {
//        (null, StringUtils.cleanPath(file.getOriginalFilename()),  file.getContentType(), String.valueOf(file.getSize()),  file.getInputStream().readAllBytes()))
        Integer userid = authenticationService.getUserId();
        return fileMapper.insert(new File(null, StringUtils.cleanPath(file.getOriginalFilename()), file.getContentType(), String.valueOf(file.getSize()),  file.getInputStream().readAllBytes(),userid));
    }

    public List<File> getAllFiles(){
        Integer userid = authenticationService.getUserId();
        return fileMapper.getAllFiles(userid);
    }

    public void deleteById(Integer id) {
        fileMapper.deleteById(id);
    }


    public File getFileById(Integer id) {
        File tempFile = fileMapper.getFileById(id);
        return tempFile;
    }
}
