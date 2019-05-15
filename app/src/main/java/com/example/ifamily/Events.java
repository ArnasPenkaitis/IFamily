package com.example.ifamily;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.EditText;

import com.example.ifamily.Models.EventsModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class Events extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    int selectedYear=0;
    int selectedMonth=0;
    int selectedDay=0;

    private List<EventsModel> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


        events= new ArrayList<EventsModel>();

        final CalendarView calendar = (CalendarView) findViewById(R.id.calendarView);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.revents);
        Bundle b = getIntent().getExtras();
        final String familyID = b.getString("FID");
        final ListAdapterForEvents listAdapter = new ListAdapterForEvents(events);
        recyclerView.setAdapter(listAdapter);
        final FirebaseFirestore database = FirebaseFirestore.getInstance();
        final EditText eventText = (EditText) findViewById(R.id.inputeventname);
        String selectedsDate= "";


        if(selectedDay==0|| selectedYear==0 || selectedMonth==0)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            selectedsDate= sdf.format(new Date(calendar.getDate()));
        }else {
            if(selectedMonth<10){
                selectedsDate = selectedDay + "/0"+selectedMonth+"/"+selectedYear;
            }else{
                selectedsDate = selectedDay + "/"+selectedMonth+"/"+selectedYear;
            }
        }


        FloatingActionButton fab = findViewById(R.id.fabevents);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eventText.getText().length() < 3)
                {
                    Snackbar.make(view, "Event name must be longer than five characters", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    String selectedsDate= "";
                    Map<String,Object> event = new HashMap<>();
                    if(selectedDay==0|| selectedYear==0 || selectedMonth==0)
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                         selectedsDate= sdf.format(new Date(calendar.getDate()));
                    }else {
                        if(selectedMonth<10 && selectedDay <10){
                            selectedsDate = "0"+selectedDay + "/0"+selectedMonth+"/"+selectedYear;
                        }else{
                            if(selectedMonth<10 && selectedDay >=10){
                                selectedsDate = selectedDay + "/0"+selectedMonth+"/"+selectedYear;
                            }else {
                                if(selectedMonth>=10 && selectedDay <10){
                                    selectedsDate = "0"+selectedDay + "/"+selectedMonth+"/"+selectedYear;
                                }else {
                                    selectedsDate = selectedDay + "/"+selectedMonth+"/"+selectedYear;
                                }
                            }
                        }
                    }
                    event.put("date",selectedsDate);
                    event.put("familyid",familyID);
                    event.put("text",eventText.getText().toString());
                    Log.d("Event", event.toString());
                    eventText.setText("");
                    View vie = view;

                    InputMethodManager inm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        inm.hideSoftInputFromWindow(vie.getWindowToken(),0);
                    database.collection("Events").document().set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                }
            }
        });

        database.collection("Events").whereEqualTo("familyid",familyID).whereEqualTo("date",selectedsDate).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!= null){
                    Log.d("Fragment",e.getMessage());
                }

                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType()== DocumentChange.Type.ADDED){
                        EventsModel event = doc.getDocument().toObject(EventsModel.class);
                        events.add(event);

                        listAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                FirebaseFirestore database = FirebaseFirestore.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String selected2Date = sdf.format(new Date(year-1900,month,dayOfMonth));
                Log.d("year",String.valueOf(year));
                selectedYear = year;
                selectedMonth = month+1;
                selectedDay = dayOfMonth;
                events.clear();
                listAdapter.notifyDataSetChanged();

                database.collection("Events").whereEqualTo("familyid",familyID).whereEqualTo("date",selected2Date).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e!= null){
                            Log.d("Fragment",e.getMessage());
                        }

                        for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                            if(doc.getType()== DocumentChange.Type.ADDED){
                                EventsModel event = doc.getDocument().toObject(EventsModel.class);
                                events.add(event);

                                listAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
        });


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}
