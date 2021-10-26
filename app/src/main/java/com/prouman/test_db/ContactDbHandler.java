package com.prouman.test_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.prouman.model.NotifcationdataObj;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jcs on 11/22/2016.
 */

public class ContactDbHandler {
    private TestSQLiteHelper dbHelper;

    public ContactDbHandler(Context context) {
        dbHelper = new TestSQLiteHelper(context);
    }


    public int addContact(ContactTest phoneBook) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TestSQLiteHelper.KEY_ID,phoneBook.getId());
        values.put(TestSQLiteHelper.KEY_NAME, phoneBook.getName());
        values.put(TestSQLiteHelper.KEY_CONTACT, phoneBook.getPhone());
        values.put(TestSQLiteHelper.KEY_EMAIL_ONLINE, phoneBook.getEmail_mOnline());
        values.put(TestSQLiteHelper.KEY_MOBILE_ONLINE, phoneBook.getMobile_mOnline());
        values.put(TestSQLiteHelper.KEY_IMAGE, phoneBook.getContactImage());
        values.put(TestSQLiteHelper.KEY_MOBILE_DEFAULT,phoneBook.getMobile_defaut());
        values.put(TestSQLiteHelper.KEY_EMAIL_DEFAULT,phoneBook.getEmail_defaut());
        values.put(TestSQLiteHelper.KEY_EMAIL_SHARED,phoneBook.getMobileShared());
        values.put(TestSQLiteHelper.KEY_MOBILE_SHARED,phoneBook.getEmailShared());
        values.put(TestSQLiteHelper.KEY_EMAIL_ID,phoneBook.getEmail_Id());
        long insertId = db.insert(TestSQLiteHelper.TABLE_CONTACTS, null, values);
        db.close();
        return (int)insertId;
    }

    public int addNotification(NotifcationdataObj notificationData) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TestSQLiteHelper.KEY_TITLE,notificationData.getStr_tittle());
        values.put(TestSQLiteHelper.KEY_MESSAGE, notificationData.getStr_description());
        values.put(TestSQLiteHelper.KEY_DATE, notificationData.getStr_date());
        values.put(TestSQLiteHelper.KEY_IMAGE, notificationData.getStr_userimage());
        values.put(TestSQLiteHelper.KEY_USER_ID, notificationData.getStr_fromuser());
        values.put(TestSQLiteHelper.KEY_USER_NAME, notificationData.getStr_username());
        values.put(TestSQLiteHelper.KEY_ISREAD, "0");
        long insertId = db.insert(TestSQLiteHelper.TABLE_NOTIFICATION, null, values);
        db.close();
        return (int)insertId;
    }

    public List<NotifcationdataObj> getAllNotification() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<NotifcationdataObj> phoneBookList = new ArrayList<NotifcationdataObj>();
        String selectQuery = "SELECT  * FROM " + TestSQLiteHelper.TABLE_NOTIFICATION +" ORDER BY "+ TestSQLiteHelper.KEY_ID +" ASC";

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                NotifcationdataObj phoneBook = new NotifcationdataObj();
                phoneBook.setNotification_id(cursor.getInt(0));
                phoneBook.setStr_tittle(cursor.getString(1));
                phoneBook.setStr_description(cursor.getString(2));
                phoneBook.setStr_date(cursor.getString(3));
                phoneBook.setStr_userimage(cursor.getString(4));
                phoneBook.setStr_fromuser(cursor.getString(5));
                phoneBook.setStr_username(cursor.getString(6));
                phoneBook.setStr_isread(cursor.getString(7));
                phoneBookList.add(phoneBook);
            } while (cursor.moveToNext());
        }

        return phoneBookList;
    }

    public int getUnreadNotification() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<NotifcationdataObj> phoneBookList = new ArrayList<NotifcationdataObj>();
//        String selectQuery =
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TestSQLiteHelper.TABLE_NOTIFICATION +" where isRead = ?",new String[]{String.valueOf("0")});
       // Cursor cursor = db.rawQuery(selectQuery, null);


//        if (cursor.moveToFirst()) {
//            do {
//                NotifcationdataObj phoneBook = new NotifcationdataObj();
//                phoneBook.setNotification_id(cursor.getInt(0));
//                phoneBook.setStr_tittle(cursor.getString(1));
//                phoneBook.setStr_description(cursor.getString(2));
//                phoneBook.setStr_date(cursor.getString(3));
//                phoneBook.setStr_userimage(cursor.getString(4));
//                phoneBook.setStr_fromuser(cursor.getString(5));
//                phoneBook.setStr_username(cursor.getString(6));
//                phoneBook.setStr_isread(cursor.getString(7));
//                phoneBookList.add(phoneBook);
//            } while (cursor.moveToNext());
    //    }

        return cursor.getCount();
    }
    public NotifcationdataObj getNotification(int id) {
        NotifcationdataObj phoneBook=null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
    //      String query= "SELECT  * FROM " + dbHelper.TABLE_NOTIFICATION +" WHERE "+dbHelper.KEY_ID+"=+'id'";
        Cursor cursor = db.rawQuery("Select * from "+ TestSQLiteHelper.TABLE_NOTIFICATION +" where id = ?", new String[] {String.valueOf(id)});
//        String query="SELECT * FROM " + dbHelper.TABLE_CONTACTS +" WHERE " + dbHelper.KEY_ID+"="+"\'"+id+"\'";
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                phoneBook = new NotifcationdataObj();
                phoneBook.setNotification_id(cursor.getInt(0));
                phoneBook.setStr_tittle(cursor.getString(1));
                phoneBook.setStr_description(cursor.getString(2));
                phoneBook.setStr_date(cursor.getString(3));
                phoneBook.setStr_userimage(cursor.getString(4));
                phoneBook.setStr_fromuser(cursor.getString(5));
                phoneBook.setStr_username(cursor.getString(6));
                phoneBook.setStr_isread(cursor.getString(7));
            }
        }
        return phoneBook;
    }
    public int updateNotification(int id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TestSQLiteHelper.KEY_ISREAD, "1");

        return db.update(TestSQLiteHelper.TABLE_NOTIFICATION, values, TestSQLiteHelper.KEY_ID + " = ?",
                new String[] {String.valueOf(id) });
    }
    public ContactTest getContact(String id) {
        ContactTest phoneBook=null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query(TestSQLiteHelper.TABLE_CONTACTS,new String[]{TestSQLiteHelper.KEY_ID,
                        TestSQLiteHelper.KEY_NAME, TestSQLiteHelper.KEY_CONTACT, TestSQLiteHelper.KEY_IMAGE,
                        TestSQLiteHelper.KEY_EMAIL_ONLINE, TestSQLiteHelper.KEY_MOBILE_ONLINE, TestSQLiteHelper.KEY_MOBILE_DEFAULT
        , TestSQLiteHelper.KEY_EMAIL_DEFAULT, TestSQLiteHelper.KEY_EMAIL_SHARED, TestSQLiteHelper.KEY_MOBILE_SHARED
                , TestSQLiteHelper.KEY_EMAIL_ID}, TestSQLiteHelper.KEY_ID +"=?",
                new String[] {String.valueOf(id)}, null, null, null, null);
//        String query="SELECT * FROM " + dbHelper.TABLE_CONTACTS +" WHERE " + dbHelper.KEY_ID+"="+"\'"+id+"\'";
      if (cursor.getCount() > 0) {
        while (cursor.moveToNext()) {
                    phoneBook = new ContactTest();
                    phoneBook.setId(cursor.getString(0));
                    phoneBook.setName(cursor.getString(1));
                    phoneBook.setPhone(cursor.getString(2));
                    phoneBook.setContactImage(cursor.getBlob(3));
                    phoneBook.setEmail_mOnline(cursor.getString(4));
                    phoneBook.setMobile_mOnline(cursor.getString(5));
                    phoneBook.setMobile_defaut(cursor.getString(6));
                    phoneBook.setEmail_defaut(cursor.getString(7));
                    phoneBook.setEmailShared(cursor.getString(8));
                    phoneBook.setMobileShared(cursor.getString(9));
                    phoneBook.setEmail_Id(cursor.getString(10));
                 }
        }
        return phoneBook;
    }

    public List<ContactTest> getDefaultPlusConnectedContacts(int fromEmail) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<ContactTest> phoneBookList = new ArrayList<ContactTest>();
        String selectQuery;// = "SELECT  * FROM " + dbHelper.TABLE_CONTACTS +" ORDER BY "+dbHelper.KEY_NAME+" ASC";
        if(fromEmail==1) {
            selectQuery = "SELECT  * FROM " + TestSQLiteHelper.TABLE_CONTACTS +" WHERE " +"("+ TestSQLiteHelper.KEY_EMAIL_ONLINE +"="+"1"
                    +" OR "+ TestSQLiteHelper.KEY_EMAIL_DEFAULT +"="+"1"+")"+" AND "+ TestSQLiteHelper.KEY_EMAIL_ID +"!=1"+" ORDER BY "+ TestSQLiteHelper.KEY_NAME +" ASC";
        }
        else{
            selectQuery = "SELECT  * FROM " + TestSQLiteHelper.TABLE_CONTACTS +" WHERE " + TestSQLiteHelper.KEY_MOBILE_ONLINE +"="+"1"
                    +" OR "+ TestSQLiteHelper.KEY_MOBILE_DEFAULT +"="+"1"+" ORDER BY "+ TestSQLiteHelper.KEY_NAME +" ASC";
        }
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                ContactTest phoneBook = new ContactTest();
                phoneBook.setId(cursor.getString(0));
                phoneBook.setName(cursor.getString(1));
                phoneBook.setPhone(cursor.getString(2));
                phoneBook.setContactImage(cursor.getBlob(3));
                phoneBook.setEmail_mOnline(cursor.getString(4));
                phoneBook.setMobile_mOnline(cursor.getString(5));
                phoneBook.setMobile_defaut(cursor.getString(6));
                phoneBook.setEmail_defaut(cursor.getString(7));
                phoneBook.setEmailShared(cursor.getString(8));
                phoneBook.setMobileShared(cursor.getString(9));
                phoneBook.setEmail_Id(cursor.getString(10));
                if(fromEmail!=1){
                    if(!phoneBook.getPhone().equals("")){
                phoneBookList.add(phoneBook);}
                }
                else
                {
                    phoneBookList.add(phoneBook);
                }
            } while (cursor.moveToNext());
        }

        return phoneBookList;
    }
    public List<ContactTest> getAllContacts() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<ContactTest> phoneBookList = new ArrayList<ContactTest>();
        String selectQuery = "SELECT  * FROM " + TestSQLiteHelper.TABLE_CONTACTS +" ORDER BY "+ TestSQLiteHelper.KEY_NAME +" ASC";

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                ContactTest phoneBook = new ContactTest();
                phoneBook.setId(cursor.getString(0));
                phoneBook.setName(cursor.getString(1));
                phoneBook.setPhone(cursor.getString(2));
                phoneBook.setContactImage(cursor.getBlob(3));
                phoneBook.setEmail_mOnline(cursor.getString(4));
                phoneBook.setMobile_mOnline(cursor.getString(5));
                phoneBook.setMobile_defaut(cursor.getString(6));
                phoneBook.setEmail_defaut(cursor.getString(7));
                phoneBook.setEmailShared(cursor.getString(8));
                phoneBook.setMobileShared(cursor.getString(9));
                phoneBook.setEmail_Id(cursor.getString(10));
                phoneBookList.add(phoneBook);
            } while (cursor.moveToNext());
        }

        return phoneBookList;
    }
    public List<ContactTest> getAllEmailContacts() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<ContactTest> phoneBookList = new ArrayList<ContactTest>();
        String selectQuery = "SELECT  * FROM " + TestSQLiteHelper.TABLE_CONTACTS +" WHERE "+ TestSQLiteHelper.KEY_EMAIL_ID +"!="+"1"+" ORDER BY "+ TestSQLiteHelper.KEY_NAME +" ASC";

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                ContactTest phoneBook = new ContactTest();
                phoneBook.setId(cursor.getString(0));
                phoneBook.setName(cursor.getString(1));
                phoneBook.setPhone(cursor.getString(2));
                phoneBook.setContactImage(cursor.getBlob(3));
                phoneBook.setEmail_mOnline(cursor.getString(4));
                phoneBook.setMobile_mOnline(cursor.getString(5));
                phoneBook.setMobile_defaut(cursor.getString(6));
                phoneBook.setEmail_defaut(cursor.getString(7));
                phoneBook.setEmailShared(cursor.getString(8));
                phoneBook.setMobileShared(cursor.getString(9));
                phoneBook.setEmail_Id(cursor.getString(10));
                phoneBookList.add(phoneBook);
            } while (cursor.moveToNext());
        }

        return phoneBookList;
    }
    public List<ContactTest> getAllMobileContacts() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<ContactTest> phoneBookList = new ArrayList<ContactTest>();
        String selectQuery = "SELECT  * FROM " + TestSQLiteHelper.TABLE_CONTACTS +" ORDER BY "+ TestSQLiteHelper.KEY_NAME +" ASC";
//        String selectQuery = "SELECT  * FROM " + dbHelper.TABLE_CONTACTS +" WHERE "+dbHelper.KEY_EMAIL_ID+"="+"1"+" ORDER BY "+dbHelper.KEY_NAME+" ASC";
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                ContactTest phoneBook = new ContactTest();
                phoneBook.setId(cursor.getString(0));
                phoneBook.setName(cursor.getString(1));
                phoneBook.setPhone(cursor.getString(2));
                phoneBook.setContactImage(cursor.getBlob(3));
                phoneBook.setEmail_mOnline(cursor.getString(4));
                phoneBook.setMobile_mOnline(cursor.getString(5));
                phoneBook.setMobile_defaut(cursor.getString(6));
                phoneBook.setEmail_defaut(cursor.getString(7));
                phoneBook.setEmailShared(cursor.getString(8));
                phoneBook.setMobileShared(cursor.getString(9));
                phoneBook.setEmail_Id(cursor.getString(10));
                if(!phoneBook.getPhone().equals("")){
                phoneBookList.add(phoneBook);}
            } while (cursor.moveToNext());
        }

        return phoneBookList;
    }
    public List<ContactTest> getAllDisconnectedContacts(int disconnectedEmail) {
        String selectQuery=null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<ContactTest> phoneBookList = new ArrayList<ContactTest>();
        if(disconnectedEmail==1) {
            selectQuery = "SELECT  * FROM " + TestSQLiteHelper.TABLE_CONTACTS +" WHERE " +"("+ TestSQLiteHelper.KEY_EMAIL_ONLINE +"="+"0"
                   +" AND "+ TestSQLiteHelper.KEY_EMAIL_DEFAULT +"="+"0"+")"+" AND "+ TestSQLiteHelper.KEY_EMAIL_ID +"!=1"+" ORDER BY "+ TestSQLiteHelper.KEY_NAME +" ASC";
        }
        else{
            selectQuery = "SELECT  * FROM " + TestSQLiteHelper.TABLE_CONTACTS +" WHERE " + TestSQLiteHelper.KEY_MOBILE_ONLINE +"="+"0"
                    +" AND "+ TestSQLiteHelper.KEY_MOBILE_DEFAULT +"="+"0"+" ORDER BY "+ TestSQLiteHelper.KEY_NAME +" ASC";
        }
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                ContactTest phoneBook = new ContactTest();
                phoneBook.setId(cursor.getString(0));
                phoneBook.setName(cursor.getString(1));
                phoneBook.setPhone(cursor.getString(2));
                phoneBook.setContactImage(cursor.getBlob(3));
                phoneBook.setEmail_mOnline(cursor.getString(4));
                phoneBook.setMobile_mOnline(cursor.getString(5));
                phoneBook.setMobile_defaut(cursor.getString(6));
                phoneBook.setEmail_defaut(cursor.getString(7));
                phoneBook.setEmailShared(cursor.getString(8));
                phoneBook.setMobileShared(cursor.getString(9));
                phoneBook.setEmail_Id(cursor.getString(10));
                if(disconnectedEmail!=1){
                    if(!phoneBook.getPhone().equals("")){
                        phoneBookList.add(phoneBook);}
                }
                else
                {
                    phoneBookList.add(phoneBook);
                }
            } while (cursor.moveToNext());
        }

        return phoneBookList;
    }
    public List<ContactTest> getAllConnectedContacts(int connectEmail) {
        String selectQuery;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<ContactTest> phoneBookList = new ArrayList<ContactTest>();
        if(connectEmail==1) {
            selectQuery = "SELECT  * FROM " + TestSQLiteHelper.TABLE_CONTACTS +" WHERE " + TestSQLiteHelper.KEY_EMAIL_ONLINE +"="+"1"+" ORDER BY "+ TestSQLiteHelper.KEY_NAME +" ASC";
        }
        else{
            selectQuery = "SELECT  * FROM " + TestSQLiteHelper.TABLE_CONTACTS +" WHERE " + TestSQLiteHelper.KEY_MOBILE_ONLINE +"="+"1"+" ORDER BY "+ TestSQLiteHelper.KEY_NAME +" ASC";
        }
      //  String selectQuery = "SELECT  * FROM " + dbHelper.TABLE_CONTACTS;

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                ContactTest phoneBook = new ContactTest();
                phoneBook.setId(cursor.getString(0));
                phoneBook.setName(cursor.getString(1));
                phoneBook.setPhone(cursor.getString(2));
                phoneBook.setContactImage(cursor.getBlob(3));
                phoneBook.setEmail_mOnline(cursor.getString(4));
                phoneBook.setMobile_mOnline(cursor.getString(5));
                phoneBook.setMobile_defaut(cursor.getString(6));
                phoneBook.setEmail_defaut(cursor.getString(7));
                phoneBook.setEmailShared(cursor.getString(8));
                phoneBook.setMobileShared(cursor.getString(9));
                phoneBook.setEmail_Id(cursor.getString(10));
                if(connectEmail!=1){
                    if(!phoneBook.getPhone().equals("")){
                        phoneBookList.add(phoneBook);}
                }
                else
                {
                    phoneBookList.add(phoneBook);
                }
            } while (cursor.moveToNext());
        }

        return phoneBookList;
    }
    public int updateSentStatus(ContactTest phoneBook,String fromemail) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        if(fromemail.equals("1")){
        values.put(TestSQLiteHelper.KEY_EMAIL_SHARED,phoneBook.getEmailShared());}
        else{values.put(TestSQLiteHelper.KEY_MOBILE_SHARED,phoneBook.getMobileShared());}
        return db.update(TestSQLiteHelper.TABLE_CONTACTS, values, TestSQLiteHelper.KEY_ID + " = ?",
                new String[] { phoneBook.getId() });
    }
    public int updateContact(ContactTest phoneBook) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TestSQLiteHelper.KEY_ID, phoneBook.getId());
        values.put(TestSQLiteHelper.KEY_NAME, phoneBook.getName());
        values.put(TestSQLiteHelper.KEY_CONTACT, phoneBook.getPhone());
        values.put(TestSQLiteHelper.KEY_EMAIL_ONLINE,phoneBook.getEmail_mOnline());
        values.put(TestSQLiteHelper.KEY_MOBILE_ONLINE,phoneBook.getMobile_mOnline());
       values.put(TestSQLiteHelper.KEY_IMAGE, phoneBook.getContactImage());
       values.put(TestSQLiteHelper.KEY_MOBILE_DEFAULT,phoneBook.getMobile_defaut());
        values.put(TestSQLiteHelper.KEY_EMAIL_DEFAULT,phoneBook.getEmail_defaut());
        values.put(TestSQLiteHelper.KEY_EMAIL_SHARED,phoneBook.getEmailShared());
        values.put(TestSQLiteHelper.KEY_MOBILE_SHARED,phoneBook.getMobileShared());
        values.put(TestSQLiteHelper.KEY_EMAIL_ID,phoneBook.getEmail_Id());
        return db.update(TestSQLiteHelper.TABLE_CONTACTS, values, TestSQLiteHelper.KEY_ID + " = ?",
                new String[] { phoneBook.getId() });
    }


    public void deleteContact(ContactTest phoneBook) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TestSQLiteHelper.TABLE_CONTACTS, TestSQLiteHelper.KEY_ID + " = ?",
                new String[] { phoneBook.getId() });
        db.close();
    }
    public void deleteAllContact() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TestSQLiteHelper.TABLE_CONTACTS,null,null);
        db.close();
    }

    public int getContactsCount() {

        String countQuery = "SELECT  * FROM " + TestSQLiteHelper.TABLE_CONTACTS;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }


}
