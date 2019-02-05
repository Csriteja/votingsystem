package com.example.sri.votingsystem;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.List;

import static com.example.sri.votingsystem.votingactivity.FalseItems;

/**
 * Created by Sri on 4/9/2017.
 */

public class PollQuestions extends ArrayAdapter<String> {

    private Activity context;
    private List<String> quesList;
    private DatabaseReference databaseReference,db;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();


    public PollQuestions(Activity context, List<String> quesList){
        super(context,R.layout.poll_layout,quesList);
        this.context=context;
        this.quesList=quesList;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d("Welcome to:","pollQuestions");
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.poll_layout, null, true);

        TextView Pollques = (TextView) listViewItem.findViewById(R.id.PollQuestion);

        String questemp = quesList.get(position);

        Pollques.setText(questemp);

        Button b1 = (Button) listViewItem.findViewById(R.id.choice_yes);

            b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        //get the row the clicked button is in
                        //LinearLayout vwParentRow = (LinearLayout)v.getParent();
                        Log.d("FalseItems", FalseItems.toString());
                        if (FalseItems.contains(position+1)) {
                            databaseReference = FirebaseDatabase.getInstance().getReference("Result");
                            //Toast.makeText(context, "You Clicked Yes " + position+1, Toast.LENGTH_SHORT).show();
                            final String presentQues=votingactivity.PollQues.get(position);
                            //Toast.makeText(context,presentQues,Toast.LENGTH_SHORT).show();
                            databaseReference.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    String a=dataSnapshot.getKey();
                                    //Log.d("debing:",a);
                                    String temp = a.replaceAll("^\"|\"$", "");
                                    //Log.d("debing:",a);
                                    if(temp.compareTo(presentQues)==0) {
                                        int count = dataSnapshot.getValue(Integer.class);
                                        count++;
                                        databaseReference.child(a).setValue(count);
                                    }
                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            db=FirebaseDatabase.getInstance().getReference("Users");
                            db=db.child(firebaseUser.getUid());
                            db.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot){
                                    User objtemp=dataSnapshot.getValue(User.class);
                                    String booltemp = objtemp.bool;
                                    char tempchararray[] = booltemp.toCharArray();
                                    for(int i=0;i<tempchararray.length;i++){
                                        if(tempchararray[i]-49==position)
                                            tempchararray[i+1] = 'g';
                                    }
                                    StringBuilder sb = new StringBuilder();
                                    for(int i=0;i<tempchararray.length;i++){
                                        sb.append(tempchararray[i]);
                                    }
                                    booltemp = sb.toString();
                                    User newobj = new User(objtemp.Age,objtemp.Name,booltemp);
                                    db.setValue(newobj);
                                    for(int i=0;i<votingactivity.FalseItems.size();i++){
                                        if(votingactivity.FalseItems.get(i)==position+1)
                                            votingactivity.FalseItems.remove(i);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }
                        else
                            Toast.makeText(context, "You cannot vote for this question " + position, Toast.LENGTH_SHORT).show();
                        //TextView child = (TextView)vwParentRow.getChildAt(0)
                /*Button b2 = (Button)v.findViewById(R.id.choice_no);
                if(v==b2){
                    Toast.makeText(context,"You Clicked No",Toast.LENGTH_LONG).show();
                }
                else if(v==b1){
                    Toast.makeText(context,"You Clicked Yes",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(context,"Fucked UP",Toast.LENGTH_LONG).show();
                    */
                    }

            });


        Button b2 = (Button)listViewItem.findViewById(R.id.choice_no);

            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //get the row the clicked button is in
                    //LinearLayout vwParentRow = (LinearLayout)v.getParent();
                    if (FalseItems.contains(position+1)) {
                        Toast.makeText(context, "Thanks for the response " + position, Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(context, "You cannot vote for this question " + position, Toast.LENGTH_SHORT).show();
                    //TextView child = (TextView)vwParentRow.getChildAt(0)
                /*Button b2 = (Button)v.findViewById(R.id.choice_no);
                if(v==b2){
                    Toast.makeText(context,"You Clicked No",Toast.LENGTH_LONG).show();
                }
                else if(v==b1){
                    Toast.makeText(context,"You Clicked Yes",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(context,"Fucked UP",Toast.LENGTH_LONG).show();
                    */
                }
            });


        return listViewItem;
    }



}
