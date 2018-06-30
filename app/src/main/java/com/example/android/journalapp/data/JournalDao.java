package com.example.android.journalapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface JournalDao {

    @Query("SELECT * FROM thejournal WHERE fireBaseUid = :fireBaseUid ORDER BY id")
    LiveData<List<JournalEntry>> loadAllEntries(String fireBaseUid);

    @Insert
    void insertEntry(JournalEntry journalEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEntry(JournalEntry journalEntry);

    @Delete
    void deleteEntry(JournalEntry journalEntry);

    @Query("SELECT * FROM thejournal WHERE id = :id")
    LiveData<JournalEntry> loadEntryById(int id);

}
