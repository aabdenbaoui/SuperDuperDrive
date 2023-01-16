package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.mappers.ICredentialMapping;
import com.udacity.jwdnd.course1.cloudstorage.mappers.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    @Autowired
    ICredentialMapping credentialMapping;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    HashService hashService;

    @Autowired
    EncryptionService encryptionService;
    public void  createOrUpdateCredential(Credential credential) {
        Credential tempCredential = credentialMapping.getCredentialById(credential.getCredentialId());
        System.out.println(credential.getCredentialId() + ":" + credential.getUrl());
        if (tempCredential == null) {
             createCredentialService(credential);
        } else {
             updateCredential(credential);
        }
    }

    private int createCredentialService(Credential credential){
        if(credentialMapping.getCredentialByUrl(credential.getUrl()) != null){
            throw new IllegalArgumentException("Duplicate url");
        }
        String key = "7x!A%D*G-JaNdRgU";
        Integer userid = authenticationService.getUserId();
        credential.setUserId(userid);
        return credentialMapping.insert(new Credential(null, credential.getUrl(), credential.getUserName(), key, encryptionService.encryptValue(credential.getPassword(), key), userid));
    }
    public List<Credential>  getAllCredentials(){
        return credentialMapping.getAllCredentials(authenticationService.getUserId());
    }

    public void deleteById(Integer id) {
        System.out.println("The id to be deleted: " + id);
       credentialMapping.deleteById(id);
    }

    private void updateCredential(Credential credential) {
        String key = "7x!A%D*G-JaNdRgU";
        credential.setKey(key);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), key));
        credential.setUserId(authenticationService.getUserId());
        credentialMapping.updateCredential(credential);
    }

    public Credential getCredentialById(Integer id) {
        return credentialMapping.getCredentialById(id);
    }
}
