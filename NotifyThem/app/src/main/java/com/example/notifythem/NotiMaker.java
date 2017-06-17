package com.example.notifythem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class NotiMaker extends AppCompatActivity {
    EditText eTitle;
    EditText eBody;

    NotiFormat notificationBuilder;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static final int RC_SIGN_IN=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_maker);

        notificationBuilder=new NotiFormat();


        mFirebaseAuth=FirebaseAuth.getInstance();
        mAuthStateListener=new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                  notificationBuilder.setSendername(user.getDisplayName());




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

    public void NotifiAddedClicked(View view) {
        eTitle=(EditText)findViewById(R.id.title);
        eBody=(EditText)findViewById(R.id.body);





        if(eTitle.getText().toString().trim()!=null) {

            Log.i("message","log message this is here 10");

            notificationBuilder.setTitle(eTitle.getText().toString());
            notificationBuilder.setContent(eBody.getText().toString());




            Intent i=new Intent(this,RecieverInfo.class);
            i.putExtra("SampleObject",notificationBuilder);
            startActivity(i);
            Log.i("message","log message this is here 10");


        }
        else{
            Toast.makeText(getApplicationContext(),"Title cannot be empty",Toast.LENGTH_SHORT).show();
        }
    }

    public void SendOrSent(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.SendNotiButton:
                i=new Intent(this,NotiMaker.class);
                startActivity(i);
                break;
            case R.id.SentNotiButton:
                i=new Intent(this,SentNotifications.class);
                startActivity(i);


        }
    }
}
