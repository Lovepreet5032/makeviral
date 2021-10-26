package com.prouman.contact_act;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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


    int addContact(ContactTest phoneBook) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TestSQLiteHelper.KEY_NAME, phoneBook.getName());
        values.put(TestSQLiteHelper.KEY_CONTACT, phoneBook.getPhone());
        values.put(TestSQLiteHelper.KEY_TYPE, phoneBook.getName());
          values.put(TestSQLiteHelper.KEY_IMAGE, phoneBook.getContactImage());

        long insertId = db.insert(TestSQLiteHelper.TABLE_CONTACTS, null, values);
        db.close();
        return (int)insertId;
    }


    ContactTest getContact(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TestSQLiteHelper.TABLE_CONTACTS, new String[] {TestSQLiteHelper.KEY_ID,
                        TestSQLiteHelper.KEY_NAME, TestSQLiteHelper.KEY_CONTACT, TestSQLiteHelper.KEY_IMAGE}, TestSQLiteHelper.KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ContactTest phoneBook = new ContactTest(cursor.getString(0),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getBlob(4));

        return phoneBook;
    }


    public List<ContactTest> getAllContacts() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<ContactTest> phoneBookList = new ArrayList<ContactTest>();
        String selectQuery = "SELECT  * FROM " + TestSQLiteHelper.TABLE_CONTACTS;

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                ContactTest phoneBook = new ContactTest();
                phoneBook.setId(cursor.getString(0));
                phoneBook.setName(cursor.getString(1));
                phoneBook.setPhone(cursor.getString(2));
                phoneBook.setmOnline(cursor.getString(3));
                phoneBook.setContactImage(cursor.getBlob(4));
                phoneBookList.add(phoneBook);
            } while (cursor.moveToNext());
        }

        return phoneBookList;
    }


    public int updateContact(ContactTest phoneBook) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TestSQLiteHelper.KEY_NAME, phoneBook.getName());
        values.put(TestSQLiteHelper.KEY_CONTACT, phoneBook.getPhone());
        values.put(TestSQLiteHelper.KEY_TYPE,phoneBook.getmOnline());
       values.put(TestSQLiteHelper.KEY_IMAGE, phoneBook.getContactImage());


        return db.update(TestSQLiteHelper.TABLE_CONTACTS, values, TestSQLiteHelper.KEY_ID + " = ?",
                new String[] { phoneBook.getId() });
    }


    public void deleteContact(ContactTest phoneBook) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TestSQLiteHelper.TABLE_CONTACTS, TestSQLiteHelper.KEY_ID + " = ?",
                new String[] { phoneBook.getId() });
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
