package com.example.ibra.newproject;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by ibra on 12/17/15.
 */
public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.example.ibra.newproject.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
