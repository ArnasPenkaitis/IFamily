package com.example.ifamily;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class familyatach extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familyatach);

        final EditText familyCodeInput = (EditText) findViewById(R.id.fncode);
        final EditText familyNameInput = (EditText) findViewById(R.id.fninput);
        Button attachBtn = (Button) findViewById(R.id.atachfam);



        attachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String familyCode = familyCodeInput.getText().toString();
                String familyName = familyNameInput.getText().toString();
                if(familyCode.length()!=5 && familyName.length() < 5)
                {
                    Snackbar.make(v, "Invalid family name or code.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    final FirebaseFirestore database = FirebaseFirestore.getInstance();
                    database.collection("Family")
                            .whereEqualTo("Name",familyName)
                            .whereEqualTo("Code",familyCode)
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                    if(e!= null){
                                        Log.d("Fragment",e.getMessage());
                                    }
                                    Bundle b = getIntent().getExtras();
                                    String userID = b.getString("UID");
                                    for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){

                                        database.collection("Users").document(userID)
                                                .update("familyId",doc.getDocument().getId()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                EditText familyCodeInput = (EditText) findViewById(R.id.fncode);
                                                final EditText familyNameInput = (EditText) findViewById(R.id.fninput);
                                                familyCodeInput.setEnabled(false);
                                                familyNameInput.setEnabled(false);
                                                Toast.makeText(familyatach.this,"Attached to family",Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                }
                            });
                }
            }
        });
    }
}
