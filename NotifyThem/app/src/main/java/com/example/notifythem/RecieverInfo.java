package com.example.notifythem;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.notifythem.data.NotifyContract;
import com.example.notifythem.data.NotifyDbHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecieverInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;

    NotifyDbHelper mDbHelper;

    //private NotifyDbHelper mDbHelper;
    private NotiFormat mNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciever_info);



        Intent i=getIntent();
        mNotification=(NotiFormat)i.getSerializableExtra("SampleObject");
        //Toast.makeText(getApplicationContext(),notification.getTitle()+notification.getContent()+notification.getSendername(),Toast.LENGTH_SHORT).show();

        Spinner spinner1 = (Spinner) findViewById(R.id.Branch_Spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.branch_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);

        spinner1.setOnItemSelectedListener(this);

        Spinner spinner2 = (Spinner) findViewById(R.id.Year_Spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(this);




        Spinner spinner3 = (Spinner) findViewById(R.id.Section_Spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.section_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner3.setAdapter(adapter3);

        spinner3.setOnItemSelectedListener(this);


        Log.i("message","log message 11");


        mFirebaseDatabase=FirebaseDatabase.getInstance();




    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        if(parent.getId()==R.id.Branch_Spinner)
           mNotification.setBranch((String)parent.getItemAtPosition(pos));

        if(parent.getId()==R.id.Year_Spinner)
            mNotification.setYear((String)parent.getItemAtPosition(pos));

        if(parent.getId()==R.id.Section_Spinner)
            mNotification.setSection((String)parent.getItemAtPosition(pos));






    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void NotificationObjectMade(View view) {





        mMessageDatabaseReference=mFirebaseDatabase.getReference().child(mNotification.getBranch()).child(mNotification.getYear()).child(mNotification.getSection());


        mMessageDatabaseReference.push().setValue(mNotification);


        Toast.makeText(this,"Notification Sucessfully Sent",Toast.LENGTH_SHORT).show();
        //Intent i=new Intent(this,NotiMaker.class);
        //startActivity(i);

        InsertIntoSQLDatabase();

        Toast.makeText(this,"Notification sucessfully sent",Toast.LENGTH_SHORT).show();
        Intent i1=new Intent(this,NotiMaker.class);
        startActivity(i1);


    }

    private void InsertIntoSQLDatabase() {


        Log.i("message","log message this is here 10");

        ContentValues values = new ContentValues();
        int Database_branch = 1, Database_Year = 1, Database_Section = 1;
        if (mNotification.getBranch().equals("Computer Science"))
            Database_branch = NotifyContract.NotifyEntry.BRANCH_COMPUTER_SCIENCE;
        if (mNotification.getBranch().equals("Information Technology"))
            Database_branch = NotifyContract.NotifyEntry.BRANCH_INFORMATION_TECHNOLOGY;
        if (mNotification.getBranch().equals("Mechanical"))
            Database_branch = NotifyContract.NotifyEntry.BRANCH_MECHANICAL;
        if (mNotification.getBranch().equals("Electrical and Electronics"))
            Database_branch = NotifyContract.NotifyEntry.BRANCH_ELECTRICAL;
        if (mNotification.getBranch().equals("Electronics and Communication"))
            Database_branch = NotifyContract.NotifyEntry.BRANCH_ELECTRONICS;
        if (mNotification.getBranch().equals("BioTechnology"))
            Database_branch = NotifyContract.NotifyEntry.BRANCH_BIOTECHNOLOGY;


        Log.i("message","log message this is here 11");


        if (mNotification.getSection().equals("Section 1"))
            Database_Section = NotifyContract.NotifyEntry.SECTION_1;
        if (mNotification.getSection().equals("Section 2"))
            Database_Section = NotifyContract.NotifyEntry.SECTION_2;

        if (mNotification.getYear().equals("First Year"))
            Database_Year = NotifyContract.NotifyEntry.YEAR_1;
        if (mNotification.getYear().equals("Second Year"))
            Database_Year = NotifyContract.NotifyEntry.YEAR_2;
        if (mNotification.getYear().equals("Third Year"))
            Database_Year = NotifyContract.NotifyEntry.YEAR_3;
        if (mNotification.getYear().equals("Fourth Year"))
            Database_Year = NotifyContract.NotifyEntry.YEAR_4;


        Log.i("message","log message this is here 12");


        Toast.makeText(this,String.valueOf(Database_branch)+String.valueOf(Database_Year)+String.valueOf(Database_Section),Toast.LENGTH_LONG).show();

        Log.i("message","log message this is here 13");



        values.put(NotifyContract.NotifyEntry.COLUMN_NOTIFY_BRANCH,Database_branch);
        values.put(NotifyContract.NotifyEntry.COLUMN_NOTIFY_YEAR,Database_Year);
        values.put(NotifyContract.NotifyEntry.COLUMN_NOTIFY_SECTION,Database_Section);
        values.put(NotifyContract.NotifyEntry.COLUMN_NOTIFY_SENDERNAME,mNotification.getSendername());
        values.put(NotifyContract.NotifyEntry.COLUMN_NOTIFY_TITLE,mNotification.getTitle());
        values.put(NotifyContract.NotifyEntry.COLUMN_NOTIFY_CONTENT,mNotification.getContent());

        Log.i("message","log message 1");
        mDbHelper=new NotifyDbHelper(this);
        SQLiteDatabase db=mDbHelper.getWritableDatabase();

        Log.i("message","log message 3");
        db.insert(NotifyContract.NotifyEntry.TABLE_NAME,null,values);


        Log.i("message","log message this is here 4");

    }

}
