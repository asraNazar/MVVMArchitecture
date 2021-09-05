package com.example.myarchitecture;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note_DBTable.class},version = 1)
public abstract class NoteDBRoom extends RoomDatabase {

    private static NoteDBRoom instance;

    public abstract NoteDAO noteDAO();

    public static synchronized NoteDBRoom getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDBRoom.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
    return instance;
    }
private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);
        new PopulateDBAsyncTask(instance).execute();
    }
} ;

private static class PopulateDBAsyncTask extends AsyncTask<Void,Void,Void>{

    private NoteDAO noteDAO;
    private PopulateDBAsyncTask(NoteDBRoom db){
        noteDAO=db.noteDAO();
    }

    @Override
    protected Void doInBackground(Void... voids) {

       noteDAO.insert(new Note_DBTable("Title 1","Description 1",1));
        noteDAO.insert(new Note_DBTable("Title 2","Description 2",2));
        noteDAO.insert(new Note_DBTable("Title 3","Description 3",3));


        return null;
    }
}





}
