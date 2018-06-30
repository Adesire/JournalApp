package com.example.android.journalapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.example.android.journalapp.data.AppDatabase;
import com.example.android.journalapp.data.JournalEntry;

import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class DisplayJournalActivity extends AppCompatActivity implements JournalAdapter.ItemClickListener {

    private static final String TAG = DisplayJournalActivity.class.getSimpleName() ;
    private RecyclerView mRecyclerView;
    private JournalAdapter mAdapter;
    
    private AppDatabase mDb;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_journal);

        mRecyclerView = findViewById(R.id.recyclerViewEntries);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new JournalAdapter(this,this);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(),VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<JournalEntry> entry = mAdapter.getJournalEntries();
                        mDb.journalDao().deleteEntry(entry.get(position));
                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent journalActivityIntent = new Intent(DisplayJournalActivity.this,JournalActivity.class);
                startActivity(journalActivityIntent);
            }
        });

        mDb = AppDatabase.getInstance(getApplicationContext());
        setupViewModel();

    }

    private void setupViewModel(){
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getEntry().observe(this, new Observer<List<JournalEntry>>() {
            @Override
            public void onChanged(@Nullable List<JournalEntry> journalEntries) {
                Log.d(TAG,"Updating list of tasks from Live data in view model");
                mAdapter.setJournalEntries(journalEntries);
            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {

        Intent intent = new Intent(DisplayJournalActivity.this,JournalActivity.class);
        intent.putExtra(JournalActivity.EXTRA_ENTRY_ID,itemId);
        startActivity(intent);

    }
}
