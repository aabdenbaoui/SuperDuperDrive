package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
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


    public void createOrUpdateCredential(Credential credential){
        Credential tempCredential = credentialMapping.getCredentialById(credential.getCredentialId());
        if(tempCredential == null){
            createCredentialService(credential);
        }else{
            updateCredential(credential);
        }

    }
    private int createCredentialService(Credential credential){
        String key = "7x!A%D*G-JaNdRgU";
        Integer userid = authenticationService.getUserId();
        credential.setUserId(userid);
        return credentialMapping.insert(new Credential(null, credential.getUrl(), credential.getUserName(), key, encryptionService.encryptValue(credential.getPassword(), key), userid));
    }
    public List<Credential>  getAllCredentials(){
        List<Credential> credentialsWithUnencryptedPassword = new ArrayList<>();
        Integer userid = authenticationService.getUserId();
        for(Credential tempCredential: credentialMapping.getAllCredentials(userid)){
            String unencryptedPass =  encryptionService.decryptValue(tempCredential.getPassword(), tempCredential.getKey());
            credentialsWithUnencryptedPassword.add(new Credential(tempCredential.getCredentialId(), tempCredential.getUrl(), tempCredential.getUserName(), tempCredential.getKey(), unencryptedPass, tempCredential.getUserId() ));
        }
        return credentialsWithUnencryptedPassword;
    }

    public void deleteById(Integer id) {
        System.out.println("The id to be deleted: " + id);
       credentialMapping.deleteById(id);
    }

    private void updateCredential(Credential credential) {
        credentialMapping.updateCredential(credential);
    }

    public Credential getCredentialById(Integer id) {
        return credentialMapping.getCredentialById(id);
    }
}
