package com.example.sri.votingsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sri on 4/9/2017.
 */

public class votingactivity extends Fragment implements View.OnClickListener{

    private TextView ques;
    private DatabaseReference db, db1;
    EditText question;
    private FirebaseAuth fb;
    private Button b1, b2;
    PollQuestions adapter;
    ListView QuesList;
    //public List<String> PollQues;
    public static List<String> PollQues;
    public static ArrayList<Integer> FalseItems = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.d("Welcome to:","voting activity:on create view");
        return inflater.inflate(R.layout.votingactivity, container, false);
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        fb = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference("Poll");
        db1 = FirebaseDatabase.getInstance().getReference("Users");

        db1 = db1.child(fb.getCurrentUser().getUid());
        QuesList = (ListView) getView().findViewById(R.id.listviewques);
        PollQues = new ArrayList<>();

        db1.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User obj3 = dataSnapshot.getValue(User.class);
                /*if (!obj3.bool) {

                    int s1 = obj3.ques;
                    FalseItems.add(s1);
                    Log.d("in voting activity: ", FalseItems.toString()+obj3.bool);

                }
                */
                String inp = obj3.bool;
                Log.d("nah",inp);
                char inparray[] = inp.toCharArray();
                for(int i=0;i<inparray.length;i++){
                    if(inparray[i]>=49 && inparray[i]<=57){
                        if(inparray[i+1]=='f')
                            FalseItems.add(inparray[i]-48);
                    }
                }
                Log.d("in voting activity: ","doneediting"+FalseItems.toString());
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                PollQues.clear();

                for (DataSnapshot temp : dataSnapshot.getChildren()) {
                    String object1 = temp.getValue(String.class);
                    PollQues.add(object1);
                }
                if(getActivity()!=null){
                    adapter = new PollQuestions(getActivity(), PollQues);
                QuesList.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStart () {
        super.onStart();
    }


    @Override
    public void onClick (View v){

    }
}
