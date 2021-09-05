package com.example.myarchitecture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST=1;
    private NoteViewModel noteViewModel;

    public static final int EDIT_NOTE_REQUEST=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);
            }
        });





        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        //View model knows which lifecycle it has to be scope of
        //view model ka instance le rhe
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this,new Observer<List<Note_DBTable>>(){
            //returns live data
            //live data is lifecycle aware
                    @Override
                    public void onChanged(List<Note_DBTable> note_dbTables) {
//update recyclerview  //this method is only called while activity is in the foreground
                       // Toast.makeText(MainActivity.this,"onChanged",Toast.LENGTH_SHORT).show();
                            adapter.submitList(note_dbTables);
                    }
                });
  //item touch helper makes view swipeable
  new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
          ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) //callback
          {
      @Override
      public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
          return false;
      }

      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

          noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
          Toast.makeText(MainActivity.this,"Note Deleted ",
                  Toast.LENGTH_SHORT).show();

      }
  }).attachToRecyclerView(recyclerView); //we have to attached it to recyclerview otherwise it will not work

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note_DBTable note_dbTable) {
// to handle the click here we implemented the onitemclicklistener as an anonymous inner class
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID,note_dbTable.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE,note_dbTable.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION,note_dbTable.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY,note_dbTable.getPriority());
                startActivityForResult(intent,EDIT_NOTE_REQUEST);
                //EDit note request so that we can get right Callback
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==ADD_NOTE_REQUEST && resultCode==RESULT_OK){
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY,1);

            Note_DBTable note = new Note_DBTable(title,description,priority);
            noteViewModel.insert(note);

            Toast.makeText(this,"Note saved" , Toast.LENGTH_SHORT).show();


        }
    else if (requestCode==EDIT_NOTE_REQUEST && resultCode==RESULT_OK){
        int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID,-1);

        if(id==-1){
            Toast.makeText(this,"Note can't be updated",Toast.LENGTH_SHORT).show();
            return;
        }
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY,1);

            Note_DBTable note = new Note_DBTable(title,description,priority);
           // note.setId(id);
            noteViewModel.update(note);

            Toast.makeText(this,"Note Updated",Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this,"Note not saved",Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this,"All notes deleted",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }



    }
}