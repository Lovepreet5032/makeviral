package com.prouman.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony.Sms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prouman.guest_section.NinjaGuestHome;
import com.prouman.member_all.GHomeActivity;
import com.squareup.picasso.Picasso;
import com.prouman.R;
import com.prouman.Util.AppConstant;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.Util.SessionManager;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import de.hdodenhof.circleimageview.CircleImageView;


public class ContactActivity extends AppCompatActivity implements View.OnClickListener{


    Button homeBtn;
    String imgUrl;
    String img_name;
    String lastname;
    Button logoutBtn;
    AppCompatActivity mActivity;
    String name;
    String phone;
    CircleImageView toolImg;
    TextView toolText;
    Toolbar toolbar;
    TextView tvLast;
    TextView tvName;
    TextView tvPhoneNumber;
    ProgressDialog pDialog;
    private RelativeLayout backToGuest;
    private RelativeLayout bottomLayout;
    private boolean isShowShareLayout;
    private ProgressBar loadProgress;
    private ImageView fb_share,skype_share,linkedin_share,google_share,youtube_share;

    private RelativeLayout shareLayout;
    private TextView title;
    private RelativeLayout topLayout;
    private ImageView image_back;
    private SessionManager session;
    private Button chng_btn;
    private TextView language;
    private LinearLayout llShopLifeWave,llSignUpLifeWave,llShopProuman,llIscriptivProuman;
    private String strWebsiteurl="";
    private String strDistributorid="";
    private TextView txt_distributorid;
    private SharedPreferences sharedPreferences;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        session=new SessionManager(getApplicationContext());

        llShopLifeWave= findViewById(R.id.llShopLifeWave);
        llSignUpLifeWave= findViewById(R.id.llSignUpLifeWave);
        llShopProuman= findViewById(R.id.llShopProuman);
        llIscriptivProuman= findViewById(R.id.llIscriptivProuman);

        sharedPreferences =ContactActivity.this.getSharedPreferences("MyPref",0);
        llShopLifeWave.setOnClickListener(v -> {
            if(!"null".equalsIgnoreCase(sharedPreferences.getString("shop_link", "null"))) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString("shop_link", ""))));
            }
            else {Toast.makeText(ContactActivity.this,"Link is empty",Toast.LENGTH_LONG).show();}
        });
        llSignUpLifeWave.setOnClickListener(v -> {
            if(!"null".equalsIgnoreCase(sharedPreferences.getString("G_uproAffLink", "null"))) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString("G_uproAffLink", ""))));
            }
            else {Toast.makeText(ContactActivity.this,"Link is empty",Toast.LENGTH_LONG).show();}
        });
        llShopProuman.setOnClickListener(v -> {
            if(!"null".equalsIgnoreCase(sharedPreferences.getString(PrefrencesConstant.shop_pro_link, "null"))) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.shop_pro_link, ""))));
            }
            else {Toast.makeText(ContactActivity.this,"Link is empty",Toast.LENGTH_LONG).show();}
        });
        llIscriptivProuman.setOnClickListener(v -> {
            if(!"null".equalsIgnoreCase(sharedPreferences.getString(PrefrencesConstant.oms_referral_nocopy_link, "null"))) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.oms_referral_nocopy_link, ""))));
            }
            else {Toast.makeText(ContactActivity.this,"Link is empty",Toast.LENGTH_LONG).show();}
        });

        chng_btn=findViewById(R.id.chng_btn);
        language=findViewById(R.id.language);
        language.setText(R.string.label_text);
        chng_btn.setText(R.string.btn_change_language);
        chng_btn.setOnClickListener(view -> showLanguageChangePopup());
       /* toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Contact");*/
       SharedPreferences sharedPreferences = this.getSharedPreferences("MyPref", 0);
        Intent intent = getIntent();
       // if (intent != null) {
            Bundle extras = intent.getExtras();
           String userhash= sharedPreferences.getString(PrefrencesConstant.hash, "");
        if("" == userhash){
            strDistributorid=sharedPreferences.getString(PrefrencesConstant.guestDistributorId,null);
            strWebsiteurl= sharedPreferences.getString(PrefrencesConstant.guestWebsite,null);
            phone =sharedPreferences.getString(PrefrencesConstant.guestlastPhone,null);
            img_name = sharedPreferences.getString(PrefrencesConstant.guestImagename, null);
            name = sharedPreferences.getString(PrefrencesConstant.guestfirstname, "");
            lastname= sharedPreferences.getString(PrefrencesConstant.guestlastname, null);
            phone =sharedPreferences.getString(PrefrencesConstant.guestlastPhone,null);
        }
        else
            {
                name = sharedPreferences.getString("FName", "");
                lastname= sharedPreferences.getString("LName", null);
                phone =sharedPreferences.getString("L_Phone",null);
                img_name = sharedPreferences.getString("imgName", null);
                strDistributorid=sharedPreferences.getString("Lupro_distributor_id",null);
                strWebsiteurl= sharedPreferences.getString("upro_subdomain_link", null);
            }

            //phone = sharedPreferences.getString("Phone", null);

            //websiteUrl = sharedPreferences.getString("upro_subdomain_link", null);

         //   phone = extras.getString("Phone");
           // img_name = extras.getString("imgName");
//        } else {
//            Toast.makeText(getApplicationContext(), "No Bundle", Toast.LENGTH_LONG).show();
//        }
        logoutBtn =  findViewById(R.id.logoutBtn);
        homeBtn =  findViewById(R.id.homeBtn);
        tvName =  findViewById(R.id.tvName);
        image_back=findViewById(R.id.image_back);
        tvName.setText(name+" "+ lastname);
        txt_distributorid =  findViewById(R.id.txt_distributorid);
        txt_distributorid.setText(strDistributorid);
        tvLast =  findViewById(R.id.tvLast);
        tvLast.setText(strWebsiteurl);
        tvLast.setOnClickListener(this);
        tvPhoneNumber =  findViewById(R.id.tvPhone);
        tvPhoneNumber.setText(phone);
        tvPhoneNumber.setOnClickListener(view -> {
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
        });
        String phone_number = tvPhoneNumber.getText().toString();
        title = findViewById(R.id.title);
//        title.setText(upro_title);
        backToGuest = findViewById(R.id.layout_back_to_guest);
        topLayout = ((RelativeLayout)findViewById(R.id.layout_top));
        toolImg =(CircleImageView) findViewById(R.id.contact_view);
        fb_share=(ImageView)findViewById(R.id.fb_share);
        skype_share=(ImageView)findViewById(R.id.skype_share);
        linkedin_share=(ImageView)findViewById(R.id.linkedin_share);
        google_share=(ImageView)findViewById(R.id.google_share);
        youtube_share=(ImageView)findViewById(R.id.youtube_share);
        fb_share.setOnClickListener(this);
        skype_share.setOnClickListener(this);
        linkedin_share.setOnClickListener(this);
        google_share.setOnClickListener(this);
        youtube_share.setOnClickListener(this);
        imgUrl =  img_name;//ConfigURL.PHOTO_URL +
        Picasso.with(this).load(imgUrl).placeholder(R.drawable.round_corner).into(toolImg);
        toolImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog settingsDialog = new Dialog(ContactActivity.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

                settingsDialog.setContentView(R.layout.image_layout);
                ImageView image = (ImageView) settingsDialog.findViewById(R.id.image_full);
                Button btnCancel = (Button) settingsDialog.findViewById(R.id.btnCancel);
                Picasso.with(getApplicationContext()).load(imgUrl).placeholder(R.drawable.full_screen_image).into(image);
                settingsDialog.show();
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settingsDialog.dismiss();
                    }
                });

            }
        });
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void sendSMS() {
        if (Build.VERSION.SDK_INT >= 19) {
            String defaultSmsPackageName = Sms.getDefaultSmsPackage(this);
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
        smsIntent.putExtra("sms_body", "message");
        startActivity(smsIntent);
    }

    private void onCall() {
        if (Build.VERSION.SDK_INT >= 19) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        123);
            } else {
                startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + phone)));
            }
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    Log.d("TAG", "Call Permission Not Granted");
                } else {
                    onCall();
                }
            default:
        }
    }

    @Override
    public void onClick(View view) {
        SharedPreferences sharedPreferences =this.getSharedPreferences("MyPref",0);
        //sharedPreferences.getString("G_FName", "");
//        editor.putString("facebook_link",);
//        editor.putString("twitter_link",sharedPreferences.getString("twitter_link"));
//        editor.putString("google_link",sharedPreferences.getString("google_link"));
//        editor.putString("youtube_link",sharedPreferences.getString("youtube_link"));
//        editor.putString("pinterest_link",sharedPreferences.getString("pinterest_link"));
//        editor.putString("linkedin_link",sharedPreferences.getString("linkedin_link"));
//        editor.putString("vimeo_link",sharedPreferences.getString("vimeo_link"));
//        editor.putString("instagram_link",sharedPreferences.getString("instagram_link"));
//        editor.putString("skype_link",sharedPreferences.getString("skype_link"));
        switch (view.getId())
        {
            case R.id.fb_share:
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/proumancommunity/")));
//                shareLink(sharedPreferences.getString("facebook_link",""),"com.facebook.katana");
                break;
            case R.id.skype_share:
                shareLink(sharedPreferences.getString("skype_link",""),"com.skype.raider");
                break;
            case R.id.linkedin_share:
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.instagram.com/prouman/?hl=it")));
//                shareLink(sharedPreferences.getString("linkedin_link",""),"com.linkedin.android");
                break;
            case R.id.google_share:
                shareLink(sharedPreferences.getString("google_link",""),"com.google.android.apps.plus");
                break;
            case R.id.youtube_share:
                shareLink(sharedPreferences.getString("youtube_link",""),"com.google.android.youtube");
//                if(""!=sharedPreferences.getString("youtube_link","")) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sharedPreferences.getString("youtube_link", ""))));
//                }
//                else {
//                Toast.makeText(ContactActivity.this,"No link available",Toast.LENGTH_LONG).show();}
                    break;
            case R.id.layout_shop:

                if(!"null".equalsIgnoreCase(sharedPreferences.getString(PrefrencesConstant.oms_referral_nocopy_link, "null"))) {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.oms_referral_nocopy_link, ""))));
                }
                else {Toast.makeText(ContactActivity.this,"Link is empty",Toast.LENGTH_LONG).show();}
             break;
            case R.id.layout_joinkyani:
                if(!"null".equalsIgnoreCase(sharedPreferences.getString("upro_affiliation_link", "null"))) {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString("upro_affiliation_link", ""))));
                }
                //startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.guestuproasfflink, ""))));
              //  startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.viralninjalink,""))));
                break;
            case R.id.viral_ninja:
                if(!"null".equalsIgnoreCase(sharedPreferences.getString(PrefrencesConstant.guestuproasfflink, "null"))) {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(sharedPreferences.getString(PrefrencesConstant.guestuproasfflink, ""))));
                }
                else {Toast.makeText(ContactActivity.this,"Link is empty",Toast.LENGTH_LONG).show();}

                break;
            case R.id.tvLast:
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(strWebsiteurl)));
                break;
        }
    }
    private void shareLink(String urlToShare,String packageName)
    {
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/plain");
//// intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
//        intent.putExtra(Intent.EXTRA_TEXT, urlToShare);
//
//// See if official Facebook app is found
//        boolean facebookAppFound = false;
//        List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
//        for (ResolveInfo info : matches) {
//            if (info.activityInfo.packageName.toLowerCase().startsWith(packageName)) {
//                intent.setPackage(info.activityInfo.packageName);
//                facebookAppFound = true;
//                break;
//            }
//        }
//
//// As fallback, launch sharer.php in a browser
//        if (!facebookAppFound) {
//            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
//            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
//        }
//
//        startActivity(intent);
//        if(null!=urlToShare && ""!=urlToShare) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(urlToShare)));
            }
            catch (Exception e)
            {
                Toast.makeText(ContactActivity.this,"Link not available",Toast.LENGTH_LONG).show();}
            }
//            }
//        else{
//            Toast.makeText(ContactActivity.this,"Link not available",Toast.LENGTH_LONG).show();}
//        }
private void showLanguageChangePopup() {
    CharSequence languages[] = new CharSequence[]{
            "English",
            "Italiano (Italian)",
            "Turkish",
            "German"
    };
    final String codes[] = new String[]{
            "en",
            "it",
            "tr",
            "gr"
    };

    String currentLangIndex = session.getString(AppConstant.STRINGLANGUAGE,"");
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(R.string.text_select_language);
    boolean haslanguage=false;
    for(int i=0;i<codes.length;i++) {
        if(codes[i].equalsIgnoreCase(currentLangIndex)) {
            haslanguage=true;
            builder.setSingleChoiceItems(languages, i, null);
        }
    }
    if(!haslanguage)
    {
        builder.setSingleChoiceItems(languages,1, null);
    }
    builder.setNegativeButton("Cancel", null);
    builder.setPositiveButton(R.string.text_select_language, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            int selectedIndex = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
            AppConstant.isLanguagechanged=true;
            session.put(AppConstant.STRINGLANGUAGE,codes[selectedIndex]);
            AppConstant.setLanguageForApp(session.getString(AppConstant.STRINGLANGUAGE,""),ContactActivity.this);
            startActivity(getIntent());
            finish();

        }
    });

    builder.show();
}
}