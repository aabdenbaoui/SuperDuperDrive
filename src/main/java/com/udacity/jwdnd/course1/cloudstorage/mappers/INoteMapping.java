package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface INoteMapping {

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getNotesByUserId(Integer userid);

    @Delete("DELETE FROM NOTES WHERE noteId = #{id}")
    void deleteById(Integer id);
    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription=#{noteDescription}, userid =#{userId} WHERE noteId =  #{noteId}")
    void updateNote(Note note);
    @Select("SELECT * FROM NOTES WHERE noteId = #{id}")
    Note getNoteById(Integer id);

    @Select("SELECT * FROM NOTES WHERE noteTitle = #{title}")
    Note getNoteByTitle(String title);
}
