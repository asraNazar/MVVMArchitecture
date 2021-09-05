package com.example.myarchitecture;

import android.app.Application;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDAO noteDAO;
    private LiveData<List<Note_DBTable>> allNotes;

    public NoteRepository(Application application){
        NoteDBRoom database = NoteDBRoom.getInstance(application);
        //for the context we placed application
        //since application is subclass of context

        noteDAO = database.noteDAO();
        allNotes = noteDAO.getAllNotes();
    }

    public void insert(Note_DBTable note){
new InsertNoteAsyncTask(noteDAO).execute(note);
    }

    public void update(Note_DBTable note){
        new UpdateNoteAsyncTask(noteDAO).execute(note);
    }

    public void delete(Note_DBTable note){
        new DeleteNoteAsyncTask(noteDAO).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNotesAsyncTask(noteDAO).execute();
    }

    public LiveData<List<Note_DBTable>> getAllNotes(){
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note_DBTable,Void,Void>{

        private NoteDAO noteDAO;

        private InsertNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note_DBTable... note_dbTables) {

            noteDAO.insert(note_dbTables[0]);

            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note_DBTable,Void,Void>{

        private NoteDAO noteDAO;

        private UpdateNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note_DBTable... note_dbTables) {

            noteDAO.update(note_dbTables[0]);

            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note_DBTable,Void,Void>{

        private NoteDAO noteDAO;

        private DeleteNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note_DBTable... note_dbTables) {

            noteDAO.delete(note_dbTables[0]);

            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void,Void,Void>{

        private NoteDAO noteDAO;

        private DeleteAllNotesAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Void...voids) {

            noteDAO.DeleteAllNotes();

            return null;
        }
    }







}
