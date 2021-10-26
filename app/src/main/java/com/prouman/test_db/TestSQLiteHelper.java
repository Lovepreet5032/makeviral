package com.prouman.test_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class TestSQLiteHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 5;

    private static final String DATABASE_NAME = "testersingh";

    protected static final String TABLE_CONTACTS = "contact";
    protected static final String TABLE_NOTIFICATION= "notification";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_DATE = "date";
    public static final String KEY_USER_IMAGE = "image";
    public static final String KEY_USER_ID = "user_Id";
    public static final String KEY_USER_NAME = "user_Name";
    public static final String KEY_ISREAD = "isRead";
    public static final String KEY_CONTACT = "phone_number";
//    public static final String KEY_TYPE = "phone_type";
    public static final String KEY_IMAGE = "contact_image";
    public static final String KEY_EMAIL_ONLINE = "email_online";
    public static final String KEY_MOBILE_ONLINE = "mobile_online";
    public static final String KEY_MOBILE_SHARED = "mobile_shared";
    public static final String KEY_EMAIL_SHARED = "email_shared";
    public static final String KEY_MOBILE_DEFAULT = "mobile_default";
    public static final String KEY_EMAIL_DEFAULT = "email_default";
    public static final String KEY_EMAIL_ID = "email_id";
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
                + KEY_CONTACT + " TEXT," + KEY_IMAGE + " BLOB," + KEY_EMAIL_ONLINE + " TEXT,"+KEY_MOBILE_ONLINE +" TEXT,"
                + KEY_MOBILE_DEFAULT + " TEXT,"+KEY_EMAIL_DEFAULT+" Text,"+KEY_EMAIL_SHARED+" Text,"
                +KEY_MOBILE_SHARED+" Text,"+KEY_EMAIL_ID+" Text)";
        db.execSQL(CREATE_CONTACTS_TABLE);

        String CREATE_NOTIFICATION_TABLE = "CREATE TABLE " + TABLE_NOTIFICATION + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT,"
                + KEY_MESSAGE + " TEXT," + KEY_DATE + " TEXT," + KEY_IMAGE + " TEXT,"+KEY_USER_ID +" TEXT,"
        +KEY_USER_NAME +" TEXT,"+KEY_ISREAD +" TEXT)";
                //+ KEY_MOBILE_DEFAULT + " TEXT,"+KEY_EMAIL_DEFAULT+" Text,"+KEY_EMAIL_SHARED+" Text,"
                //+KEY_MOBILE_SHARED+" Text,"+KEY_EMAIL_ID+" Text)";
        db.execSQL(CREATE_NOTIFICATION_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        onCreate(db);
    }
}
