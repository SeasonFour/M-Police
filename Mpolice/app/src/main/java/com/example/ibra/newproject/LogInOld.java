package com.example.ibra.newproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LogInOld extends AppCompatActivity implements View.OnClickListener{
    @InjectView(R.id.badge_no)
    EditText et_badge;

    @InjectView(R.id.pin_no)
    EditText et_pin;

    @InjectView(R.id.login)
    Button login;

    @InjectView(R.id.request_pin)
    TextView request;

    String badgeNumber;
    String badgeList;

    List<ParseObject> obj;
    List<String> badgeNumbersList=  new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //ParseUtils.verifyParseConfig(this);


        setContentView(R.layout.activity_log_in);
        ButterKnife.inject(this);
        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        badgeNumber = et_badge.getText().toString();
        Log.e("LogIn", "Edit text number: " + badgeNumber);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.whereEqualTo("BadgeNumber", badgeNumber);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    Intent i = new Intent(LogInOld.this, SearchPlate.class);
                    startActivity(i);

                    int randomPIN = (int) ((Math.random() * 9000) + 1000);
                    String PINString = String.valueOf(randomPIN);

                    Log.d("LogIn", "Pin is: " + PINString);

                } else {
                    if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                        Toast.makeText(getBaseContext(), "Invalid badge number", Toast.LENGTH_SHORT).show();
                        Log.e("LogIn", "error obj not found");
                    } else {
                        //unknown error, debug
                    }

                }
            }
        });
        }

    }

