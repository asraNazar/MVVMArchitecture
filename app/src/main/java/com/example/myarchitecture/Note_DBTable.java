package com.example.myarchitecture;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_Table")
public class Note_DBTable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private  String title;
    private String description;
    private int priority;

    public Note_DBTable(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public void setId(int id){
        this.id=id;
    }
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
