package com.prouman.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.prouman.R;
import com.prouman.adapter.RecyclerViewAdapter;
import com.prouman.adapter.SampleAdapterView;
import com.prouman.model.ContactVO;
import com.prouman.model.ItemObject;
import com.prouman.ninjaforms.NativeFormActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.os.Build.VERSION_CODES.M;
import static com.prouman.R.id.tv_contactName;

public class NinjaMemberHome extends AppCompatActivity {


    String sEmailId;

    String uproAffLink;
    String uproShopLink;
    String upro_ref, upro_id;

    ImageView drawImage;
    TextView emailTv, phoneTv, contactName, header_TV;//websiteName//phoneNumberemailName
    ImageView phoneIcon, chatIcon, contactImage, backBtn;//emailIcon
    Button connectContBtn,disConnectBtn;//joinBtn

    private String websiteUrl;


    String firstName;
    Button home, logBtn, callBtn, settingBtn, pushBtn;//msg
    String imgUrl;
    String img_name;
    private GridLayoutManager lLayout;
    String lastName;
    Button logoutBtn;
    String phone;
    RecyclerViewAdapter rcAdapter;
    List<ItemObject> rowListItem;
    CircleImageView toolImg;
    TextView toolText;
    TextView tvLast;
    TextView tvName;
    TextView tvPhoneNumber;
    LinearLayout ll1;
    LinearLayout ll2;
    LinearLayout ll3;
    LinearLayout ll4;
    LinearLayout ll5;
    LinearLayout ll6;
    private RelativeLayout backToGuest;
    private RelativeLayout bottomLayout;
    private boolean isShowShareLayout;
    private ProgressBar loadProgress;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RelativeLayout shareLayout;
    private TextView title;
    private RelativeLayout topLayout;
    CircleImageView headerImage;
    String headerName;
  //  TextView copyrightTv;
  //  private ImageView broweserIcon;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //initToolbar();
       // mSearchView = (SearchView) findViewById(R.id.search);
        initView();

        Intent intent = getIntent();
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
        }
        backToGuest = (RelativeLayout) findViewById(R.id.layout_back_to_guest);
        topLayout = ((RelativeLayout) findViewById(R.id.layout_top));
        backToGuest.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                finish();

            }
        });
        // headerName = firstName;
        imgUrl =img_name;// ConfigURL.PHOTO_URL +
        Picasso.with(this).load(imgUrl).placeholder(R.drawable.round_corner).into(toolImg);

//        broweserIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(websiteUrl)));
//                return;
//            }
//        });
//        copyrightTv.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(ConfigURL.COPY_RIGHT_URL)));
//                return;
//            }
//        });

       // showContacts();
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

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
//        TextView headerPlace = (TextView) header.findViewById(R.id.memebr_place);
//        headerPlace.setText("Italy");
        drawImage.setOnClickListener(new OnClickListener() {
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
        home.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(NinjaMemberHome.this,NinjaMemberHome.class);
                startActivity(i);*/
                drawer.closeDrawers();
            }
        });
//        msg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(NinjaMemberHome.this, NotificationActivity.class);
//                startActivity(i);
//                drawer.closeDrawers();
//            }
//        });
        settingBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NinjaMemberHome.this, SettingActivity.class);
                startActivity(i);
                drawer.closeDrawers();
            }
        });
        toolImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //mSearchView.setOnQueryTextListener(listener);
    }/*
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
    };*/
    private void initView() {
        drawImage = (ImageView) findViewById(R.id.menu_icon_iv);
        //phoneNumber = (TextView) findViewById(R.id.phone_number);
      //  emailName = (TextView) findViewById(R.id.emailName);
        toolImg = (CircleImageView) findViewById(R.id.layout_logo);
        contactImage = (ImageView) findViewById(R.id.contact_image);
        //joinBtn = (Button) findViewById(R.id.change_language);
        contactName = (TextView) findViewById(R.id.contactName);
        //websiteName = (TextView) findViewById(R.id.website_url_member);
        //copyrightTv = (TextView) findViewById(R.id.copyright_tv_member);
        header_TV = (TextView) findViewById(tv_contactName);
        home = (Button) findViewById(R.id.btn_home);
        //msg = (Button) findViewById(R.id.btn_demo_info);
        settingBtn = (Button) findViewById(R.id.btn_setting);
        pushBtn = (Button) findViewById(R.id.btn_push);
        logBtn = (Button) findViewById(R.id.btn_logout);
        //broweserIcon = (ImageView) findViewById(R.id.browswer_icon);
        //emailIcon = (ImageView) findViewById(R.id.email_icon);
        backBtn = (ImageView) findViewById(R.id.back_btn);
        //chatIcon = (ImageView) findViewById(R.id.msg_icon);
        ll1 = (LinearLayout) findViewById(R.id.layout_1);
        ll2 = (LinearLayout) findViewById(R.id.layout_2);
        ll3 = (LinearLayout) findViewById(R.id.layout_3);
        ll4 = (LinearLayout) findViewById(R.id.layout_4);
        ll5 = (LinearLayout) findViewById(R.id.layout_5);
        ll6 = (LinearLayout) findViewById(R.id.layout_6);
        ll1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(NinjaMemberHome.this, ContactListActivity.class));
               // Toast.makeText(getApplicationContext(),"Contact Listing will be there",Toast.LENGTH_LONG).show();
            }
        });

        ll2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(NinjaMemberHome.this,NinjaCategoryActivity.class);
                startActivity(intent);
            }
        });
        ll3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(NinjaMemberHome.this,PrivateCategoryActivity.class);
                startActivity(intent);

            }
        });
        ll5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://academy.ninjaviralteam.com")));
            }
        });
        ll4.setOnClickListener(new OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       startActivity(new Intent("android.intent.action.VIEW", Uri.parse(" https://app.prouman.network/auth/register_account/" + uproShopLink)));
                                   }
                               }
        );
        ll6.setOnClickListener(new OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Intent intent =new Intent(NinjaMemberHome.this,NativeFormActivity.class);
                                       startActivity(intent);
                                       //startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.moneysite.net/auth/register_account/" + uproShopLink)));
                                   }
                               }
        );

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
        if (Build.VERSION.SDK_INT >= M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            getAllContacts();

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                getAllContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void getAllContacts() {
        List<ContactVO> contactVOList = new ArrayList();
        ContactVO contactVO;
        // Check the SDK version and whether the permission is already granted or not.

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    image_uri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                    contactVO = new ContactVO();
                    contactVO.setContactName(name);
                    // contactVO.setContactImage(imgId);

                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);
                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactVO.setContactNumber(phoneNumber);
                    }

                    phoneCursor.close();
                    if (image_uri != null) {
                        contactVO.setContactImage(Uri.parse(image_uri));
//                        try {
//                          //  bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse(image_uri));
//                            //phoneContactImage = getImageBytes(bitmap);
//                            // bitmap =MediaStore.Images.Media.getBitmap()
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    } else {
                        // phoneContactImage = null;
                        contactVO.setContactImage(null);
                    }

                    // contactVO.setContactImage(phoneContactImage);
                    // item.setPhone(phone);
                    Cursor emailCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (emailCursor.moveToNext()) {
                        String emailId = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));}
                    emailCursor.close();

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
                }
            }

            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method

            // Android version is lesser than 6.0 or the permission is already granted.

        /*    rvContacts.setLayoutManager(new LinearLayoutManager(this));
             contactAdapter = new SampleAdapterView(contactVOList, getApplicationContext(),rvContacts);
         rvContacts.addItemDecoration( new ContactDividerItem(ContextCompat.getDrawable(getApplicationContext(),R.drawable.item_decoratin)));
            rvContacts.setAdapter(contactAdapter);*/


        }
    }

    private byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }
}
