package com.example.ifamily;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ifamily.Models.NotesModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter {

    public List<NotesModel> notes;
    public  ListAdapter(List<NotesModel> notesmodel)
    {
        this.notes = notesmodel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardlayout,viewGroup,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ListViewHolder) viewHolder ).bindView(i);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView itemText,itemTitle;
        private Switch aswitch;
        private View card;
        public ListViewHolder(View itemView)
        {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.textView5);
            itemText = (TextView) itemView.findViewById(R.id.textView6);
            aswitch = (Switch) itemView.findViewById(R.id.switch1);
            card = (View) itemView.findViewById(R.id.carderino);
            itemView.setOnClickListener(this);
        }

        public void bindView (final int position){
            itemText.setText(notes.get(position).text);
            itemTitle.setText(notes.get(position).authorname);
            if(notes.get(position).isdone)
            {
                aswitch.setChecked(true);
                aswitch.setEnabled(false);
            }

            aswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    FirebaseFirestore database = FirebaseFirestore.getInstance();
                    notes.get(position).setIsdone(true);
                    database.collection("Notes").document(notes.get(position).noteid).set(notes.get(position)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            aswitch.setEnabled(false);
                        }
                    });
                }
            });

            card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    FirebaseFirestore database = FirebaseFirestore.getInstance();
                    database.collection("Notes").document(notes.get(position).noteid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            notes.remove(notes.get(position));
                            notifyDataSetChanged();
                        }
                    });
                    return true;
                }
            });

        }

        public void onClick(View view){

        }


    }
}
