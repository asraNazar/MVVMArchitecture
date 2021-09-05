package com.example.myarchitecture;

import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert
    void insert(Note_DBTable note);

    @Update
    void update(Note_DBTable note);

    @Delete
    void delete(Note_DBTable note);

    @Query("DELETE FROM note_Table")
    void DeleteAllNotes();

    @Query("SELECT * FROM note_Table ORDER BY priority DESC")
    LiveData<List<Note_DBTable>> getAllNotes();
}
