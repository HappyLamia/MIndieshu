package com.orion.darmawan.eclinic.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.orion.darmawan.eclinic.Model.ModelData;

public class SharedPrefManager  {
    //the constants
    private static final String SHARED_PREF_NAME = "indieshupref";
    private static final String KEY_USERNAME = "keyname";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_ID = "keyid";
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(ModelData user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getName());
        editor.putString(KEY_GENDER, user.getGender());
        editor.apply();
    }


    //this method will give the logged in user
    public ModelData getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new ModelData(
                sharedPreferences.getString(KEY_ID, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_GENDER, null)
        );
    }

}

