package com.example.notifyme;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Notification extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static final int RC_SIGN_IN=1;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mNotifyDatabaseReference;
    private ChildEventListener mChildEventListener;

    private  CustomAdapter mNotificationAdapter;

    private ListView mNotificationListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mNotificationListView=(ListView)findViewById(R.id.NotificationListView);

        Intent i=getIntent();
        StudentInfo student=(StudentInfo)i.getSerializableExtra("SampleObject");



        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mNotifyDatabaseReference=mFirebaseDatabase.getReference().child(student.getBranch()).child(student.getYear()).child(student.getSection());

        List<NotiFormat> notificationList=new ArrayList<>();
        mNotificationAdapter=new CustomAdapter(this,R.layout.custom_row,notificationList);
        mNotificationListView.setAdapter(mNotificationAdapter);


        TextView NameTextview=(TextView)findViewById(R.id.NameTextview);
        NameTextview.setText("Hello "+student.getUsername());

        TextView InfoTextView=(TextView)findViewById(R.id.InfoTextview);
        InfoTextView.setText("The Notifications of "+student.getYear()+" "+student.getBranch()+" "+student.getSection()+" are as follows");

        mFirebaseAuth=FirebaseAuth.getInstance();
        mAuthStateListener=new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    OnSignedInInitialize();
                }
                else{

                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN
                    );
                }
            }
        };


    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(mAuthStateListener!=null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        detachDatabaseReadListener();
        mNotificationAdapter.clear();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator=getMenuInflater();
        inflator.inflate(R.menu.main_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void OnSignedInInitialize(){
        if(mChildEventListener==null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    NotiFormat notification=dataSnapshot.getValue(NotiFormat.class);
                    mNotificationAdapter.add(notification);

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
            };

        }
        mNotifyDatabaseReference.addChildEventListener(mChildEventListener);
    }

    private void detachDatabaseReadListener() {
        if (mChildEventListener!=null){
            mNotifyDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener=null;
        }
    }


}
