package com.example.ifamily;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 7117;
    List<AuthUI.IdpConfig> providers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        providers = Arrays.asList(
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        ConstraintLayout layout = findViewById(R.id.root_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        ImageView imageView = findViewById(R.id.logo);
        Button loginBtn = findViewById(R.id.login);
        Button registerBtn = findViewById(R.id.register);


        imageView.animate().alpha(1f).setDuration(2000);
        loginBtn.animate().alpha(1f).setDuration(2000);
        registerBtn.animate().alpha(1f).setDuration(2000);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,registerfamily.class);
                startActivity(intent);
            }
        });


        configureLoginFormButton();
    }

    private void showSignInOptions() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false).setAvailableProviders(providers).build(),MY_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_REQUEST_CODE)
        {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK)
            {
                FirebaseFirestore database = FirebaseFirestore.getInstance();
                FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
                Log.d("User",user.toString());
                Intent intent = new Intent(MainActivity.this,loginform.class);
                intent.putExtra("UserName",user.getUid());
                final String userid= user.getUid();
                final String userprofile = user.getPhotoUrl().toString();
                final String username = user.getDisplayName();
                Log.d("PICTURE", userprofile);
                database.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            boolean exists = false;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getData().get("name").toString().equals(username)){
                                    exists= true;
                                    break;
                                }
                            }
                            if(exists == false){
                                //create user here
                                FirebaseFirestore database = FirebaseFirestore.getInstance();
                                Map<String,Object> user = new HashMap<>();
                                user.put("name",username);
                                user.put("image",userprofile);
                                user.put("familyId","");
                                database.collection("Users").document(userid)
                                        .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                    }
                                });
                            }
                        }else{

                        }
                    }
                });


                startActivity(intent);
            }else {
                Toast.makeText(this,""+response.getError().getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void configureLoginFormButton(){
        Button loginBtn =   (Button) findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInOptions();
            }
        });
    }
}
