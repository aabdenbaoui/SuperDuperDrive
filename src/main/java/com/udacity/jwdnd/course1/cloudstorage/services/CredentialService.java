package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.mappers.ICredentialMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {

    @Autowired
    ICredentialMapping credentialMapping;

    public int createCredentialService(Credential credential){
        return credentialMapping.createCredential(new Credential(null, credential.getUrl(), credential.getUsername(), credential.getPassword(), credential.getKey(), null));

    }

}
