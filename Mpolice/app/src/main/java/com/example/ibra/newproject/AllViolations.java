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

import com.michaldrabik.tapbarmenulib.TapBarMenu;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @Bind(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;

    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("mpolice", "before recycler");
        setContentView(R.layout.all_violations);
        ButterKnife.bind(this);

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
            Log.d("mpolice", "clicked: "+number_plate.get(position));
            for (int i =0; i<obj.size();i++) {
                numberP = number_plate.get(position);
                descr = description.get(position);
                ownr = owner.get(position);
                stats = status.get(position);
                Log.d("mpolice", "clicked: "+number_plate.get(position));
                recyclerV.setAdapter(new DetailsAdapter(getApplicationContext(), numberP, descr, ownr, stats));
            }
        }
    }

    @OnClick(R.id.tapBarMenu) public void onMenuButtonClick() {
        tapBarMenu.toggle();
    }

    @OnClick({ R.id.item1, R.id.item2, R.id.item3, R.id.item4 }) public void onMenuItemClick(View view) {
        tapBarMenu.close();
        switch (view.getId()) {
            case R.id.item1:
                Intent i = new Intent(AllViolations.this,Report.class);
                startActivity(i);
                break;
            case R.id.item2:
                Intent i2 = new Intent(AllViolations.this,Search.class);
                startActivity(i2);
                Log.i("TAG", "Item 2 selected");
                break;
            case R.id.item3:
                Intent i3 = new Intent(AllViolations.this,LogIn.class);
                startActivity(i3);
                Log.i("TAG", "Item 3 selected");
                break;
            case R.id.item4:
                Log.i("TAG", "Item 4 selected");
                Intent i4 = new Intent(AllViolations.this,AllViolations.class);
                startActivity(i4);
                break;
        }
    }
}
