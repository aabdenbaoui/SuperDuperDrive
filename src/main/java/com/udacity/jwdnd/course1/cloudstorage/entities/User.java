package com.udacity.jwdnd.course1.cloudstorage.entities;

import java.util.ArrayList;
import java.util.List;

public class User {

     private String userId;
     private String username;
     private String salt;
     private String password;
     private String firstName;
     private String lastName;
     private List<Credential> credentials = new ArrayList<>();
     private List<File> files = new ArrayList<>();
     private List<Note> notes = new ArrayList<>();



     public User() {
     }

     public User(String userId, String username, String salt, String password, String firstName, String lastName) {
          this.userId = userId;
          this.username = username;
          this.salt = salt;
          this.password = password;
          this.firstName = firstName;
          this.lastName = lastName;
     }

     public String getUsername() {
          return username;
     }

     public void setUsername(String username) {
          this.username = username;
     }

     public String getSalt() {
          return salt;
     }

     public void setSalt(String salt) {
          this.salt = salt;
     }

     public String getPassword() {
          return password;
     }

     public void setPassword(String password) {
          this.password = password;
     }

     public String getFirstName() {
          return firstName;
     }

     public void setFirstName(String firstName) {
          this.firstName = firstName;
     }

     public String getLastName() {
          return lastName;
     }

     public void setLastName(String lastName) {
          this.lastName = lastName;
     }

     public String getUserId() {
          return userId;
     }

     public List<Credential> getCredentials() {
          return credentials;
     }

     public List<File> getFiles() {
          return files;
     }

     public List<Note> getNotes() {
          return notes;
     }

     @Override
     public String toString() {
          return "User{" +
                  "userId='" + userId + '\'' +
                  ", username='" + username + '\'' +
                  ", salt='" + salt + '\'' +
                  ", password='" + password + '\'' +
                  ", firstName='" + firstName + '\'' +
                  ", lastName='" + lastName + '\'' +
                  '}';
     }
}
