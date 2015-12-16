package com.example.ibra.newproject;

import android.app.Application;
import android.util.Log;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

import static com.digits.sdk.android.Digits.authenticate;

/**
 * Created by ruot on 12/6/15.
 */
public class AppConfig extends Application {
    private AuthCallback authCallback;

    private static final String TWITTER_KEY = "fHPv5bbQufPqFzSdNfQfjVpRL";
    private static final String TWITTER_SECRET = "Xv3EhmTIz2PCBbVFlzZGzRtWqw9l5kfU6nJDUw3DxNRUYa2kXV";

    public static final String PARSE_CHANNEL = "GET PIN";
    public static final int NOTIFICATION_ID = 1000;
    public static final String PARSE_APPLICATION_ID ="7uE5i8t1LBqdC3f5lmlsuCOaXgrZR4iBDtHXtVCO";
    public static final String PARSE_CLIENT_KEY ="Uqp5HP8zWmKJB8S5N6EzhDzdL8rW6EVZ9emkEAeP";

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig =  new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Digits(), new Twitter(authConfig));

        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        authCallback = new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // Do something with the session
                authenticate(authCallback, "+254706392936");
                authenticate(authCallback, android.R.style.Theme_Material, "+254706392936", true);
            }

            @Override
            public void failure(DigitsException exception) {
                // Do something on failure
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        };

      /*  Parse.initialize(this, "zhqsKp7mr7xAXtnpEpb4kNYttvbnzFt7tgjetnex", "Awpvq61ZwXOhvz2UYiybXaZcEMwpqCGvxTfKT86I");
        ParseInstallation.getCurrentInstallation().saveInBackground();*/

    }

    public AuthCallback getAuthCallback(){
        return authCallback;
    }
}
