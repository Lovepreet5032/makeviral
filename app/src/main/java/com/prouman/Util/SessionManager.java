package com.prouman.Util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.prouman.activity.MemberLoginActivity;
import com.prouman.model.ContactVO;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jcs on 10/23/2016.
 */

public class SessionManager {
    public static final String KEY_FNAME = "keyfname";
    public static final String KEY_LNAME = "keylname";
    public static final String KEY_IMAGENAME = "image_name";
    public static final String KEY_UPRO_REF = "upro_ref";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_UPRO_AFFLINK = "affLink";
    public static final String KEY_UPRO_SHOPLINK ="shoplink" ;
    public static final String KEY_EMAIL_ID ="email_id" ;
    public static final String UPRO_WEBURL = "weburl";
//    public static ArrayList<ContactVO> eContactVOList=new ArrayList<>();
    public static ArrayList<ContactVO> ContContactVOList=new ArrayList<>();
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    public static final String PREF_NAME = "NinjaApp";

    // All Shared Preferences Keys
    public static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String UPRO_ID = "upro_id";
    public static final String GUEST_UPRO_ID = "upro_id";
    public static final String KEY_HASH = "hash";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void setPreferences(Context context, String key, String value) {

         editor = context.getSharedPreferences("NinjaApp", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();

    }
    public  String getPreferences(Context context, String key) {
        pref = context.getSharedPreferences("NinjaApp",	Context.MODE_PRIVATE);
        String position = pref.getString(key, "");
        return position;
    }
    public void setContactUpdateDateTime(String value) {
        editor.putString("Updated_at", value);
        editor.commit();

    }
    public  String getContactUpdateDateTime() {
        String position = pref.getString("Updated_at", "");
        return position;
    }
    public void setGCMToken(String value) {
        editor.putString("Token", value);
        editor.commit();

    }
    public  String getGCMToken() {
        String position = pref.getString("Token", "");
        return position;
    }
    public void setIsTokenRegister(boolean value) {
        editor.putBoolean("isTokenRecived", value);
        editor.commit();

    }
    public  boolean getIsTokenRegister() {
        boolean position = pref.getBoolean("isTokenRecived",false);
        return position;
    }
    public void createLoginSession(String name, String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
       // editor.putString(KEY_EMAIL, email);

        // commit changes
        editor.commit();
    }
    public void createMemberLoginSession(String firstName, String lastName, String phone, String imgName,
                                         String upro_id, String websiteUrl, String hash, String affLink, String shopLink, String emailId) {

        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_FNAME, firstName);

        // Storing email in pref
        editor.putString(KEY_LNAME, lastName);
        // Storing name in pref
        editor.putString(KEY_PHONE, phone);

        // Storing email in pref
        editor.putString(KEY_IMAGENAME , imgName);
        // Storing name in pref
        editor.putString(UPRO_ID, upro_id);

        // Storing email in pref
        editor.putString(UPRO_WEBURL, websiteUrl);
        // Storing name in pref
        editor.putString(KEY_HASH, hash);

        // Storing email in pref
       editor.putString(KEY_UPRO_AFFLINK, affLink);
        editor.putString(KEY_UPRO_SHOPLINK, shopLink);
        editor.putString(KEY_EMAIL_ID,emailId);
        // commit changes
        editor.commit();

    }
    public HashMap<String, String> getMemberDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(UPRO_ID, pref.getString(UPRO_ID, null));
        user.put(KEY_FNAME, pref.getString(KEY_FNAME, null));
        user.put(KEY_LNAME, pref.getString(KEY_LNAME, null));
        // user email id
        user.put(KEY_PHONE, pref.getString(UPRO_ID, null));


        user.put(KEY_IMAGENAME, pref.getString(KEY_IMAGENAME, null));
        user.put(KEY_UPRO_REF, pref.getString(KEY_UPRO_REF, null));
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));

        user.put(KEY_UPRO_AFFLINK, pref.getString(KEY_UPRO_AFFLINK, null));

        user.put(KEY_UPRO_SHOPLINK, pref.getString(KEY_UPRO_SHOPLINK, null));
        user.put(KEY_EMAIL_ID, pref.getString(KEY_EMAIL_ID, null));
        // user email id
        user.put(UPRO_WEBURL, pref.getString(UPRO_WEBURL, null));
        user.put(KEY_HASH, pref.getString(KEY_HASH, null));

        // return user
        return user;
    }
    public HashMap<String, String> getGuestDetails(){

        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(UPRO_ID, pref.getString(UPRO_ID, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(UPRO_ID, pref.getString(UPRO_ID, null));

        user.put(UPRO_ID, pref.getString(UPRO_ID, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(UPRO_ID, pref.getString(UPRO_ID, null));
        // return user
        return user;
    }
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MemberLoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }
        public void logoutUser(){
            // Clearing all data from Shared Preferences
            editor.clear();
            editor.commit();

            // After logout redirect user to Loing Activity
            Intent i = new Intent(_context, MemberLoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
    public String getString(String key, String defaultValue) {
        return pref.getString(key, defaultValue);
    }
    public void put(String key, String val) {
       // doEdit();
        editor.putString(key, val);
        editor.commit();
    }
}
