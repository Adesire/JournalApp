package com.example.android.journalapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.journalapp.data.AppDatabase;
import com.example.android.journalapp.data.JournalEntry;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class JournalActivity extends AppCompatActivity {

    public static final String EXTRA_ENTRY_ID = "extraEntryId";
    public static final String INSTANCE_ENTRY_ID = "instanceEntryId";

    private static final int DEFAULT_ENTRY_ID =-1;
    private static final String TAG = JournalActivity.class.getSimpleName();

    EditText titleEditText, messageEditText;
    Button saveBtn;

    private int mEntryId = DEFAULT_ENTRY_ID;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        initViews();

        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_ENTRY_ID)) {
            mEntryId = savedInstanceState.getInt(INSTANCE_ENTRY_ID, DEFAULT_ENTRY_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_ENTRY_ID)) {
            saveBtn.setText(getString(R.string.update));
            if (mEntryId == DEFAULT_ENTRY_ID) {
                mEntryId = intent.getIntExtra(EXTRA_ENTRY_ID,DEFAULT_ENTRY_ID);

                JournalViewModelFactory factory = new JournalViewModelFactory(mDb,mEntryId);

                final JournalViewModel viewModel = ViewModelProviders.of(this,factory).get(JournalViewModel.class);

                viewModel.getEntry().observe(this, new Observer<JournalEntry>() {
                    @Override
                    public void onChanged(@Nullable JournalEntry journalEntry) {
                        viewModel.getEntry().removeObserver(this);
                        populateUI(journalEntry);
                    }
                });
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_ENTRY_ID, mEntryId);
        super.onSaveInstanceState(outState);
    }

    private void initViews() {
        titleEditText = findViewById(R.id.titleEditText);
        messageEditText = findViewById(R.id.messageEditText);

        saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
    }

    private void populateUI(JournalEntry entry) {
        if(entry == null) {
            return;
        }

        titleEditText.setText(entry.getTitle());
        messageEditText.setText(entry.getMessage());
    }

    public void onSaveButtonClicked() {
        if (messageEditText.getText().toString().isEmpty() || titleEditText.getText().toString().isEmpty())
            return;
        String title = titleEditText.getText().toString();
        String message = messageEditText.getText().toString();
        Date date = new Date();

        final JournalEntry entry = new JournalEntry(title, message, date, MainActivity.FIREBASE_USER_ID);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mEntryId == DEFAULT_ENTRY_ID) {
                    mDb.journalDao().insertEntry(entry);
                } else {
                    entry.setId(mEntryId);
                    mDb.journalDao().updateEntry(entry);
                }
                finish();
            }
        });
    }

}
