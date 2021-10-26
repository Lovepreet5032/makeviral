package com.prouman.contact_act;

import android.Manifest;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.prouman.R;
import com.prouman.adapter.ContactDividerItem;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestDbActivity extends AppCompatActivity {
    RecyclerView rvContacts;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private List<ContactTest> contactItems;


    String phoneNumber;
    private Button add;
   // private DBAdapterNew adapter;
    private ComplexAdapter adapter;
    ContactDbHandler db;
    Cursor phonesCursor;

    // private SearchView searchView;


    protected Toolbar toolbar;
    private String id, name, phone, image_uri;
    private byte[] contactImage = null;
    private Bitmap bitmap;

    private ListView listView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contacts);

        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
       // progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        initToolbar();
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
     //   boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        db = new ContactDbHandler(this);

        contactItems = db.getAllContacts();
        if (contactItems.size()>0)
        {
            setListAdapter();

           /* SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.commit();*/
        }
        else {

            db = new ContactDbHandler(this);

            contactItems = db.getAllContacts();
            new ContactInfo().execute();

        }







}

    private void initToolbar() {
        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.action_allcontacts){
                   Toast.makeText(getApplicationContext(),"DIalog will be here",Toast.LENGTH_LONG).show();

                }
                else if(item.getItemId() == R.id.action_connected){
                 //  adapter.filterConnected();
                }
                else if(item.getItemId() == R.id.action_disconnected)
                {
                    Toast.makeText(getApplicationContext(),"Disconnedted will be here",Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
        toolbar.inflateMenu(R.menu.menu_test);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                readContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void readContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
        else {



            contactItems = new ArrayList<>();
            ContentResolver cr = getApplicationContext().getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                    null, null, null);
            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    ContactTest item = new ContactTest();
                    id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    // name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
                    image_uri = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                    if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phone = phone.replaceAll("\\s+", "");
                            phone = phone.replaceAll("[^0-9]", "");
                        }
                        pCur.close();
                    }
                    if (image_uri != null) {
                        try {
                            bitmap = MediaStore.Images.Media
                                    .getBitmap(getApplicationContext().getContentResolver(),
                                            Uri.parse(image_uri));
                            contactImage = getImageBytes(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        contactImage = null;
                    }
                    item.setId(id);
                    item.setName(name);
                    item.setContactImage(contactImage);
                    item.setPhone(phone);
                    item.setmOnline("0");
                    contactItems.add(item);
                }
            }
        }
    }

    private byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }
/*
    private void bindDataToAdapter() {
        // Bind adapter to recycler view object
        rv.setAdapter(new ComplexAdapter(getSampleArrayList()));
    }*/
    public class ContactInfo extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
            rvContacts.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            readContacts();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            progressBar.setVisibility(View.GONE);
            rvContacts.setVisibility(View.VISIBLE);
            setListAdapter();
            adapter.notifyDataSetChanged();
        }
    }

    private void setListAdapter() {

        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new DBAdapterNew(contactItems, getApplicationContext(),rvContacts);
        adapter= new ComplexAdapter(contactItems, getApplicationContext(),rvContacts);
        //contactAdapter = new ContactAdapter(getApplicationContext(),contactVOList);

        rvContacts.addItemDecoration( new ContactDividerItem(ContextCompat.getDrawable(getApplicationContext(),R.drawable.item_decoratin)));

         rvContacts.setAdapter(adapter);
    }

}
