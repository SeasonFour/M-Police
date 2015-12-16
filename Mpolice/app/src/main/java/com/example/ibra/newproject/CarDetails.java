package com.example.ibra.newproject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class CarDetails extends AppCompatActivity {

    ProgressDialog pDialog;
    RecyclerView recyclerV;
    LinearLayoutManager layoutManager;
    List<ParseObject> obj;
    List<String> number_plate = new ArrayList<String>();
    List<String> description = new ArrayList<String>();
    List<String> owner = new ArrayList<String>();
    List<String> status = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("mpolice", "before recycler");
        setContentView(R.layout.car_details);


    }

    public class getViolations extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CarDetails.this);
            pDialog.setMessage("Loading data");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Violations");
            try {
                obj = query.find();

            } catch (ParseException e) {
                e.printStackTrace();
                Log.d("mpolice", "after query");
            }

            for (int i =0; i<obj.size();i++){
                number_plate.add(obj.get(i).getString("Number_plate"));
                description.add(obj.get(i).getString("Description"));
                owner.add(obj.get(i).getString("Location"));
                status.add(obj.get(i).getString("Violation"));
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerV.setAdapter(new DetailsAdapter(getApplicationContext(), number_plate, description, owner, status));
            pDialog.cancel();
        }
    }

}
