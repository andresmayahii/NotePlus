package com.itkloud.noteplus.dao;

import com.itkloud.noteplus.dto.Note;

import java.util.List;

/**
 * Created by andressh on 20/11/14.
 */
public interface NoteDao {

    List<Note> getAll();
    void delete(Note note);
    void saveOrUpdate(Note note);
    void flush();

}
