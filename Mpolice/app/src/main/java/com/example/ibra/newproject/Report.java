package com.example.ibra.newproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class Report extends AppCompatActivity {
    EditText etViolationOther,etNumberPlate, etDescription,etLocation,etTime;
    RadioGroup violationType;

    String Violation,Number_Plate, Description, Location, Time;

    ParseObject obj = new ParseObject("Violations");
    ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Report a violation");
        setSupportActionBar(toolbar);

        etViolationOther = (EditText) findViewById(R.id.violationOther);
        etNumberPlate = (EditText) findViewById(R.id.numberPlate);
        etDescription = (EditText) findViewById(R.id.description);
        etLocation = (EditText) findViewById(R.id.location);
        etTime = (EditText) findViewById(R.id.time);

        currentUser = ParseUser.getCurrentUser();
        Log.d("Current user", ""+currentUser);

    }

    public void typeOfViolation(View v){
        /*Intent i = new Intent(Report.this, LogIn.class);
        startActivity(i);*/
    }

    public void reportViolation(View v){
        Violation = etViolationOther.getText().toString().trim();
        Number_Plate = etNumberPlate.getText().toString().trim();
        Description = etDescription.getText().toString();
        Location = etLocation.getText().toString();
        Time = etTime.getText().toString();

        obj.put("Violation", Violation);
        obj.put("Number_plate", Number_Plate);
        obj.put("Description", Description);
        obj.put("Location", Location);
        obj.put("Time", Time);
        obj.saveEventually();

        obj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Toast.makeText(getBaseContext(),
                            "Reporting violation successful",
                            Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),AllViolations.class);
                    startActivity(i);
                }else {
                    Toast.makeText(getBaseContext(),
                            "Error.Reporting violation up not successful",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
