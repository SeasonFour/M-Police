package com.example.ibra.newproject;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search extends AppCompatActivity {
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
        setContentView(R.layout.search);


        recyclerV = (RecyclerView) findViewById(R.id.recycler_violations);
        layoutManager = new LinearLayoutManager(getBaseContext());
        recyclerV.setLayoutManager(layoutManager);
        recyclerV.setHasFixedSize(true);


        handleIntent(getIntent());
        Log.d("mpolice", "after handle intent");
    }

    public void doMySearch(String numberP){

        Pattern p = Pattern.compile("(([A-Z]{3})|([a-z]{3}))([\\s])?([1-9][\\d]{2})(([A-Z]{1})|([a-z]{1}))$");
        // Now create matcher object.
        Matcher m = p.matcher(numberP);
        if (m.find()){

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Violations");

            query.whereEqualTo("Number_plate", numberP);
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

            Log.d("mpolice", "before adapter");
            recyclerV.setAdapter(new MpoliceAdapter(getApplicationContext(),number_plate,description,owner,status));
            Log.d("mpolice", "after adapter");

        }else {
            Toast.makeText(getApplicationContext(), "Wrong License Plate!", Toast.LENGTH_LONG).show();
        }

    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);

        }

    }
}
