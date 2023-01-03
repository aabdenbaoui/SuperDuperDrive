package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.mappers.IFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    @Autowired
    IFileMapper fileMapper;

    @Autowired
    AuthenticationService authenticationService;

    public int createFile(File file){
        Integer userid = authenticationService.getUserId();
        return fileMapper.insert(new File(null, file.getFileName(), file.getContentType(), file.getFileSize(), file.getFileData() ,userid));
    }

    public List<File> getAllFiles(){
        Integer userid = authenticationService.getUserId();
        return fileMapper.getAllFiles(userid);
    }

    public void deleteById(Integer id) {
        fileMapper.deleteById(id);
    }
}
