package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface ICredentialMapping {
    @Select("SELECT * FROM CREDENTIALS WHERE username = #{username}")
    User getCredential(String username);
    @Insert("INSERT INTO CREDENTIALS (url, username, password, key, userid) " +
            "VALUES(#{url}, #{username}, #{password}, #{key}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int createCredential(Credential credential);

}
