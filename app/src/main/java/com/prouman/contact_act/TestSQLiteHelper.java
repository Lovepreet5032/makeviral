package com.prouman.contact_act;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TestSQLiteHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 5;

    private static final String DATABASE_NAME = "testersingh";

    protected static final String TABLE_CONTACTS = "contact";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_CONTACT = "phone_number";
    public static final String KEY_TYPE = "phone_type";
    public static final String KEY_IMAGE = "contact_image";


    public TestSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
   /* String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("

            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"

            + KEY_IMAGE + " BLOB" + ")";
*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_CONTACT + " TEXT," + KEY_IMAGE + " BLOB," + KEY_TYPE + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }
}
