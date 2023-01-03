package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note,NoteAdapter.NoteViewHoler> {

    Context context;
    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHoler holder, int position, @NonNull Note note) {
        holder.titleTxt.setText(note.title);
        holder.contentTxt.setText(note.content);
        holder.timestampTxt.setText(Utility.timestampToString(note.timestamp));

        holder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context,NoteDetailsActivity.class);
            intent.putExtra("title",note.title);
            intent.putExtra("content",note.content);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId",docId);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public NoteViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_note_item,parent,false);
        return new NoteViewHoler(view);
    }

    class NoteViewHoler extends RecyclerView.ViewHolder{

        TextView titleTxt,contentTxt,timestampTxt;

        public NoteViewHoler(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.note_title_txt);
            contentTxt = itemView.findViewById(R.id.note_content_txt);
            timestampTxt = itemView.findViewById(R.id.note_timestamp_txt);
        }
    }
}
