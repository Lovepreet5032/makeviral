package com.prouman.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Telephony;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.prouman.R;
import com.prouman.adapter.ContactDividerItem;
import com.prouman.adapter.RecyclerViewAdapter;
import com.prouman.filter.CustomFilter;
import com.prouman.model.ItemObject;
import com.prouman.services.UploadData;
import com.prouman.sms_template.SmsActivity;
import com.prouman.test_db.ContactDbHandler;
import com.prouman.test_db.ContactTest;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jcs on 12/6/2016.
 */

public class EmailListActivity extends AppCompatActivity implements View.OnClickListener {

    String sEmailId;

    String uproAffLink;
    String uproShopLink;
    String upro_ref, upro_id;

    ImageView drawImage;
    TextView phoneNumber, emailTv, emailName, phoneTv, contactName, websiteName, header_TV;
    ImageView phoneIcon, emailIcon, chatIcon, backBtn;
    Button joinBtn,connectContBtn,disConnectBtn;

    private String websiteUrl;


    String firstName;
    Button home, msg, logBtn, callBtn, settingBtn, pushBtn;
    String imgUrl;
    String img_name;
    private GridLayoutManager lLayout;
    String lastName;
    Button logoutBtn;
    String phone;
    RecyclerViewAdapter rcAdapter;
    List<ItemObject> rowListItem;
    CircleImageView toolImg;
    private RelativeLayout bottomLayout;
    private boolean isShowShareLayout;
    private ProgressBar loadProgress;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RelativeLayout shareLayout;
    private TextView title;
    private RelativeLayout topLayout;
    private RelativeLayout backToGuest;
    List<ContactTest> contactVOList;
    CircleImageView headerImage;
    String headerName;
    TextView copyrightTv;
    private ImageView broweserIcon;
    RecyclerView rvContacts;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    Cursor phonesCursor;
    String image_uri;
    private Bitmap bitmap;
    private byte[] phoneContactImage = null;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    SampleAdapterView contactAdapter;
    private SearchView mSearchView;
    ContactDbHandler db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list_layout);
        db = new ContactDbHandler(this);
       initToolbar();
        // mSearchView = (SearchView) findViewById(R.id.search);
        initView();

        /*Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            firstName = extras.getString("FName");
            lastName = extras.getString("LName");
            phone = extras.getString("Phone");
            img_name = extras.getString("imgName");
            uproAffLink = extras.getString("uproAffLink");
            uproShopLink = extras.getString("uproShopLink");
            sEmailId = extras.getString("upro_main_email");
            websiteUrl = extras.getString("upro_subdomain_link");
            upro_id = extras.getString("upro_id");
        } else {
            Toast.makeText(getApplicationContext(), "No Bundle", Toast.LENGTH_LONG).show();
        }*/
        backToGuest = (RelativeLayout) findViewById(R.id.layout_back_to_guest);

        backToGuest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();

            }
        });
       /* // headerName = firstName;
        imgUrl = ConfigURL.PHOTO_URL + img_name;
        Picasso.with(this).load(imgUrl).placeholder(R.drawable.round_corner).into(toolImg);

        broweserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(websiteUrl)));
                return;
            }
        });
        copyrightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(ConfigURL.COPY_RIGHT_URL)));
                return;
            }
        });*/
   //     if(getIntent().getExtras().getString("from").equals("emailclick")){
//        if(SessionManager.eContactVOList.size()>0)
//        {
//            contactVOList=new ArrayList<>();
//            contactVOList.addAll(SessionManager.eContactVOList);
//            setListAdapter();
//        }
//        else{
  // showContacts();}
//        }
//        else
//        {
//            if((SessionManager.ContContactVOList.size()>0)) {
//                contactVOList = new ArrayList<>();
//                contactVOList.addAll(SessionManager.ContContactVOList);
//                setListAdapter();
//            }
//            else{
                showContacts();

    //}
       // }

    /*  final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


    View header = navigationView.getHeaderView(0);
    headerImage = (CircleImageView) header.findViewById(R.id.imageView);

    Picasso.with(this).load(imgUrl).placeholder(R.drawable.round_corner).into(headerImage);
    TextView headerTitle = (TextView) header.findViewById(R.id.header_name);
    headerTitle.setText(firstName);
    TextView headetID = (TextView) header.findViewById(R.id.memebr_id);
    headetID.setText(upro_id);
    TextView headerPlace = (TextView) header.findViewById(R.id.memebr_place);
    headerPlace.setText("Italy");
    drawImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.END)) {
                drawer.closeDrawer(GravityCompat.END);
            } else {
                drawer.openDrawer(GravityCompat.END);
            }
        }
    });
  home.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                *//*Intent i = new Intent(NinjaMemberHome.this,NinjaMemberHome.class);
                startActivity(i);*//*
            drawer.closeDrawers();
        }
    });
    msg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(NinjaMemberHome.this, NotificationActivity.class);
            startActivity(i);
            drawer.closeDrawers();
        }
    });
    settingBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(NinjaMemberHome.this, SettingActivity.class);
            startActivity(i);
            drawer.closeDrawers();
        }
    });*/


}
    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();

            contactAdapter.getFilter().filter(query);
            return false;




        }
        public boolean onQueryTextSubmit(String query) {
            return false;
        }
    };
    private void initView() {

        backBtn = (ImageView) findViewById(R.id.back_btn);

        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        mSearchView = (SearchView) findViewById(R.id.search);
        mSearchView.setOnQueryTextListener(listener);

    }

    private void initToolbar() {
        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu_test);
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

    public class ContactInfo extends AsyncTask<Void, Void, Void> {
    ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog= new ProgressDialog(EmailListActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.show();
            rvContacts.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            getAllContacts();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //progressBar.setVisibility(View.GONE);
            progressDialog.hide();
            rvContacts.setVisibility(View.VISIBLE);
            setListAdapter();
            contactAdapter.notifyDataSetChanged();
            Intent intent = new Intent(EmailListActivity.this, UploadData.class);
            startService(intent);
        }
    }

    private void setListAdapter() {

        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter = new SampleAdapterView(contactVOList, getApplicationContext(),rvContacts);
        rvContacts.addItemDecoration( new ContactDividerItem(ContextCompat.getDrawable(getApplicationContext(),R.drawable.item_decoratin)));
        rvContacts.setAdapter(contactAdapter);
    }
    private void getAllContacts() {
         contactVOList = new ArrayList();
        ContactTest contactVO;
        // Check the SDK version and whether the permission is already granted or not.
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER ,ContactsContract.CommonDataKinds.Phone.PHOTO_URI};
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

             //   int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
//                if (hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.DATA));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    image_uri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                    contactVO = new ContactTest();
                    contactVO.setName(name);
                    // contactVO.setContactImage(imgId);
                     contactVO.setId(id);
                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);
                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactVO.setPhone(phoneNumber);
                    }

                    phoneCursor.close();
                    if (image_uri != null) {
                      //  contactVO.setContactImage(Uri.parse(image_uri));
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse(image_uri));
                            phoneContactImage = getImageBytes(bitmap);
                         //    bitmap = MediaStore.Images.Media.getBitmap();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        phoneContactImage = null;
                      //  contactVO.setContactImage(null);
                    }

                    contactVO.setContactImage(phoneContactImage);
                    // item.setPhone(phone);
                    Cursor emailCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (emailCursor.moveToNext()) {
                        String emailId = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));}
                    emailCursor.close();
                    //   contactVO.set
                    /*try {
                        Uri phoneUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode("Phone number"));
                      phonesCursor = contentResolver.query(phoneUri, new String[]{ContactsContract.PhoneLookup.PHOTO_THUMBNAIL_URI}, null, null, null);
                    } catch (NullPointerException | IllegalArgumentException e) {
                        e.printStackTrace();
                    }

                    while (phonesCursor.moveToFirst()){
                        String contactId = phonesCursor.getString(0);
                        contactVO.setContactImage(contactId);
                    }
                    phonesCursor.close();*/
                    contactVOList.add(contactVO);
                    db.addContact(contactVO);
              //  }
            }

            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method

            // Android version is lesser than 6.0 or the permission is already granted.
//           if(getIntent().getExtras().getString("from").equals("emailclick")){
//              SessionManager.eContactVOList.addAll(contactVOList);}
//        else{ SessionManager.ContContactVOList.addAll(contactVOList);}

        }
    }

    private byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back_btn:
                finish();
                break;
        }
    }
    public class SampleAdapterView extends RecyclerView.Adapter<SampleAdapterView.ViewHolder> implements Filterable {
        private static final int UNSELECTED = -1;

        private RecyclerView recyclerView;
        private int selectedItem = UNSELECTED;
        public List<ContactTest> contactVOList, filterList;
        private Context mContext;
        String number;
        ArrayList<String> numberList;
        CustomFilter filter;
        NinjaMemberHome activity;

        private final static int SENT_MESSAGE = 0, RECEIVED_MESSAGE = 1;

        public SampleAdapterView(List<ContactTest> contactVOList, Context mContext, RecyclerView recyclerView) {
            this.contactVOList = contactVOList;
            this.mContext = mContext;
            this.recyclerView = recyclerView;
            this.filterList = contactVOList;
            numberList= new ArrayList<>();
            // db= new ContactDbHandler(mContext);
        }



        @Override
        public SampleAdapterView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.connection_list_row, parent, false);
            return new SampleAdapterView.ViewHolder(itemView);

        }


        @Override
        public void onBindViewHolder(SampleAdapterView.ViewHolder holder, final int position) {
            final ContactTest contactVO = contactVOList.get(position);
            holder.bind(position);
            final String name = contactVO.getName();
            number = contactVO.getPhone();
            holder.tvContactName.setText(contactVO.getName());
            holder.tvPhoneNumber.setText(contactVO.getPhone());
            if ((contactVO.getContactImage()) != null) {
             //   Bitmap contactImage = getContactImage(contactVO.getContactImage());
                //holder.ivContactImage.setImageBitmap(contactImage);
               // Picasso.with(mContext).load(contactVO.getContactImage()).into(holder.ivContactImage);
               // Picasso.with(mContext).load(contactImage).placeholder(R.drawable.round_corner).into(headerImage);// Picasso
            } else {
                holder.ivContactImage.setImageResource(R.drawable.ic_account_circle);
            }
            holder.connecBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCall(number);
                }
            });
            holder.msgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent msgIntent = new Intent(mContext,SmsActivity.class);
                    msgIntent.putExtra("phoneNumber",numberList);
                    msgIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(msgIntent);
                    //sendSMS(number);
                }
            });
            holder.disConnBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactVOList.remove(position);
                    notifyDataSetChanged();
                }
            });
           // holder.chkSelected.setChecked(contactVO.isSelected());

            holder.chkSelected.setTag(contactVO);


            holder.chkSelected.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    ContactTest contact = (ContactTest) cb.getTag();

                    //  contact.setSelected(cb.isChecked());
                   // contactVO.setSelected(cb.isChecked());
                    if(cb.isChecked()){
                        numberList.add(contact.getPhone());
                    }
else if(!cb.isChecked()){
                        numberList.remove(contact.getPhone());
                    }
             /*  Toast.makeText(
                        v.getContext(),
                        "Clicked on Checkbox: " + cb.getText() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();*/
                }
            });
        }

        @Override
        public int getItemCount() {
            return contactVOList.size();
        }

        @Override
        public Filter getFilter() {
            if(filter==null)
            {
                filter=new CustomFilter(filterList,this);
            }
            return filter;

        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private ExpandableLayout expandableLayout;
            ImageView ivContactImage;
            TextView tvContactName;
            TextView tvPhoneNumber;
            private int position;
            Button connecBtn,msgbtn;
            LinearLayout disConnBtn;
            public CheckBox chkSelected;
            public ViewHolder(View itemView) {
                super(itemView);


                expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout2);
                ivContactImage = (ImageView) itemView.findViewById(R.id.ivContactImage);
                tvContactName = (TextView) itemView.findViewById(R.id.tvContactName);
                tvPhoneNumber = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
                connecBtn= (Button)itemView.findViewById(R.id.btn_call);
                msgbtn= (Button)itemView.findViewById(R.id.btn_msg);
                disConnBtn= (LinearLayout)itemView.findViewById(R.id.layout_inactioe);
                chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);
                itemView.setOnClickListener(this);

            }

            public void bind(int position) {
                this.position = position;

                //t.setText(position + ". Tap to expand");

                itemView.setSelected(false);
                expandableLayout.collapse(false);
            }

            @Override
            public void onClick(View view) {
               SampleAdapterView.ViewHolder holder = (SampleAdapterView.ViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
                if (holder != null) {
                    holder.itemView.setSelected(false);
                    holder.expandableLayout.collapse();
                }

                if (position == selectedItem) {
                    selectedItem = UNSELECTED;
                } else {
                    itemView.setSelected(true);
                    expandableLayout.expand();
                    selectedItem = position;
                }
            }
        }
        private void sendSMS(String phone) {
            if (Build.VERSION.SDK_INT >= 19) {
                String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(mContext);
                Intent sendIntent = new Intent("android.intent.action.SEND", Uri.parse("tel:" + phone));
                sendIntent.putExtra("address", phone);
                sendIntent.setType("text/plain");
                sendIntent.putExtra("android.intent.extra.TEXT", "");
                if (defaultSmsPackageName != null) {
                    sendIntent.setPackage(defaultSmsPackageName);
                }
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(sendIntent);
                return;
            }
            Intent smsIntent = new Intent("android.intent.action.VIEW");
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", phone);
            smsIntent.putExtra("sms_body", "message");
            smsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(smsIntent);
        }

        private void onCall(String phone) {
            if (Build.VERSION.SDK_INT >= 19) {
                int permissionCheck = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);
                TelephonyManager tm = (TelephonyManager)mContext.getSystemService(TELEPHONY_SERVICE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            activity,
                            new String[]{Manifest.permission.CALL_PHONE},
                            123);
                } else {
                    Intent sendIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
                    sendIntent.putExtra("address", phone);
                    if (tm != null && tm.getSimState() == 5) {
                        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(sendIntent);
                        return;
                    }
                }


                return;
            }
            Intent callIntent = new Intent("android.intent.action.DIAL");
            callIntent.setPackage("com.android.phone");
            callIntent.setData(Uri.parse(phone));
            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(callIntent);
        }

        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch (requestCode) {

                case 123:
                    if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                        onCall(number);
                    } else {
                        Log.d("TAG", "Call Permission Not Granted");
                    }
                    break;

                default:
                    break;
            }
        }
        public void refreshBlockOverlay(int position) {
            notifyItemChanged(position);
        }

        private Bitmap getContactImage(byte[] photo) {
            int targetW = 50, targetH = 50;
            BitmapFactory.Options options = new BitmapFactory.Options();
            BitmapFactory.decodeByteArray(photo, 0, photo.length, options);
            options.inJustDecodeBounds = true;
            int imageW = options.outWidth;
            int imageH = options.outHeight;
            int scaleFactor = 1;
            if ((targetW > 0) || (targetH > 0)) {
                scaleFactor = Math.min(imageW / targetW, imageH / targetH);
            }
            options.inJustDecodeBounds = false;
            options.inSampleSize = scaleFactor;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return BitmapFactory.decodeByteArray(photo, 0, photo.length, options);
        }

    }
}
