package com.example.ibra.newproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Report extends AppCompatActivity {
    EditText etViolationOther,etNumberPlate, etDescription,etLocation,etTime;
    ProgressDialog pDialog;
    RadioGroup radioGroup;

    String Violation,Number_Plate, Description, Location, Time;
    String violationOther;
    TextInputLayout textInputLayout;

    ParseObject obj = new ParseObject("Violations");
    ParseUser currentUser;

    @Bind(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;

    private SimpleDateFormat myFormatter = new SimpleDateFormat("MMMM dd yyyy hh:mm aa");
    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            //Toast.makeText(Report.this, myFormatter.format(date),Toast.LENGTH_SHORT).show();
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
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Report a violation");

        setSupportActionBar(toolbar);



        etViolationOther = (EditText) findViewById(R.id.violationOther);
        etNumberPlate = (EditText) findViewById(R.id.numberPlate);
        etDescription = (EditText) findViewById(R.id.description);
        etLocation = (EditText) findViewById(R.id.location);
        etTime = (EditText) findViewById(R.id.time);
        textInputLayout = (TextInputLayout) findViewById(R.id.input_other);
        radioGroup = (RadioGroup) findViewById(R.id.radio);

        textInputLayout.setVisibility(View.INVISIBLE);

        currentUser = ParseUser.getCurrentUser();
        Log.d("Current user", "" + currentUser);
    }


    public void typeOfViolation(View v) {
        // (radioGroup.getCheckedRadioButtonId() == -1)
            /*Toast.makeText(getBaseContext(),
                    "Please enter the violation type",
                    Toast.LENGTH_LONG).show();*/

            boolean checked = ((RadioButton) v).isChecked();
            switch (v.getId()) {
                case R.id.accidentBtn:
                    if (checked)
                        textInputLayout.setVisibility(v.INVISIBLE);
                    Violation = "Accident";
                    break;
                case R.id.hitrunBtn:
                    if (checked)
                        textInputLayout.setVisibility(v.INVISIBLE);
                    Violation = "Hit and run";
                    break;
                case R.id.stolenBtn:
                    if (checked)
                        textInputLayout.setVisibility(v.INVISIBLE);
                    Violation = "Stolen";
                    break;
                case R.id.otherBtn:
                    if (checked)
                        textInputLayout.setVisibility(v.VISIBLE);
                    break;
                default:
                    Toast.makeText(getBaseContext(),
                            "Please enter the violation type",
                            Toast.LENGTH_LONG).show();
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

        if (radioGroup.getCheckedRadioButtonId() == -1){
            Intent i = new Intent(Report.this,Report.class);
            startActivity(i);
        }else{
        violationOther = etViolationOther.getText().toString().trim();
        Number_Plate = etNumberPlate.getText().toString().trim();
        Description = etDescription.getText().toString();
        Location = etLocation.getText().toString();
        Time = etTime.getText().toString();

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
            try {
                obj.put("Violation", Violation);
                obj.put("Number_plate", Number_Plate);
                obj.put("Description", Description);
                obj.put("Location", Location);
                obj.put("Time", Time);
                obj.put("Other",violationOther);
            }catch (Exception e){
                Log.e("mpolice","exception",e);
                Log.e("mpolice", "Exception: "+Log.getStackTraceString(e));
            }
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
        }
    }

    @OnClick(R.id.tapBarMenu) public void onMenuButtonClick() {
        tapBarMenu.toggle();
    }

    @OnClick({ R.id.item1, R.id.item2, R.id.item3, R.id.item4 }) public void onMenuItemClick(View view) {
        tapBarMenu.close();
        switch (view.getId()) {
            case R.id.item1:
                Intent i = new Intent(Report.this,Report.class);
                startActivity(i);
                break;
            case R.id.item2:
                Intent i2 = new Intent(Report.this,Search.class);
                startActivity(i2);
                Log.i("TAG", "Item 2 selected");
                break;
            case R.id.item3:
                Intent i3 = new Intent(Report.this,LogIn.class);
                startActivity(i3);
                Log.i("TAG", "Item 3 selected");
                break;
            case R.id.item4:
                Log.i("TAG", "Item 4 selected");
                Intent i4 = new Intent(Report.this,AllViolations.class);
                startActivity(i4);
                break;
        }
    }
}
