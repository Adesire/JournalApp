package com.example.android.journalapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.journalapp.data.AppDatabase;
import com.example.android.journalapp.data.JournalEntry;

public class JournalViewModel extends ViewModel {

    private LiveData<JournalEntry> entry;

    public JournalViewModel(AppDatabase db, int entryId) {
        entry = db.journalDao().loadEntryById(entryId);
    }

    public LiveData<JournalEntry> getEntry() { return entry; }
}
