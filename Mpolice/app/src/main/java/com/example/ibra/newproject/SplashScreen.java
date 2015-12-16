package com.example.ibra.newproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ibra on 12/2/15.
 */
public class SplashScreen  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Thread mythread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);

                    Intent i = new Intent(getApplicationContext(), LogIn.class);

                    startActivity(i);

                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        mythread.start();
    }
}
