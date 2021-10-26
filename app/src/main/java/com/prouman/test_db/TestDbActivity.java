package com.prouman.test_db;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
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


import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.prouman.R;
import com.prouman.Util.SessionManager;
import com.prouman.adapter.ContactDividerItem;
import com.prouman.services.UploadData;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TestDbActivity extends AppCompatActivity {
    RecyclerView rvContacts;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private List<ContactTest> contactItems;
    private List<DisconnectTest> disList;
//
//    String phoneNumber;
    private Button add;
   // private DBAdapterNew adapter;
    private ComplexAdapter adapter;
    ContactDbHandler db;
    Cursor phonesCursor;

    // private SearchView searchView;



    private String id, name, phone, image_uri,email_id="1";
    private byte[] contactImage = null;
    private Bitmap bitmap;
    TextView text_member;
    private ListView listView;
    private ProgressBar progressBar;

    private SearchView mSearchView;
    private ImageView backBtn;
    private boolean fromEmail;
    SessionManager sessionManager;
    Toolbar toolbar;
    int menuClick=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contacts);
        text_member=(TextView)findViewById(R.id.text_member);
        sessionManager=new SessionManager(this);
        fromEmail = getIntent().getExtras().getString("from").equals("emailclick");
        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        mSearchView = (SearchView) findViewById(R.id.search);
        backBtn =(ImageView)findViewById(R.id.layout_logo);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {


                      finish();


            }
        });
        mSearchView.setOnQueryTextListener(listener);
        initToolbar();
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
     //   boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        db = new ContactDbHandler(this);
        if(fromEmail){
        contactItems = db.getDefaultPlusConnectedContacts(1);}
        else{contactItems = db.getDefaultPlusConnectedContacts(0);}
        if (contactItems.size()>0)
        {
            setListAdapter();

           /* SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.commit();*/
        }
        else {

//            db = new ContactDbHandler(this);
//
//            contactItems = db.getAllContacts();
            showContacts();

        }
            updateMenuTitles();
}
    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();

            adapter.getFilter().filter(query);
            return false;




        }
        public boolean onQueryTextSubmit(String query) {
            return false;
        }
    };
    private void initToolbar() {
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.action_allcontacts){
                 //  Toast.makeText(getApplicationContext(),"Dialog will be here",Toast.LENGTH_LONG).show();
                   // contactItems = db.getAllContacts();
                    menuClick=0;
//                    if(fromEmail) {
//                       // contactItems = db.getAllEmailContacts();
//                        contactItems=db.getDefaultPlusConnectedContacts(1);
//                    }
//                    else{//contactItems = db.getAllMobileContacts();
//                        contactItems=db.getDefaultPlusConnectedContacts(0);
//                         }
//                    setListAdapter();
                }
                else if(item.getItemId() == R.id.action_connected){
                    menuClick=1;
//                    if(fromEmail) {
//                        contactItems = db.getAllConnectedContacts(1);
//                    }
//                    else{contactItems = db.getAllConnectedContacts(0);}
//                    setListAdapter();
//                    Iterator<ContactTest> ite = contactItems.iterator();
//
//          /* Remove the second value of the list, while iterating over its elements,
//           * using the iterator's remove method. */
//                    while(ite.hasNext()) {
//                        ContactTest value = ite.next();
//                        if(value.getEmail_mOnline().equals("0"))
//                            ite.remove();
//
//                       adapter.notifyDataSetChanged();
//                    }
                }
                else if(item.getItemId() == R.id.action_disconnected)
                {
                    menuClick=2;
//                    if(fromEmail) {
//                        contactItems = db.getAllDisconnectedContacts(1);
//                    }
//                    else {contactItems = db.getAllDisconnectedContacts(0);}
//                        setListAdapter();
                }
                else if(item.getItemId() == R.id.action_sync)
                {
                   menuClick=3;
                  //  new ContactInfo().execute();
                }
                onMenuclick();
                return true;
            }
        });

        toolbar.inflateMenu(R.menu.menu_test);
    }
    private void onMenuclick()
    {
        if(menuClick == 0){

            if(fromEmail) {
                // contactItems = db.getAllEmailContacts();
                contactItems=db.getDefaultPlusConnectedContacts(1);
            }
            else{//contactItems = db.getAllMobileContacts();
                contactItems=db.getDefaultPlusConnectedContacts(0);
            }
            setListAdapter();
        }
        else if(menuClick==1){
            if(fromEmail) {
                contactItems = db.getAllConnectedContacts(1);
            }
            else{contactItems = db.getAllConnectedContacts(0);}
            setListAdapter();
        }
        else if(menuClick == 2)
        {

            if(fromEmail) {
                contactItems = db.getAllDisconnectedContacts(1);
            }
            else {contactItems = db.getAllDisconnectedContacts(0);}
            setListAdapter();
        }
        else if(menuClick==3)
        {
            new ContactInfo().execute();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                new ContactInfo().execute();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    private void readContacts() {
//            int update=db.getAllContacts().size();
//            contactItems = new ArrayList<>();
//            String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//                    ContactsContract.CommonDataKinds.Phone.NUMBER ,
//                    ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
//                    ContactsContract.CommonDataKinds.Email.ADDRESS};
//            ContentResolver cr = getApplicationContext().getContentResolver();
//            Cursor cur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
//            if (cur.getCount() > 0) {
//                while (cur.moveToNext()) {
//                    ContactTest item = new ContactTest();
//                    email_id="1";
//                    id = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.DATA));
//                    name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                    // name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
//                    image_uri = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
//                  //  email_id= cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
////                    if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
////                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
////                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
////                        while (pCur.moveToNext()) {
////                            phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
////                            phone = phone.replaceAll("\\s+", "");
////                            phone = phone.replaceAll("[^0-9]", "");
////                        }
////                        pCur.close();
////                    }
//                    Cursor phoneCursor = cr.query(
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Contactables.DATA + " = ?",
//                            new String[]{id},
//                            null);
//                    if (phoneCursor.moveToNext()) {
//                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        phone=phoneNumber;
//                    }
//
//                    phoneCursor.close();
//                    ArrayList<String> arrayList=new ArrayList<>();
//                    Cursor emailCursor = cr.query(
//                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
//                            new String[]{id}, null);
//                    while (emailCursor.moveToNext()) {
//                        email_id = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));}
//                    arrayList.add(email_id);
//                    emailCursor.close();
//                    item.setEmail_Id(email_id);
//                    if (image_uri != null) {
//                        try {
//                            bitmap = MediaStore.Images.Media
//                                    .getBitmap(getApplicationContext().getContentResolver(),
//                                            Uri.parse(image_uri));
//                            contactImage = getImageBytes(bitmap);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        contactImage = null;
//                    }
//                    if(update>0){
//                    ContactTest contact=db.getContact(id);
//                        db.deleteContact(contact);
//                        if(contact==null) {
//                            insertContact(item,arrayList);
//                        }
//                        else {item.setId(id);
//                            item.setName(name);
//                            item.setContactImage(contactImage);
//                            item.setPhone(phone);
//                            item.setEmail_mOnline(contact.getEmail_mOnline());
//                            item.setMobile_mOnline(contact.getMobile_mOnline());
//                            item.setMobile_defaut(contact.getMobile_defaut());
//                            item.setEmail_defaut(contact.getEmail_defaut());
//                            item.setEmailShared(contact.getEmailShared());
//                            item.setMobileShared(contact.getMobileShared());
//                            item.setEmail_Id(email_id);
//                            db.updateContact(item);}
//                        }
//                    else
//                    {
//                        insertContact(item,arrayList);
//                    }
//
//
//                }
//               // setListAdapter();
//                if(fromEmail){
//                    contactItems = db.getAllEmailContacts();}
//                else{contactItems = db.getAllMobileContacts();}
//                sessionManager.setContactUpdateDateTime(getDateTime());
//
//            }
//        }


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
          ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog= new ProgressDialog(TestDbActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.show();
            rvContacts.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... params) {
//            readContacts();
            ShowContact();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();
          //progressBar.setVisibility(View.GONE);
           // progressDialog.hide();
            rvContacts.setVisibility(View.VISIBLE);
            setListAdapter();
            updateMenuTitles();
            adapter.notifyDataSetChanged();
            Intent intent = new Intent(TestDbActivity.this, UploadData.class);
            startService(intent);
        }
    }

    private void setListAdapter() {

        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new DBAdapterNew(contactItems, getApplicationContext(),rvContacts);
        adapter= new ComplexAdapter(contactItems, TestDbActivity.this,rvContacts);
        //contactAdapter = new ContactAdapter(getApplicationContext(),contactVOList);

        rvContacts.addItemDecoration( new ContactDividerItem(ContextCompat.getDrawable(getApplicationContext(),R.drawable.item_decoratin)));



         rvContacts.setAdapter(adapter);
    }
    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            new ContactInfo().execute();

        }
    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    private void updateMenuTitles() {
       Menu menu=toolbar.getMenu();
        MenuItem bedMenuItem = menu.findItem(R.id.action_sync);
       // AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) bedMenuItem.getMenuInfo();
            bedMenuItem.setTitle("Last Sync "+sessionManager.getContactUpdateDateTime());

    }
    private void insertContact(ContactTest item,ArrayList<String> emailList)
    {
        if (emailList.size() > 0) {
            for (int i = 0; i < emailList.size(); i++) {
                item.setId(id+8000+i);
                item.setName(name);
                item.setContactImage(contactImage);
                if(i==0) {
                    item.setPhone(phone);
                }
                else{item.setPhone("");}
                item.setEmail_mOnline("0");
                item.setMobile_mOnline("0");
                item.setMobile_defaut("1");
                item.setEmail_defaut("1");
                item.setEmailShared("0");
                item.setMobileShared("0");
                item.setEmail_Id(emailList.get(i));
                db.addContact(item);
            }
        }
        else
        {
            item.setId(id);
            item.setName(name);
            item.setContactImage(contactImage);
            item.setPhone(phone);
            item.setEmail_mOnline("0");
            item.setMobile_mOnline("0");
            item.setMobile_defaut("1");
            item.setEmail_defaut("1");
            item.setEmailShared("0");
            item.setMobileShared("0");
            item.setEmail_Id(email_id);
            db.addContact(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        onMenuclick();
//        if(menuClick==0) {
//            if (fromEmail) {
//                contactItems = db.getDefaultPlusConnectedContacts(1);
//            } else {
//                contactItems = db.getDefaultPlusConnectedContacts(0);
//            }
//            if (contactItems.size() > 0) {
//                setListAdapter();
//            }
//        }
//        else if(menuClick==1)
//        {
//
//        }
//        else if(menuClick==2)
//        {
//            a
//        }
    }
//    private void query2()
//    {
//        ContentResolver resolver = getContentResolver();
//        Cursor c = resolver.query(
//                ContactsContract.Data.CONTENT_URI,
//                null,
//                ContactsContract.Data.HAS_PHONE_NUMBER + "!=0 AND (" + ContactsContract.Data.MIMETYPE + "=? OR " + ContactsContract.Data.MIMETYPE + "=?)",
//                new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE},
//                ContactsContract.Data.CONTACT_ID);
//
//        while (c.moveToNext()) {
//            long id = c.getLong(c.getColumnIndex(ContactsContract.Data.CONTACT_ID));
//            String name = c.getString(c.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
//            String data1 = c.getString(c.getColumnIndex(ContactsContract.Contacts.Data.DATA1));
//
//            System.out.println(id + ", name=" + name + ", data1=" + data1);
//        }
//    }
    public ArrayList<String> ShowContact() {
        int update=db.getAllContacts().size();
       ArrayList nameList = new ArrayList<String>();
       ArrayList phoneList = new ArrayList<String>();
       ArrayList<String> emailList=null;

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
//                String id = cur.getString(cur
//                        .getColumnIndex(ContactsContract.Contacts._ID));
//                String name = cur
//                        .getString(cur
//                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                ContactTest item = new ContactTest();
                email_id="1";
                id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                // name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
                image_uri = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                if (Integer
                        .parseInt(cur.getString(cur
                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    // Query phone here. Covered next

                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = ?", new String[] { id }, null);
                    while (pCur.moveToNext()) {
                        // Do something with phones
                        phone= pCur
                                .getString(pCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        nameList.add(name); // Here you can list of contact.
                        phoneList.add(phone); // Here you will get list of phone number.

                        emailList = new ArrayList<String>();
                        Cursor emailCur = cr.query(
                                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (emailCur.moveToNext()) {
                            email_id  = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                            emailList.add(email_id); // Here you will get list of email

                        }
                        emailCur.close();
                    }
                    pCur.close();
                    item.setEmail_Id(email_id);
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
                    if(update>0){
                        ContactTest contact=db.getContact(id);
                        if(contact==null) {
                            insertContact(item,emailList);
                        }
                        else {
                            if (emailList.size() > 0) {
                                for (int i = 0; i < emailList.size(); i++) {
                                    item.setId(id+8000+i);
                                    item.setName(name);
                                    item.setContactImage(contactImage);
                                    if(i==0) {
                                        item.setPhone(phone);
                                    }
                                    else{item.setPhone("");}
                                        item.setEmail_mOnline(contact.getEmail_mOnline());
                                    item.setMobile_mOnline(contact.getMobile_mOnline());
                                    item.setMobile_defaut(contact.getMobile_defaut());
                                    item.setEmail_defaut(contact.getEmail_defaut());
                                    item.setEmailShared(contact.getEmailShared());
                                    item.setMobileShared(contact.getMobileShared());
                                    item.setEmail_Id(emailList.get(i));
                                    db.updateContact(item);
                                }
                            } else {
                                item.setId(id);
                                item.setName(name);
                                item.setContactImage(contactImage);
                                item.setPhone(phone);
                                item.setEmail_mOnline(contact.getEmail_mOnline());
                                item.setMobile_mOnline(contact.getMobile_mOnline());
                                item.setMobile_defaut(contact.getMobile_defaut());
                                item.setEmail_defaut(contact.getEmail_defaut());
                                item.setEmailShared(contact.getEmailShared());
                                item.setMobileShared(contact.getMobileShared());
                                item.setEmail_Id(email_id);
                                db.updateContact(item);
                            }
                        }
                        }
                    else
                    {
                        insertContact(item,emailList);
                    }


                }
                // setListAdapter();
                if(fromEmail){
                    contactItems = db.getAllEmailContacts();}
                else{contactItems = db.getAllMobileContacts();}
                sessionManager.setContactUpdateDateTime(getDateTime());



                }
            }


        return nameList; // here you can return whatever you want.
    }
}

