package com.example.android.journalapp.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "thejournal")
public class JournalEntry {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getWrittenOn() {
        return writtenOn;
    }

    public void setWrittenOn(Date writtenOn) {
        this.writtenOn = writtenOn;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public String getFireBaseUid() {
        return fireBaseUid;
    }

    public void setFireBaseUid(String fireBaseUid) {
        this.fireBaseUid = fireBaseUid;
    }

    private String fireBaseUid;

    @ColumnInfo(name = "written_on")
    private Date writtenOn;

    @Ignore
    public JournalEntry(String title, String message, Date writtenOn, String fireBaseUid){
        this.title = title;
        this.message = message;
        this.writtenOn = writtenOn;
        this.fireBaseUid = fireBaseUid;
    }

    public JournalEntry(int id, String title, String message, Date writtenOn, String fireBaseUid){
        this.title = title;
        this.id = id;
        this.message = message;
        this.writtenOn = writtenOn;
        this.fireBaseUid = fireBaseUid;
    }
}
