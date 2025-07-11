package com.example.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String USER_PREFS = "user_prefs";
    private static final String LOGIN_PREFS = "login_prefs";
    private static final String KEY_LOGGED_IN_USER = "logged_in_user";

    private final SharedPreferences userPrefs;
    private final SharedPreferences loginPrefs;
    private final SharedPreferences.Editor userEditor;
    private final SharedPreferences.Editor loginEditor;

    public SharedPrefManager(Context context) {
        userPrefs = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        loginPrefs = context.getSharedPreferences(LOGIN_PREFS, Context.MODE_PRIVATE);
        userEditor = userPrefs.edit();
        loginEditor = loginPrefs.edit();
    }

    public boolean isUsernameTaken(String username) {
        return userPrefs.contains(username);
    }

    public void saveUser(String username, String password) {
        userEditor.putString(username, password);
        userEditor.apply();
    }

    public boolean validate(String username, String password) {
        String stored = userPrefs.getString(username, null);
        return stored != null && stored.equals(password);
    }

    public void setLoggedInUser(String username) {
        loginEditor.putString(KEY_LOGGED_IN_USER, username);
        loginEditor.apply();
    }

    public String getLoggedInUser() {
        return loginPrefs.getString(KEY_LOGGED_IN_USER, null);
    }

    public boolean isLoggedIn() {
        return getLoggedInUser() != null;
    }

    public void logout() {
        loginEditor.remove(KEY_LOGGED_IN_USER);
        loginEditor.apply();
    }
}
