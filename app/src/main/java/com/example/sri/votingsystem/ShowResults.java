package com.example.sri.votingsystem;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by Sri on 4/16/2017.
 */

public class ShowResults extends ArrayAdapter<String>{
    private Activity context;
    private List<String> quesList;
    private DatabaseReference databaseReference,db;



    public ShowResults(Activity context, List<String> quesList){
        super(context,R.layout.results,quesList);
        this.context=context;
        this.quesList=quesList;
    }

    public View getView(final int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.results,null,true);
        TextView result =(TextView)listViewItem.findViewById(R.id.results_question);
        String questemp = quesList.get(position);
        result.setText(questemp);


        return listViewItem;
    }
}
