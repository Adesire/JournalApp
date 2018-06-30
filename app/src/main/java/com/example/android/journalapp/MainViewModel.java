package com.example.android.journalapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.journalapp.data.AppDatabase;
import com.example.android.journalapp.data.JournalEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<JournalEntry>> entry;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the task from the Database");
        entry = database.journalDao().loadAllEntries();
    }

    public LiveData<List<JournalEntry>> getEntry() { return entry; }
}
