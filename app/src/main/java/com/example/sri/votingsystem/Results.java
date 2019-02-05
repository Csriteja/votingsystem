package com.example.sri.votingsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sri on 4/16/2017.
 */

public class Results extends Fragment implements View.OnClickListener{

    private TextView ques;
    private DatabaseReference db, db1;
    EditText question;
    private FirebaseAuth fb;
    ShowResults adapter;
    ListView ResultQues;
    List<String> ResultStrings;
    public static ArrayList<Integer> trueItems = new ArrayList<>();

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.resultlayout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fb = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference("Result");
        ResultQues = (ListView)getView().findViewById(R.id.resultlayoutques);
        ResultStrings = new ArrayList<>();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ResultStrings.clear();
                for(DataSnapshot temp : dataSnapshot.getChildren()) {
                    String s1 = temp.getKey();
                    int n = temp.getValue(Integer.class);
                    //if(!votingactivity.FalseItems.contains(n)) {
                    s1 = s1.replaceAll("^\"|\"$", "");
                    s1 = s1.concat(" : " + Integer.toString(n));
                    ResultStrings.add(s1);
                    //}
                    if (getActivity() != null) {
                        adapter = new ShowResults(getActivity(), ResultStrings);
                        ResultQues.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onClick(View v) {

    }
}
