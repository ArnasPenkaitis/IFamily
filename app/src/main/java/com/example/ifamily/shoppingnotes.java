package com.example.ifamily;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ifamily.Models.NotesModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class shoppingnotes extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Dialog dialog;
    private List<NotesModel> notes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_shoppingnotes, container, false);
        notes= new ArrayList<NotesModel>();

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.shoprview);

        Bundle b = getActivity().getIntent().getExtras();
        final String userID = b.getString("UID");
        final String familyID = b.getString("FID");
        final String authorname = b.getString("Name");
        final ListAdapter listAdapter = new ListAdapter(notes);

        recyclerView.setAdapter(listAdapter);
        dialog= new Dialog(getActivity());
        dialog.setContentView(R.layout.popupform);
        Button close= (Button) dialog.findViewById(R.id.closebtn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        FloatingActionButton fab = rootView.findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        Button addnote = (Button) dialog.findViewById(R.id.btnRegister);

        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText notetext = (EditText) dialog.findViewById(R.id.inputnote);

                FirebaseFirestore database = FirebaseFirestore.getInstance();
                if(notetext.getText().length() < 3)
                {
                    Snackbar.make(v, "Event name must be longer than five characters", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    String textas = notetext.getText().toString();
                    Map<String,Object> note = new HashMap<>();
                    note.put("text",textas);
                    note.put("familyid",familyID);
                    note.put("authorid",userID);
                    note.put("type",false);
                    note.put("authorname", authorname);
                    note.put("isdone",false);
                    note.put("noteid","");

                    database.collection("Notes").document().set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            notetext.setText("");
                            dialog.dismiss();
                            listAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });

        database.collection("Notes").whereEqualTo("familyid",familyID).whereEqualTo("type",false).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!= null){
                    Log.d("Fragment",e.getMessage());
                }

                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType()== DocumentChange.Type.ADDED){
                        NotesModel note = doc.getDocument().toObject(NotesModel.class);
                        note.setNoteid(doc.getDocument().getId());
                        notes.add(note);

                        listAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return rootView;
    }


}
