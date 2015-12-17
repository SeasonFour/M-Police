package com.example.ibra.newproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class AllViolations extends AppCompatActivity  {
    RecyclerView recyclerV;
    LinearLayoutManager layoutManager;
    List<ParseObject> obj;
    List<String> number_plate = new ArrayList<String>();
    List<String> description = new ArrayList<String>();
    List<String> owner = new ArrayList<String>();
    List<String> status = new ArrayList<String>();

    String numberP, descr,ownr,stats;

    CardView cardV;
    TextView tv_number_plate, tv_description,tv_owner, tv_status;

    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("mpolice", "before recycler");
        setContentView(R.layout.all_violations);

        recyclerV = (RecyclerView) findViewById(R.id.recycler_violations);
        layoutManager = new LinearLayoutManager(getBaseContext());
        recyclerV.setLayoutManager(layoutManager);
        recyclerV.setHasFixedSize(true);
        Log.d("mpolice", "after recycler");

        new getViolations().execute();
    }

    public class getViolations extends AsyncTask<Void, Void, Void> implements RecyclerViewListener {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AllViolations.this);
            pDialog.setMessage("Loading data");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
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
            recyclerV.setAdapter(new ViolationsAdapter(getApplicationContext(), number_plate, description, owner, status,this));
            pDialog.dismiss();
        }

        @Override
        public void recyclerViewClicked(View v, int position) {
            for (int i =0; i<obj.size();i++) {
                numberP = number_plate.get(position);
                descr = description.get(position);
                ownr = owner.get(position);
                stats = status.get(position);
                Log.d("mpolice", ""+number_plate.get(position));
                recyclerV.setAdapter(new DetailsAdapter(getApplicationContext(), numberP, descr, ownr, stats));
            }
        }
    }
}
