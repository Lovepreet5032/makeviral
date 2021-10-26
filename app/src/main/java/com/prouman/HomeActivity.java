package com.prouman;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.prouman.Util.AppConstant;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.Util.SessionManager;
import com.prouman.activity.MemberLoginActivity;
import com.prouman.guest_section.GuestLoginActivity;
import com.prouman.guest_section.NinjaGuestHome;
import com.prouman.member_all.GHomeActivity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
  //  private ImageView backBtn;
    private Button btn_loginasuser,btn_loginasguest;
    AppCompatTextView chng_btn;
    SessionManager manager;
    String L_status;
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
   // ImageView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        manager=new SessionManager(this);
      //  String uri = "android.resource://" + getPackageName() + "/" + R.raw.sunset;
        String uriGif = "android.resource://" + getPackageName() + "/" + R.raw.giphy;
     //   mVideoView= (ImageView)findViewById(R.id.videoViewsplashbackground);

//        DisplayMetrics metrics = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        android.widget.FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) mVideoView.getLayoutParams();
//        params.width =  metrics.widthPixels;
//        params.height = metrics.heightPixels;
//        params.leftMargin = 0;
//        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.setLooping(true);
//            }
//        });
//        if (mVideoView != null)
//        {  mVideoView.setVideoURI(Uri.parse(uri));
//            mVideoView.requestFocus();
//            mVideoView.start();
//           // mVideoView.setLayoutParams(params);
//        } else
//        { //toast or print "mVideoView is null"
//        }

       //Glide.with(this).load(uriGif).into(new DrawableImageViewTarget(mVideoView));
        String G_status=manager.getPreferences(HomeActivity.this,"G_status");
        boolean  bguest=getIntent().getBooleanExtra("bguest",false);
        boolean  bmember=getIntent().getBooleanExtra("bmember",false);
        if (manager.getPreferences(HomeActivity.this,"L_status").equals("1")  && bmember) {
            redirectScreen(true);
        }
        else  if(G_status.equals("1") &&bguest)
        {
            redirectScreen(false);
        }

    //    backBtn=(ImageView)findViewById(R.id.back_btn);
        btn_loginasuser=(Button) findViewById(R.id.btn_loginasuser);
        chng_btn=findViewById(R.id.chng_btn);
        chng_btn.setText(R.string.btn_change_language);
        chng_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLanguageChangePopup();
            }
        });
        btn_loginasguest=(Button)findViewById(R.id.btn_loginasguest);
        btn_loginasguest.setOnClickListener(this);
        btn_loginasuser.setOnClickListener(this);
        //backBtn.setOnClickListener(this);
        L_status =manager.getPreferences(HomeActivity.this,"L_status");
        Log.d("status",L_status);
        if(null != getIntent() && null != getIntent().getExtras() && !getIntent().getExtras().getBoolean("fromn",false)) {
            if (L_status.equals("1") && bmember) {
                redirectScreen(true);
            } else if(G_status.equals("1") &&bguest){
                redirectScreen(false);
            }
        }
        mDemoCollectionPagerAdapter =
                new DemoCollectionPagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
//                       if(position==0){
//                           btn_loginasuser.setBackgroundResource(R.drawable.round_button_selected);
//                           btn_loginasguest.setBackgroundResource(R.drawable.bacgroundhomebtn);
//                       }
//                       else if(position==1){
//                           btn_loginasuser.setBackgroundResource(R.drawable.bacgroundhomebtn);
//                           btn_loginasguest.setBackgroundResource(R.drawable.round_button_selected);
//                       }
                    }
                });

        if(getIntent().getExtras().getBoolean("bguest",false)){
            mViewPager.setCurrentItem(1);
        }
        else if(getIntent().getExtras().getBoolean("bmember",false)){
            mViewPager.setCurrentItem(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mVideoView.start();
    }

    @Override
    public void onClick(View view) {


        SharedPreferences sharedPreferences =this.getSharedPreferences("MyPref",0);
//        if (L_status.equals("1")) {
            switch (view.getId()) {
                case R.id.back_btn:
                    finish();
                    break;
                case R.id.btn_loginasuser:
                    // After 5 seconds redirect to another intent
                  //  btn_loginasuser.setBackgroundResource(R.drawable.round_button_selected);
                    //btn_loginasguest.setBackgroundResource(R.drawable.bacgroundhomebtn);
//                    else {
//                        Intent i = new Intent(HomeActivity.this, MemberLoginActivity.class);
//                        startActivity(i);
                        //finish();
                        mViewPager.setCurrentItem(0);
                 //   }
                    break;
                case R.id.btn_loginasguest:
                  //  btn_loginasuser.setBackgroundResource(R.drawable.bacgroundhomebtn);
                    //btn_loginasguest.setBackgroundResource(R.drawable.round_button_selected);

//                    else
//                {
//                    Intent intent = new Intent(HomeActivity.this, GuestLoginActivity.class);
//                    startActivity(intent);
                    //finish();
                    mViewPager.setCurrentItem(1);
              //  }

                break;
            }
        }
    public void redirectScreen(boolean frombtnMember)
    {String L_status=manager.getPreferences(HomeActivity.this,"L_status");
        Log.d("status",L_status);
        String G_status=manager.getPreferences(HomeActivity.this,"G_status");
        SharedPreferences sharedPreferences =this.getSharedPreferences("MyPref",0);
        if (L_status.equals("1")&&frombtnMember){

            // session.createMemberLoginSession(firstName,lastName,phone,imgName,upro_id,websiteUrl,hash,affLink,shopLink,emailId);
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(PrefrencesConstant.uproid, sharedPreferences.getString("L_uproId",""));
            editor.putString(PrefrencesConstant.hash,sharedPreferences.getString("L_hash",""));
            editor.putString("FName", sharedPreferences.getString("L_FName",""));
            editor.putString("LName",sharedPreferences.getString("L_LName",""));
            editor.putString("Phone", sharedPreferences.getString("L_Phone",""));
            editor.putString("imgName",sharedPreferences.getString("L_imgName",""));
            editor.putString("upro_subdomain_link",sharedPreferences.getString("L_upro_subdomain_link",""));
         //   editor.putString("Lpodcast_link",sharedPreferences.getString("podcast_link",""));
            //editor.putString("upro_id",hash);
            editor.commit();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("FName",  sharedPreferences.getString("L_FName",""));
//                    bundle.putString("LName", sharedPreferences.getString("L_LName",""));
//                    bundle.putString("Phone", sharedPreferences.getString("L_Phone",""));
//                    bundle.putString("imgName",sharedPreferences.getString("L_imgName",""));
//                    bundle.putString("upro_id", sharedPreferences.getString("L_uproId",""));
//                    bundle.putString("upro_subdomain_link", sharedPreferences.getString("L_upro_subdomain_link",""));
            Intent i=new Intent(HomeActivity.this,GHomeActivity.class);
            startActivity(i);

            finish();
        }
      else if(G_status.equals("1")&&!frombtnMember) {
            Bundle bundle = new Bundle();
            bundle.putString("FName", sharedPreferences.getString(PrefrencesConstant.guestfirstname, ""));
            bundle.putString("LName", sharedPreferences.getString(PrefrencesConstant.guestlastname, ""));
            bundle.putString("Phone", sharedPreferences.getString(PrefrencesConstant.guestlastPhone, ""));
            bundle.putString("imgName", sharedPreferences.getString(PrefrencesConstant.guestImagename, ""));
            bundle.putString("uproRef", sharedPreferences.getString(PrefrencesConstant.guestuproref, ""));
            bundle.putString("uproAffLink", sharedPreferences.getString(PrefrencesConstant.guestuproasfflink, ""));
            bundle.putString("uproShopLink", sharedPreferences.getString(PrefrencesConstant.guestuproshoplink, ""));
            bundle.putString("upro_main_email", sharedPreferences.getString(PrefrencesConstant.guestmainemail, ""));
            bundle.putString(PrefrencesConstant.uproid, sharedPreferences.getString(PrefrencesConstant.guestgproid, ""));
            bundle.putString("websiteUrl", sharedPreferences.getString(PrefrencesConstant.guestWebsite, ""));
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            //editor.putString(PrefrencesConstant.guestgproid, uproID);
//                    editor.putString("G_FName", firstName);
//                    editor.putString("G_LName", lastName);
//                    editor.putString("G_Phone", phone);
//                    editor.putString("G_imgName", imgName);
//                    editor.putString("G_uproRef", uproRef);
//                    editor.putString("G_uproAffLink", affLink);
//                    editor.putString("G_uproShopLink", shopLink);
//                    editor.putString("G_upro_main_email",emailId);
//                    editor.putString(PrefrencesConstant.guestgproid, uproID);
//                    editor.putString("G_websiteUrl", websiteUrl);
            editor.putString(PrefrencesConstant.uproid, sharedPreferences.getString(PrefrencesConstant.guestgproid, ""));
            editor.putString(PrefrencesConstant.hash,"");
            editor.commit();
            // session.setPreferences(HomeActivity.this, "G_status", "1");
            Intent intent = new Intent(HomeActivity.this, NinjaGuestHome.class);
            intent.putExtras(bundle);
            startActivity(intent);
             finish();
        }

    }
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

        String currentLangIndex = manager.getString(AppConstant.STRINGLANGUAGE, "");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.text_select_language);
        boolean haslanguage = false;
        for (int i = 0; i < codes.length; i++) {
            if (codes[i].equalsIgnoreCase(currentLangIndex)) {
                haslanguage = true;
                builder.setSingleChoiceItems(languages, i, null);
            }
        }
        if (!haslanguage) {
            builder.setSingleChoiceItems(languages, 1, null);
        }
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton(R.string.text_select_language, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                int selectedIndex = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                AppConstant.isLanguagechanged = true;
                manager.put(AppConstant.STRINGLANGUAGE, codes[selectedIndex]);
                AppConstant.setLanguageForApp(manager.getString(AppConstant.STRINGLANGUAGE, ""), HomeActivity.this);
                startActivity(getIntent().putExtra("fromn",true));
                finish();

            }
        });

        builder.show();
    }

    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
          Fragment fragment=null;
            switch (i){
              case 0:
                  fragment=new MemberLoginActivity();
                  break;
              case 1:
                  fragment=new GuestLoginActivity();
                  break;
          }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }
    }
