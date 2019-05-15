package com.example.ifamily;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class registerfamily extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerfamily);
        final EditText family = (EditText) findViewById(R.id.txtPwd);
        Button regbutton = (Button) findViewById(R.id.btnRegister);
        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String familyName = family.getText().toString();

                if(familyName.length()<5)
                {
                    Snackbar.make(v, "Family name must be longer than five characters", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    registerFamily(familyName);
                }
            }
        });
    }

    private void registerFamily(String familyName) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        final Integer code = new Random().nextInt(9999)+10000;
        Map<String,Object> family = new HashMap<>();
        family.put("Name",familyName);
        family.put("Code",code.toString());
        database.collection("Family").document()
                .set(family).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                TextView forCode = (TextView) findViewById(R.id.loginscrn);
                final EditText familyinput = (EditText) findViewById(R.id.txtPwd);
                familyinput.setEnabled(false);
                forCode.setTextSize(80);
                forCode.setText(code.toString());
            }
        });
    }
}
