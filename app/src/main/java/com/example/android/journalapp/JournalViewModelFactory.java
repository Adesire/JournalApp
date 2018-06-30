package com.example.android.journalapp;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.journalapp.data.AppDatabase;

public class JournalViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mEntryId;

    JournalViewModelFactory(AppDatabase db, int entryId) {
        mDb = db;
        mEntryId = entryId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new JournalViewModel(mDb, mEntryId);
    }

}
