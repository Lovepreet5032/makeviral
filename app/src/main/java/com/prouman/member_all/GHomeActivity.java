package com.prouman.member_all;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.prouman.guest_section.NinjaGuestHome;
import com.squareup.picasso.Picasso;
import com.prouman.R;
import com.prouman.Util.AppConstant;
import com.prouman.Util.ConfigURL;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.Util.SessionManager;
import com.prouman.activity.ContactActivity;
import com.prouman.adapter.RecyclerViewAdapter;
import com.prouman.app.Config;
import com.prouman.contactmanager.ContactList;
import com.prouman.group.GroupListActivity;
import com.prouman.model.HomeItemObjectModel;
import com.prouman.native_form.FormData;
import com.prouman.ninjaforms.DynamicForm;
import com.prouman.ninjaforms.Form;
import com.prouman.ninjaforms.FormDataObject;
import com.prouman.ninjaforms.NativeFormActivity;
import com.prouman.notification.LoginUserNotificationList;
import com.prouman.omspages.PagesMainActivity;
import com.prouman.tagmanager.TagListActivity;
import com.prouman.test_db.TestDbActivity;
import com.prouman.videonewscreen.VideoDirectory;
import com.prouman.videonewscreen.VideoHub;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.prouman.R.id.tv_contactName;
import static com.prouman.R.string.Podcast;
import static com.prouman.R.string.Push;
import static com.prouman.R.string.Webinar;


public class GHomeActivity extends AppCompatActivity {
    String sEmailId;

    String uproAffLink;
    String uproShopLink;
    String upro_ref, upro_id, distributorid;
    // String count;
    ImageView drawImage;
    LinearLayout layout_call, layout_message, layout_website, layout_email, layout_joinus;
    TextView txt_phonenumber, text_email, txt_weburl;
    TextView emailTv, phoneTv, contactName, header_TV;//phoneNumber,//emailName
    ImageView phoneIcon, contactImage, backBtn;//chatIcon,emailIcon
    Button connectContBtn, disConnectBtn;//joinBtn

    private String websiteUrl;
    private ArrayList<FormData> students;
    TextView user_total_prospects, user_growth, user_last24hours_prospects;
    String firstName;
    Button home, logBtn, callBtn, settingBtn, pushBtn;//msg
    String imgUrl;
    String img_name;

    String lastName;
    Button logoutBtn;
    String phone;

    CircleImageView toolImg;
    CircleImageView imageView_profile;
    TextView toolText, titleTv;
    TextView tvLast;
    TextView tvName;
    TextView tvPhoneNumber;
    TextView user_name, txt_Logout, home_left_drawer;
    TextView user_distributerid;
    TextView txt_pages, contact_manager, tags, group_ofcontacts;
    //TextView txtwebsiteurl;


    CircleImageView headerImage;
    String headerName;
    //  TextView copyrightTv;
    //private ImageView broweserIcon;
    RecyclerView rvContacts;
    String deviceid;
    SharedPreferences sharedPreferences;
    private GridLayoutManager lLayout;
    //  BroadcastReceiver mRegistrationBroadcastReceiver;
    RecyclerViewAdapter rcAdapter;
    List<HomeItemObjectModel> rowListItem;

    // ProgressDialog progressDialog;
    SessionManager session;
    //private LinearLayout backToGuest;
    GridView androidGridView;
    // Button btn_website;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    private static final int PERMISSIONS_REQUEST_GETACCOUNT = 1000;
    private TelephonyManager mTelephonyManager;
    MemeberCustomGridView adapterViewAndroid = null;
    AlertDialog alertDialog = null;
  /*  String[] gridViewString = {
            this.getString(R.string.viral_mail),this.getString(R.string.Viral_Message),this.getString(R.string.product_info),
           this.getString(R.string.pro_testominal),this.getString(R.string.private_video),
           this.getString(R.string.shop_product),
           this.getString(R.string.accademy),this.getString(R.string.Funne_business),
           this.getString(R.string.Funnel_prodotto),this.getString(R.string.form),this.getString(R.string.Podcast),
           this.getString(R.string.Webinar),
           this.getString(R.string.Push),this.getString(R.string.join_viral_ninja),this.getString(R.string.kyani),
           this.getString(R.string.vip_card),this.getString(R.string.s_days_pack)};
*/


    private RelativeLayout topLayout;
    private String hash;
    String mPhoneNumber = "";
    String deviceEmailId = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  FirebaseMessaging.getInstance();
        String token = FirebaseInstanceId.getInstance().getToken();
        setContentView(R.layout.activity_main);
        initView();

        final SessionManager sessionManager = new SessionManager(GHomeActivity.this);
        // if (!sessionManager.getIsTokenRegister()) {
        sendToken();
        //}
//        else{}
        // Session class instance
   /*     session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getMemberDetails();
        firstName = user.get(SessionManager.KEY_FNAME);
        lastName =  user.get(SessionManager.KEY_LNAME);
        phone =  user.get(SessionManager.KEY_PHONE);
        img_name =  user.get(SessionManager.KEY_IMAGENAME);
        uproAffLink =  user.get(SessionManager.KEY_UPRO_AFFLINK);
        uproShopLink =  user.get(SessionManager.KEY_UPRO_SHOPLINK);
        sEmailId =  user.get(SessionManager.KEY_EMAIL_ID);
        websiteUrl =  user.get(SessionManager.UPRO_WEBURL);
        upro_id =  user.get(SessionManager.UPRO_ID);*/


        session = new SessionManager(getApplicationContext());
        String status = session.getPreferences(GHomeActivity.this, "status");
        sharedPreferences = this.getSharedPreferences("MyPref", 0);
        user_total_prospects.setText(sharedPreferences.getString(PrefrencesConstant.total_prospects, "0"));
        user_growth.setText(sharedPreferences.getString(PrefrencesConstant.growth_rate, "0%"));
        user_last24hours_prospects.setText(sharedPreferences.getString(PrefrencesConstant.last_prospects, "0"));
        distributorid = sharedPreferences.getString("Lupro_distributor_id", null);
        if (status.equals("1")) {
            upro_id = sharedPreferences.getString(PrefrencesConstant.uproid, null);
            hash = sharedPreferences.getString(PrefrencesConstant.hash, null);
            sharedPreferences.getString(PrefrencesConstant.hash, null);
            firstName = sharedPreferences.getString("FName", null);
            lastName = sharedPreferences.getString("LName", null);
            phone = sharedPreferences.getString("Phone", null);
            img_name = sharedPreferences.getString("imgName", null);
            sEmailId = sharedPreferences.getString("upro_main_email", null);
            websiteUrl = sharedPreferences.getString("upro_subdomain_link", null);
            // txtwebsiteurl.setText(websiteUrl);
        } else {
            Intent intent = getIntent();
            if (intent != null) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    firstName = extras.getString("FName");
                    lastName = extras.getString("LName");
                    phone = extras.getString("Phone");
                    img_name = extras.getString("imgName");
                    uproAffLink = extras.getString("uproAffLink");
                    uproShopLink = extras.getString("uproShopLink");
                    // sEmailId = extras.getString("upro_main_email");
                    websiteUrl = extras.getString("upro_subdomain_link");
                    //    distributorid=
                    upro_id = extras.getString("upro_id");
                }
            } else {
                Toast.makeText(getApplicationContext(), "No Bundle", Toast.LENGTH_LONG).show();
            }
        }

//        backToGuest.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                finish();
//
//            }
//        });
        // headerName = firstName;
        imgUrl = img_name;//ConfigURL.PHOTO_URL +
        Picasso.with(this).load(imgUrl).placeholder(R.drawable.round_corner).into(toolImg);
        Picasso.with(this).load(imgUrl).placeholder(R.drawable.round_corner).into(imageView_profile);
      /*  btn_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(websiteUrl)));
                return;
            }
        });*/
      /*  copyrightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences =GHomeActivity.this.getSharedPreferences("MyPref",0);
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString("Lcopyright_link", ""))));
                return;
            }
        });*/

        // showContacts();
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        // View header = navigationView.getHeaderView(0);
        headerImage = (CircleImageView) findViewById(R.id.imageView);
        Picasso.with(this).load(imgUrl).placeholder(R.drawable.round_corner).into(headerImage);
        headerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GHomeActivity.this, ContactActivity.class));
            }
        });
        TextView headerTitle = (TextView) findViewById(R.id.header_name);
        headerTitle.setText(firstName + " " + lastName);
        TextView headetID = (TextView) findViewById(R.id.memebr_id);
        headetID.setText(distributorid);
        titleTv.setText(firstName + " " + lastName);
        user_name.setText(firstName + " " + lastName);
        user_distributerid.setText(distributorid);
       /* TextView headerPlace = (TextView) header.findViewById(R.id.memebr_place);

        headerPlace.setText("Italy");*/
        txt_phonenumber.setText(phone);
        text_email.setText(sEmailId);
        if (null != websiteUrl) {
            URI uri = null;
            try {
                String strSplit[] = websiteUrl.split("//");
                if (strSplit.length > 1) {
                    txt_weburl.setText(strSplit[1]);
                } else {
                    txt_weburl.setText(strSplit[0]);
                }
                uri = new URI(websiteUrl);
                String path = uri.getPath();

            } catch (URISyntaxException e) {
                e.printStackTrace();
                txt_weburl.setText(websiteUrl);
            }

        }

        toolImg.setOnClickListener(new View.OnClickListener() {
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
        drawImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(GHomeActivity.this, HomeActivity.class);
//                startActivity(i);
                finish();
                //drawer.closeDrawers();
            }
        });
        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GHomeActivity.this);
                alertDialogBuilder.setMessage("Are you sure to logout as Member?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                //  Toast.makeText(GHomeActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                sessionManager.setPreferences(GHomeActivity.this, "status", "0");
                                session.setPreferences(GHomeActivity.this, "L_status", "0");
//                Intent i = new Intent(GHomeActivity.this, HomeActivity.class);
//                startActivity(i);
                                finish();
                            }
                        });


                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  finish();
                        alertDialog.dismiss();
                    }
                });

                alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
//        msg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent i = new Intent(GHomeActivity.this, NotificationList.class);
////                startActivity(i);
////                drawer.closeDrawers();
//            }
//        });
        pushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GHomeActivity.this, LoginUserNotificationList.class);
                i.putExtra("index", 2);
                startActivity(i);
                drawer.closeDrawers();
            }
        });
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
        });
        /*toolImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        //mSearchView.setOnQueryTextListener(listener);
//        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                // checking for type intent filter
//                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
//                    // gcm successfully registered
//                    // now subscribe to `global` topic to receive app wide notifications
//                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
//
//                    sendToken();
//
//                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
//                    // new push notification is received
//
//                    String message = intent.getStringExtra("message");
//
//                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
//
//                  //  txtMessage.setText(message);
//                }
//            }
//        };
        txt_pages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GHomeActivity.this, PagesMainActivity.class));
            }
        });
        group_ofcontacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GHomeActivity.this, GroupListActivity.class));
                //.putExtra("tittle",getResources().getString(R.string.video_capture_pages)));
            }
        });
        tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GHomeActivity.this, TagListActivity.class));
            }
        });
        contact_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GHomeActivity.this, ContactList.class));
            }
        });
        home_left_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        txt_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GHomeActivity.this);
                alertDialogBuilder.setMessage("Are you sure to logout as Member?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                //  Toast.makeText(GHomeActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                sessionManager.setPreferences(GHomeActivity.this, "status", "0");
                                session.setPreferences(GHomeActivity.this, "L_status", "0");
//                Intent i = new Intent(GHomeActivity.this, HomeActivity.class);
//                startActivity(i);
                                finish();
                            }
                        });


                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  finish();
                        alertDialog.dismiss();
                    }
                });

                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    private void initView() {
        //btn_website=(Button)findViewById(R.id.btn_website);
        txt_phonenumber = (TextView) findViewById(R.id.txt_phonenumber);
        user_total_prospects = (TextView) findViewById(R.id.user_total_prospects);
        user_growth = (TextView) findViewById(R.id.user_growth);
        user_last24hours_prospects = (TextView) findViewById(R.id.user_last24hours_prospects);
        txt_phonenumber = (TextView) findViewById(R.id.txt_phonenumber);
        text_email = (TextView) findViewById(R.id.text_email);
        txt_Logout = (TextView) findViewById(R.id.txt_Logout);
        txt_weburl = (TextView) findViewById(R.id.txt_weburl);
        layout_message = (LinearLayout) findViewById(R.id.layout_message);
        layout_website = (LinearLayout) findViewById(R.id.layout_website);
        layout_email = (LinearLayout) findViewById(R.id.layout_email);
        layout_joinus = (LinearLayout) findViewById(R.id.layout_joinus);
        layout_call = (LinearLayout) findViewById(R.id.layout_call);
        phoneIcon = (ImageView) findViewById(R.id.call_icon);
        home_left_drawer = (TextView) findViewById(R.id.home_left_drawer);
        user_name = (TextView) findViewById(R.id.user_name);
        txt_pages = (TextView) findViewById(R.id.txt_pages);
        group_ofcontacts = (TextView) findViewById(R.id.group_ofcontacts);
        contact_manager = (TextView) findViewById(R.id.contact_manager);
        tags = (TextView) findViewById(R.id.tags);
        user_distributerid = (TextView) findViewById(R.id.user_distributerid);
        // chatIcon = (ImageView) findViewById(R.id.msg_icon);
        titleTv = (TextView) findViewById(R.id.titleTv);
        //backToGuest = (LinearLayout) findViewById(R.id.layout_back_to_guest);
        topLayout = ((RelativeLayout) findViewById(R.id.layout_top));
        //  txtwebsiteurl = (TextView) findViewById(R.id.txtwebsiteurl);
        drawImage = (ImageView) findViewById(R.id.menu_icon_iv);
//        phoneNumber = (TextView) findViewById(R.id.phone_number);
        // emailName = (TextView) findViewById(R.id.emailName);
        toolImg = (CircleImageView) findViewById(R.id.layout_logo);
        imageView_profile = (CircleImageView) findViewById(R.id.imageView_profile);
        contactImage = (ImageView) findViewById(R.id.contact_image);
        //joinBtn = (Button) findViewById(R.id.change_language);
        contactName = (TextView) findViewById(R.id.contactName);
        // websiteName = (TextView) findViewById(R.id.website_url_member);
        //copyrightTv = (TextView) findViewById(R.id.copyright_tv_member);
        header_TV = (TextView) findViewById(tv_contactName);
        home = (Button) findViewById(R.id.btn_home);
        //    msg = (Button) findViewById(R.id.btn_demo_info);
        settingBtn = (Button) findViewById(R.id.btn_setting);
        pushBtn = (Button) findViewById(R.id.btn_push);
        logBtn = (Button) findViewById(R.id.btn_logout);
        // broweserIcon = (ImageView) findViewById(R.id.browswer_icon);
        //   emailIcon = (ImageView) findViewById(R.id.email_icon);
        backBtn = (ImageView) findViewById(R.id.back_btn);
        // chatIcon = (ImageView) findViewById(R.id.msg_icon);

      /*  lLayout = new GridLayoutManager(this, 3);
        RecyclerView rView = (RecyclerView) findViewById(R.id.message_recyler);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);
        rcAdapter = new RecyclerViewAdapter(this, rowListItem);
        rView.setAdapter(rcAdapter);*/
       /* rView.addOnItemTouchListener(new com.makeviral.activity.GHomeActivity.RecyclerTouchListener(getApplicationContext(), rView, new ItemClickListener() {
            @Override
            public void onItemClick(int i, View view) {
                Bundle bundle = new Bundle();
                bundle.putString("FName",firstName);
                bundle.putString("LName", lastName);
                bundle.putString("Phone", phone);
                bundle.putString("imgName", img_name);
                Intent intent = new Intent(GHomeActivity.this, ContactActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);


            }

            @Override
            public void onLongClick(View view, int i) {

            }
        }
        ));*/
        layout_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   onCall();
                shareIntent(lastName + " " + firstName + ":" + phone);
            }
        });
        layout_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendSMS();
                //sendEmail();
                shareIntent(lastName + " " + firstName + ":" + sEmailId);
            }
        });
        layout_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(websiteUrl)));
            }
        });

        layout_joinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.memeberKyanilink, ""))));
                startActivity(new Intent(GHomeActivity.this, ContactActivity.class));
            }
        });
        layout_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS();
            }
        });
//        toolImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                final Dialog settingsDialog = new Dialog(GHomeActivity.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
////                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
////
////                settingsDialog.setContentView(R.layout.image_layout);
////                ImageView image = (ImageView) settingsDialog.findViewById(R.id.image_full);
////                Button btnCancel = (Button) settingsDialog.findViewById(R.id.btnCancel);
////                Picasso.with(getApplicationContext()).load(imgUrl).placeholder(R.drawable.full_screen_image).into(image);
////                settingsDialog.show();
////                btnCancel.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        settingsDialog.dismiss();
////                    }
////                });
//                           startActivity(new Intent(GHomeActivity.this,ContactActivity.class));
//            }
//        });
    }


    /* public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

         private GestureDetector gestureDetector;
         private ItemClickListener clickListener;

         public RecyclerTouchListener(this, final RecyclerView recyclerView, final ItemClickListener clickListener) {
             this.clickListener = clickListener;
             gestureDetector = new GestureDetector(co, new GestureDetector.SimpleOnGestureListener() {
                 @Override
                 public boolean onSingleTapUp(MotionEvent e) {
                     return true;
                 }

                 @Override
                 public void onLongPress(MotionEvent e) {
                     View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                     if (child != null && clickListener != null) {
                         clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                     }
                 }
             });
         }

         @Override
         public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

             View child = rv.findChildViewUnder(e.getX(), e.getY());
             if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                 clickListener.onItemClick(rv.getChildPosition(child), child);

             }
             return false;
         }

         @Override
         public void onTouchEvent(RecyclerView rv, MotionEvent e) {
         }

         @Override
         public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

         }
     }*/
    private String getDeviceImei() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS}, PERMISSIONS_REQUEST_GETACCOUNT);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            deviceEmailId = getEmiailID();
        }
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        deviceid = "";//mTelephonyManager.getDeviceId();
        try {
            mPhoneNumber = mTelephonyManager.getLine1Number();
//            if(mPhoneNumber.equals(""))
//            {
//                AccountManager am = AccountManager.get(this);
//                Account[] accounts = am.getAccounts();
//
//                for (Account ac : accounts) {
//                    String acname = ac.name;
//                    String actype = ac.type;
//                    if(actype.equals("com.whatsapp")){
//                        mPhoneNumber = ac.name;
//                    }
//                    // Take your time to look at all available accounts
//                   // System.out.println("Accounts : " + acname + ", " + actype);
//                }
//            }
////            if(mPhoneNumber.equalsIgnoreCase(""))
////            {
////                SubscriptionManager subscriptionManager = (SubscriptionManager) getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
////               for (int i=0;i<subscriptionManager.getActiveSubscriptionInfoList().size();i++)
////               {
////                   SubscriptionInfo s=subscriptionManager.getActiveSubscriptionInfoList().get(i);
////                   mPhoneNumber=mTelephonyManager.getLine1Number();
////               }
////                final String rawNumber =  mTelephonyManager.getLine1Number();
//  // }
        } catch (Exception e) {
        }

        Log.d("msg", "DeviceImei " + deviceid);
        return deviceid;
    }

    private String getEmiailID() {
        AccountManager accountManager = AccountManager.get(getApplicationContext());
        Account account = getAccount(accountManager);
        if (account == null) {
            return null;
        } else {
            return account.name;
        }
    }

    private static Account getAccount(AccountManager accountManager) {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }

    public void sendToken() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSIONS_REQUEST_READ_PHONE_STATE);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            getDeviceImei();
            final SessionManager sessionManager = new SessionManager(GHomeActivity.this);
            final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Login Into Member Account......");
            progressDialog.show();
            SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
            final String regId = pref.getString("regId", null);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.registerDevice, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("success").equals("true")) {
                            sessionManager.setIsTokenRegister(true);
                            AppConstant.count = Integer.parseInt(jsonObject.getString("push_count"));
                            // count=String.valueOf(AppConstant.count);
                            getForms();
                        } else {
                            getForms();
                        }
                        Log.e("Token:", jsonObject.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                        getForms();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    getForms();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    super.getParams();
                    Map<String, String> params = new HashMap<>();
                    SharedPreferences sharedPreferences = GHomeActivity.this.getSharedPreferences("MyPref", 0);

                    String uproID = sharedPreferences.getString(PrefrencesConstant.uproid, null);
                    params.put("upro_id", uproID);
                    params.put("hash", sharedPreferences.getString(PrefrencesConstant.hash, null));
                    params.put("device", "a");
                    params.put("imei", getDeviceImei());
                    params.put("email", "");
                    params.put("phone", mPhoneNumber);
                    params.put("reg_id", regId);//sessionManager.getGCMToken());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(GHomeActivity.this);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if ((requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE || requestCode == PERMISSIONS_REQUEST_GETACCOUNT)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendToken();
        } else {
            //  Toast.makeText(this, "Until you grant the permission, we canot display the notifications", Toast.LENGTH_SHORT).show();
            getForms();
        }
    }

    public void getForms() {
        final SessionManager sessionManager = new SessionManager(GHomeActivity.this);
        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Getting home icons......");
        progressDialog.show();
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        final String regId = pref.getString("regId", null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.GETHOME_Forms, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                try {
                    //Parsing the fetched Json String to JSON Object
                    j = new JSONObject(response);
                    Gson gson = new Gson();
                    FormDataObject formDataObject = gson.fromJson(response, FormDataObject.class);
                    //Storing the Array of JSON String to our JSON Array
                    // result = j.getJSONArray("forms");

                    //Calling method getStudents to get the students from the JSON Array
                    getAllItemList(formDataObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    getAllItemList(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                getAllItemList(null);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> params = new HashMap<>();
                SharedPreferences sharedPreferences = GHomeActivity.this.getSharedPreferences("MyPref", 0);
                String uproID = sharedPreferences.getString(PrefrencesConstant.uproid, null);
                params.put("upro_id", uproID);
                params.put("hash", sharedPreferences.getString(PrefrencesConstant.hash, null));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(GHomeActivity.this);
        requestQueue.add(stringRequest);
    }

    private void getAllItemList(FormDataObject dataObject) {
        rowListItem = new ArrayList();
        Form form = null;
        rowListItem.add(new HomeItemObjectModel(R.string.viral_mail, R.drawable.mail_icon, "", "", form));
        rowListItem.add(new HomeItemObjectModel(R.string.Viral_Message, R.drawable.sms_icon, "", "", form));
        //  rowListItem.add(new HomeItemObjectModel(R.string.product_info, R.drawable.icon_product_info,"","",form));
        //rowListItem.add(new HomeItemObjectModel(R.string.pro_testominal, R.drawable.icon_product_testimonial,"","",form));
        //rowListItem.add(new HomeItemObjectModel(R.string.business_testominals, R.drawable.icon_business_testimonial,"","",form));
        rowListItem.add(new HomeItemObjectModel(R.string.video_text, R.drawable.video_icon, "", "", form));
        rowListItem.add(new HomeItemObjectModel(R.string.video_hub, R.drawable.tutti_video_icon, "", "", form));
        //rowListItem.add(new HomeItemObjectModel(R.string.shop_product, R.drawable.icon_shop,"","",form));
        //rowListItem.add(new HomeItemObjectModel(R.string.accademy, R.drawable.icon_accademy,"","",form));
        //allItems.add(new HomeItemObjectModel(R.string.Funne_business,R.drawable.funnel_business));
        //allItems.add(new HomeItemObjectModel(R.string.Funnel_prodotto, R.drawable.funnel_prodicts));
        rowListItem.add(new HomeItemObjectModel(R.string.form, R.drawable.form_icon, "", "", form));
        if (dataObject != null && dataObject.getForms() != null) {
            for (int i = 0; i < dataObject.getForms().size(); i++) {
                Form dform = dataObject.getForms().get(i);
                rowListItem.add(new HomeItemObjectModel(0, 0, dform.getApp_home_icon(), dform.getGetApp_home_title(), dform));
            }
        }
        // rowListItem.add(new HomeItemObjectModel(Podcast, R.drawable.icon_podcast,"","",form));
        //  rowListItem.add(new HomeItemObjectModel(Webinar, R.drawable.icon_webinar,"","",form));
        rowListItem.add(new HomeItemObjectModel(R.string.join_kyani, R.drawable.round_prouman, "", "", form));
        rowListItem.add(new HomeItemObjectModel(R.string.shop_prouman, R.drawable.shop_icon, "", "", form));
        rowListItem.add(new HomeItemObjectModel(R.string.life_wave, R.drawable.life_wave, "", "", form));
        rowListItem.add(new HomeItemObjectModel(R.string.shop_life_wave, R.drawable.shop_life_wave, "", "", form));
        rowListItem.add(new HomeItemObjectModel(R.string.notification, R.drawable.notification_icon, "", "", form));
        //rowListItem.add(new HomeItemObjectModel(R.string.viral_ninja, R.drawable.icon_viral_ninja,"","",form));
        //rowListItem.add(new HomeItemObjectModel(R.string.kyani_main, R.drawable.icon_kyani,"","",form));
//        rowListItem.add(new HomeItemObjectModel(R.string.prova, R.drawable.icon_pro,"","",form));
        //allItems.add(new HomeItemObjectModel(R.string.vip_card, R.drawable.vip_card));
        //  allItems.add(new HomeItemObjectModel(R.string.s_days_pack, R.drawable.seven_day));

        adapterViewAndroid = new MemeberCustomGridView(GHomeActivity.this, rowListItem);
        androidGridView = (GridView) findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                SharedPreferences sharedPreferences = GHomeActivity.this.getSharedPreferences("MyPref", 0);
                switch (rowListItem.get(position).getName()) {
                    case R.string.viral_mail:
                        Intent intent = new Intent(GHomeActivity.this, TestDbActivity.class);
                        intent.putExtra("from", "emailclick");
                        startActivity(intent);
                        break;
                    case R.string.Viral_Message:
                        Intent inten = new Intent(GHomeActivity.this, TestDbActivity.class);
                        inten.putExtra("from", "vclick");
                        startActivity(inten);
                        break;
                    case R.string.product_info:
                        startActivity(new Intent(GHomeActivity.this, ProductInfoCategory.class));

                        break;
                    case R.string.pro_testominal:
                        startActivity(new Intent(GHomeActivity.this, ProductTestominal.class));
                        break;
                    case R.string.business_testominals:
                        Bundle args = new Bundle();
                        args.putString("catID", "47");
                        Intent inte = new Intent(GHomeActivity.this, MemSecVideoActivity.class);
                        AppConstant.videoscreentitle = getString(R.string.business_testominals);
                        inte.putExtras(args);
                        startActivity(inte);
                        break;
                    case R.string.video_text:
                        Bundle bundle = new Bundle();
                        bundle.putString("catID", "48");
                        Intent video = new Intent(GHomeActivity.this, VideoDirectory.class);
                        AppConstant.videoscreentitle = getString(R.string.premium_video);
                        video.putExtras(bundle);
                        startActivity(video);
                        break;
                    case R.string.video_hub:
                        Bundle hbundle = new Bundle();
                        hbundle.putString("videoId", "");
                        hbundle.putString("title", getString(R.string.video_hub));
                        Intent hvideo = new Intent(GHomeActivity.this, VideoHub.class);
                        AppConstant.videoscreentitle = getString(R.string.premium_video);
                        hvideo.putExtras(hbundle);
                        startActivity(hvideo);
                        break;
                    case R.string.shop_product:
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString("Lupro_subdomain_link", ""))));
                        break;
                    case R.string.shop_life_wave:
                        if (!"null".equalsIgnoreCase(sharedPreferences.getString("Lupro_subdomain_link", "null"))) {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString("Lupro_subdomain_link", ""))));
                        } else {
                            Toast.makeText(GHomeActivity.this, "Link is empty", Toast.LENGTH_LONG).show();
                        }
                        //    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.shop_link, ""))));
                        break;
                    case R.string.life_wave:
                        if (!"null".equalsIgnoreCase(sharedPreferences.getString("G_uproAffLink", "null"))) {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString("G_uproAffLink", ""))));
                        } else {
                            Toast.makeText(GHomeActivity.this, "Link is empty", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.string.accademy:
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString("Lacademy_link", ""))));

                        break;
                    case R.string.Funne_business:
                        //startActivity(new Intent(GHomeActivity.this,FunnelBusinessActivity.class));
                        break;
                    case R.string.form:
                        startActivity(new Intent(GHomeActivity.this, NativeFormActivity.class));

                        break;
                    case R.string.Funnel_prodotto:
                        //  startActivity(new Intent(GHomeActivity.this,FunnelProdottoActivity.class));
                        break;
                    case Podcast:
                        try {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString("Lpodcast_link", ""))));
                        } catch (Exception e) {
                            Toast.makeText(GHomeActivity.this, "error in link", Toast.LENGTH_LONG).show();
                        }
                        //startActivity(new Intent(GHomeActivity.this,PodcastActivity.class));
                        break;
                    case Webinar:
                        startActivity(new Intent(GHomeActivity.this, WebinarActivity.class));
                        break;

                    case R.string.join_kyani:
                        if (!"null".equalsIgnoreCase(sharedPreferences.getString(PrefrencesConstant.oms_referral_nocopy_link, "null"))) {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.oms_referral_nocopy_link, ""))));
                        } else {
                            Toast.makeText(GHomeActivity.this, "Link is empty", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.string.notification:
                        startActivity(new Intent(GHomeActivity.this, LoginUserNotificationList.class));
                        break;
                    case R.string.viral_ninja:
                        // startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.memberviralninjalink,""))));
                        if (!"null".equalsIgnoreCase(sharedPreferences.getString("upro_affiliation_link", "null"))) {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString("upro_affiliation_link", ""))));
                        } else {
                            Toast.makeText(GHomeActivity.this, "Link is empty", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.string.kyani_main:
                        //  startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.memeberKyanilink, ""))));
                        break;
                    case R.string.vip_card:
                        //  startActivity(new Intent(GHomeActivity.this,VipCardActivity.class));
                        break;

                    case R.string.s_days_pack:
                        // startActivity(new Intent(GHomeActivity.this,SevenDayActivity.class));
                        break;
                    case R.string.shop_prouman:
                        if (!"null".equalsIgnoreCase(sharedPreferences.getString(PrefrencesConstant.shop_link, "null"))) {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.shop_link, ""))));
                        } else {
                            Toast.makeText(GHomeActivity.this, "Link is empty", Toast.LENGTH_LONG).show();
                        }

                        //    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.shop_link, ""))));
                        break;
                    default:
                        Intent dynamicintent = new Intent(GHomeActivity.this, DynamicForm.class);
                        dynamicintent.putExtra("form_fields", rowListItem.get(position).getForm());
                        startActivity(dynamicintent);
                        break;

                }

                // Toast.makeText(GHomeActivity.this, "GridView Item: " +rowListItem.get(i).getName(), Toast.LENGTH_LONG).show();
            }
        });
    }


//    @Override
//    protected void onPause() {
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
//        super.onPause();
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        // register GCM registration complete receiver
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(Config.REGISTRATION_COMPLETE));
//
//        // register new push message receiver
//        // by doing this, the activity will be notified each time a new message arrives
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(Config.PUSH_NOTIFICATION));
//
//        // clear the notification area when the app is opened
//        NotificationUtils.clearNotifications(getApplicationContext());
//    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppConstant.isLanguagechanged) {
            AppConstant.isLanguagechanged = false;
            AppConstant.setLanguageForApp(session.getString(AppConstant.STRINGLANGUAGE, ""), GHomeActivity.this);
            finish();
            startActivity(getIntent());
        }
        if (adapterViewAndroid != null) {
            adapterViewAndroid.notifyDataSetChanged();
        }
    }

    private void onCall() {
        if (Build.VERSION.SDK_INT >= 19) {
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            Intent sendIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
            sendIntent.putExtra("address", phone);
            if (tm != null && tm.getSimState() == 5) {
                startActivity(sendIntent);
                return;
            }
            return;
        }
        Intent callIntent = new Intent("android.intent.action.DIAL");
        callIntent.setPackage("com.android.phone");
        callIntent.setData(Uri.parse(phone));
        startActivity(callIntent);
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            return;

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(GHomeActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendSMS() {

        if (Build.VERSION.SDK_INT >= 19) {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this);
            Intent sendIntent = new Intent("android.intent.action.SEND", Uri.parse("tel:" + phone));
            sendIntent.putExtra("address", phone);
            sendIntent.setType("text/plain");
            sendIntent.putExtra("android.intent.extra.TEXT", "");
            if (defaultSmsPackageName != null) {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            startActivity(sendIntent);
            return;
        }
        Intent smsIntent = new Intent("android.intent.action.VIEW");
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", phone);
        smsIntent.putExtra("sms_body", "");
        startActivity(smsIntent);
    }

    private void shareIntent(String share) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, share);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}

