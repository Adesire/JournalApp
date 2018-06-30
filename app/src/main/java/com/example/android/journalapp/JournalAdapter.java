package com.example.android.journalapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.android.journalapp.data.JournalEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.JournalViewHolder> {

    private static final String DATE_FORMAT = "dd/MM/yyy";

    final private ItemClickListener mItemClickListener;

    private List<JournalEntry> mJournalEntries;
    private Context mContext;

    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());


    JournalAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    @NonNull
    @Override
    public JournalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.journal_layout, parent, false);
        return new JournalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalViewHolder holder, int position) {
        JournalEntry journalEntry = mJournalEntries.get(position);
        String title = journalEntry.getTitle();
        //String message = journalEntry.getMessage();
        int serialNum = journalEntry.getId();
        String writtenOn = dateFormat.format(journalEntry.getWrittenOn());

        holder.titleTextView.setText(title);
        //holder.messageTextView.setText(message);
        holder.writtenOn.setText(writtenOn);
        holder.serialNum.setText(String.valueOf(++position));
    }

    @Override
    public int getItemCount() {
        if (mJournalEntries == null) {
            return 0;
        }
        return  mJournalEntries.size();
    }

    public List<JournalEntry> getJournalEntries() { return mJournalEntries; }

    public void setJournalEntries(List<JournalEntry> journalEntries) {
        mJournalEntries = journalEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    class JournalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTextView;
        TextView messageTextView;
        TextView writtenOn;
        TextView serialNum;

        JournalViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title);
            messageTextView = itemView.findViewById(R.id.message);
            writtenOn = itemView.findViewById(R.id.writtenAt);
            serialNum = itemView.findViewById(R.id.serialNum);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int elementId = mJournalEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }

}
