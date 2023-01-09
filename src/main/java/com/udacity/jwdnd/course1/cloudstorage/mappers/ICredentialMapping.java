package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ICredentialMapping {
    @Select("SELECT * FROM CREDENTIALS WHERE username = #{username}")
    User getCredential(String username);
    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{id}")
    Credential getCredentialById(Integer id);
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES(#{url}, #{userName}, #{key}, #{password},  #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getAllCredentials(Integer userid);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{id}")
    void deleteById(Integer id);
    @Update("UPDATE CREDENTIALS SET url = #{url}, username=#{userName}, key = #{key},password = #{password}, userid =  #{userId} WHERE credentialid=#{credentialId}")
    void updateCredential(Credential credential);
}
