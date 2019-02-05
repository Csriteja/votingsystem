package com.example.sri.votingsystem;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Sri on 4/8/2017.
 */

public class updateactivity extends Fragment implements View.OnClickListener{

    private DatabaseReference databaseReference;
    private EditText fullname,age;
    private Button save;
    private TextView useremail;

    private FirebaseAuth firebaseAuth;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.updateactivity,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            getActivity().finish();
            //starting login activity
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();


        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        fullname=(EditText) getView().findViewById(R.id.UserFullName);
        age=(EditText)getView().findViewById(R.id.UserAge);
        save=(Button)getView().findViewById(R.id.SaveUserInfo);
        useremail=(TextView)getView().findViewById(R.id.textViewUserEmail);

        useremail.setText(user.getEmail());
        save.setOnClickListener(this);

    }

    private void saveUserInfo(){
        final String name= fullname.getText().toString().trim();
        final int userage=Integer.parseInt(age.getText().toString().trim());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("user id",firebaseAuth.getCurrentUser().getUid());
                if(dataSnapshot.hasChild(firebaseAuth.getCurrentUser().getUid())){
                    User temp = dataSnapshot.child(firebaseAuth.getCurrentUser().getUid()).getValue(User.class);
                    User obj = new User(userage,name,temp.bool);
                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(obj);
                }
                else{
                    User obj = new User(userage,name,"");
                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(obj);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Toast.makeText(getActivity(), "Profile Information Updated", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {
        if(TextUtils.isEmpty(fullname.getText()) || TextUtils.isEmpty(age.getText()))
            Toast.makeText(getActivity(), "Enter valid details!", Toast.LENGTH_LONG).show();
        if(v==save) {
            saveUserInfo();
            startActivity(new Intent(getActivity(), Profile2Activity.class));
        }
    }
}
