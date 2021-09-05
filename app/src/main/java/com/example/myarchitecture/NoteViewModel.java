package com.example.myarchitecture;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {


    private NoteRepository repository;
    private LiveData<List<Note_DBTable>> allNotes;
//in application view model we can pass the constructor, which
//whenever we need to pass a context, we can pass it as application.
//but never store a context, it can cause memory leak
    //so we pass application as context
    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();



    }
//wrapper methods fr db operations
public void insert (Note_DBTable note){

        repository.insert(note);
}

public void update(Note_DBTable note){
        repository.update(note);
}

public void delete(Note_DBTable note){
        repository.delete(note);
}

public void deleteAllNotes(){
        repository.deleteAllNotes();
}

//returns all notes member variable
    public LiveData<List<Note_DBTable>> getAllNotes() {
        return allNotes;
    }
}
