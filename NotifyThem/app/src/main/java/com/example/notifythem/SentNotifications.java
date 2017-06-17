package com.example.notifythem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.example.notifythem.data.NotifyContract;
import com.example.notifythem.data.NotifyDbHelper;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class SentNotifications extends AppCompatActivity {


    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static final int RC_SIGN_IN=1;


    private CustomAdapter mCustomAdapter;
    private ListView mListView;

    private NotifyDbHelper mDbHelper;

    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_notifications);

        mListView=(ListView)findViewById(R.id.Database_ListView);

        mUsername="Anonymous";

        mFirebaseAuth=FirebaseAuth.getInstance();

        mAuthStateListener=new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    InitializeUsername(user.getDisplayName());
                    CreateList();
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

    private void InitializeUsername(String displayName) {
        Log.i("is disply name correct",displayName);
        mUsername=displayName;
    }

    private void CreateList() {

        mDbHelper=new NotifyDbHelper(this);
        SQLiteDatabase db=mDbHelper.getReadableDatabase();

        String[] projections={
                NotifyContract.NotifyEntry.COLUMN_NOTIFY_BRANCH,
                NotifyContract.NotifyEntry.COLUMN_NOTIFY_TITLE,
                NotifyContract.NotifyEntry.COLUMN_NOTIFY_YEAR,
                NotifyContract.NotifyEntry.COLUMN_NOTIFY_SECTION,
                NotifyContract.NotifyEntry.COLUMN_NOTIFY_CONTENT,
                NotifyContract.NotifyEntry.COLUMN_NOTIFY_SENDERNAME
        };
        Cursor cursor=db.query(NotifyContract.NotifyEntry.TABLE_NAME,projections,null,null,null,null,null);


        Log.i("message","log message 5");





        try{
            NotiFormat mNotiFormat;
            List<NotiFormat> mNotificationList=new ArrayList<>();
            cursor.moveToLast();
            do {
                mNotiFormat=new NotiFormat();
                mNotiFormat.setBranchThroughInt(cursor.getInt(cursor.getColumnIndex(NotifyContract.NotifyEntry.COLUMN_NOTIFY_BRANCH)));

                mNotiFormat.setSectionThroughInt(cursor.getInt(cursor.getColumnIndex(NotifyContract.NotifyEntry.COLUMN_NOTIFY_SECTION)));
                mNotiFormat.setYearThroughInt(cursor.getInt(cursor.getColumnIndex(NotifyContract.NotifyEntry.COLUMN_NOTIFY_YEAR)));
                mNotiFormat.setTitle(cursor.getString(cursor.getColumnIndex(NotifyContract.NotifyEntry.COLUMN_NOTIFY_TITLE)));
                mNotiFormat.setContent(cursor.getString(cursor.getColumnIndex(NotifyContract.NotifyEntry.COLUMN_NOTIFY_CONTENT)));
                mNotiFormat.setSendername(cursor.getString(cursor.getColumnIndex(NotifyContract.NotifyEntry.COLUMN_NOTIFY_SENDERNAME)));

                Log.i("message",mNotiFormat.getBranch()+mNotiFormat.getSection()+mNotiFormat.getYear());
                Log.i("message", mUsername+"  "+mNotiFormat.getSendername());
                if(mUsername.equals(mNotiFormat.getSendername())) {
                    mNotificationList.add(mNotiFormat);
                }

            }while(cursor.moveToPrevious());

            mCustomAdapter=new CustomAdapter(this,R.layout.custom_row,mNotificationList);

            mListView.setAdapter(mCustomAdapter);



        }
        finally {
            cursor.close();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.main_menu,menu);
        return true;
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

    }
}
