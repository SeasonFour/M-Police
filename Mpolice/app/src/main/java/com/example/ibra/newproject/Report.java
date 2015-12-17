package com.example.ibra.newproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Report extends AppCompatActivity {
    EditText etViolationOther,etNumberPlate, etDescription,etLocation,etTime;
    RadioGroup violationType;
    ProgressDialog pDialog;

    String Violation,Number_Plate, Description, Location, Time;
    String violationOther;

    ParseObject obj = new ParseObject("Violations");
    ParseUser currentUser;

    private SimpleDateFormat myFormatter = new SimpleDateFormat("MMMM dd yyyy hh:mm aa");
    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            Toast.makeText(Report.this, myFormatter.format(date),Toast.LENGTH_SHORT).show();
            Time = myFormatter.format(date);
            etTime.setText(Time);
        }

        @Override
        public void onDateTimeCancel() {
            Toast.makeText(Report.this, "Cancelled",Toast.LENGTH_SHORT).show();
        }
    };

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


    public void typeOfViolation(View v) {
        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()) {
            case R.id.accidentBtn:
                if (checked)
                    Violation = "Accident";
                break;

            case R.id.hitrunBtn:
                if (checked)
                    Violation = "Hit and run";
                break;

            case R.id.stolenBtn:
                if (checked)
                    Violation = "Stolen";
                break;

        }
    }

    public void setTime(View v){
        new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(listener)
                .setInitialDate(new Date())
                .build()
                .show();
    }

    public void reportViolationClick(View v) {
        violationOther = etViolationOther.getText().toString().trim();
        Number_Plate = etNumberPlate.getText().toString().trim();
        Description = etDescription.getText().toString();
        Location = etLocation.getText().toString();
        Time = etTime.getText().toString();


        if (Violation.equals(null)){
            Toast.makeText(getApplicationContext(),"Cant be null" ,Toast.LENGTH_SHORT).show();

        }else{
            new reportViolation().execute();
        }
    }

    public class reportViolation extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Report.this);
            pDialog.setMessage("Reporting a violation...");
            pDialog.setIndeterminate(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            obj = new ParseObject("Violations");
            obj.put("Violation", Violation);
            obj.put("Number_plate", Number_Plate);
            obj.put("Description", Description);
            obj.put("Location", Location);
            obj.put("Time", Time);
            obj.saveEventually();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            obj.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(getBaseContext(),
                                "Reporting violation successful",
                                Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getBaseContext(),
                                "Error.Reporting violation up not successful",
                                Toast.LENGTH_LONG).show();
                    }

                }
            });

            pDialog.dismiss();
            /*etViolationOther.setText("");
            etNumberPlate.setText("");
            etDescription.setText("");
            etLocation.setText("");
            etTime.setText("");*/

        }

    }


}
