package com.example.sri.votingsystem;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Sri on 4/11/2017.
 */

public class MessageDataSource {

    private static FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    static FirebaseUser user = firebaseAuth.getCurrentUser();
    private static DatabaseReference sRef=FirebaseDatabase.getInstance().getReference("Chats");
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddmmss");
    private static final String TAG = "MessageDataSource";
    private static final String COLUMN_TEXT = "Text";
    private static final String COLUMN_SENDER = "Sender";


    public static void saveMessage(Message message, String convoId){
        Date date = message.getDate();
        String key = sDateFormat.format(date);
        HashMap<String, String> msg = new HashMap<>();
        msg.put(COLUMN_TEXT, message.getText());
        msg.put(COLUMN_SENDER,user.getEmail());
        sRef.child(convoId).child(key).setValue(msg);
    }

    public static MessagesListener addMessagesListener(String convoId, final MessagesCallbacks callbacks){
        MessagesListener listener = new MessagesListener(callbacks);
        sRef.child(convoId).addChildEventListener(listener);
        return listener;

    }

    public static void stop(MessagesListener listener){
        sRef.removeEventListener(listener);
    }

    public static class MessagesListener implements ChildEventListener {
        private MessagesCallbacks callbacks;
        MessagesListener(MessagesCallbacks callbacks){
            this.callbacks = callbacks;
        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap<String,String> msg = (HashMap)dataSnapshot.getValue();
            Message message = new Message();
            message.setSender(msg.get(COLUMN_SENDER));
            message.setText(msg.get(COLUMN_TEXT));
            try {
                message.setDate(sDateFormat.parse(dataSnapshot.getKey()));
            }catch (Exception e){
                Log.d(TAG, "Couldn't parse date"+e);
            }
            if(callbacks != null){
                callbacks.onMessageAdded(message);
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
    }


    public interface MessagesCallbacks{
        public void onMessageAdded(Message message);
    }

}
