package com.prouman.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.prouman.HomeActivity;
import com.prouman.R;
import com.prouman.Util.AppConstant;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.Util.SessionManager;
import com.prouman.guest_section.NinjaGuestHome;
import com.prouman.member_all.GHomeActivity;


public class LoginSelectionActivity extends AppCompatActivity {
    SessionManager manager;
    AppCompatButton btnChangeLanguage,btnGuestLogin,btnMemberLogin,btnForgotPassword;
    String L_status;
    LinearLayout llPrivacy,llAbout,llTerms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_selection);
        manager=new SessionManager(this);
        L_status =manager.getPreferences(LoginSelectionActivity.this,"L_status");
        if(null != getIntent() && null != getIntent().getExtras() && !getIntent().getExtras().getBoolean("fromn",false)) {
            if (L_status.equals("1")) {
                redirectScreen(true);
            } else {
                redirectScreen(false);
            }
        }
        llPrivacy=findViewById(R.id.llPrivacy);
        llAbout=findViewById(R.id.llAbout);
        llTerms=findViewById(R.id.llTerms);
        btnForgotPassword=findViewById(R.id.btnForgotPassword);

        llPrivacy.setOnClickListener(v -> startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://app.prouman.network/auth_public/view_privacy"))));
        llAbout.setOnClickListener(v -> startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.prouman.network"))));
        llTerms.setOnClickListener(v -> startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://app.prouman.network/auth_public/view_terms_conditions"))));
        btnForgotPassword.setOnClickListener(v -> startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://app.prouman.network/auth/forgotten_password"))));
        btnChangeLanguage=findViewById(R.id.btnChangeLanguage);
        btnChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLanguageChangePopup();
            }
        });
        btnGuestLogin=findViewById(R.id.btnGuestLogin);
        btnMemberLogin=findViewById(R.id.btnMemberLogin);
        btnGuestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginSelectionActivity.this, HomeActivity.class);
                i.putExtra("bguest",true);
                startActivity(i);
            }
        });
        btnMemberLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginSelectionActivity.this, HomeActivity.class);
                i.putExtra("bmember",true);
                startActivity(i);
            }
        });
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
                AppConstant.setLanguageForApp(manager.getString(AppConstant.STRINGLANGUAGE, ""), LoginSelectionActivity.this);
                startActivity(getIntent().putExtra("fromn",true));
                finish();

            }
        });

        builder.show();
    }
    public void redirectScreen(boolean frombtnMember) {
        String L_status = manager.getPreferences(LoginSelectionActivity.this, "L_status");
        Log.d("status", L_status);
        String G_status = manager.getPreferences(LoginSelectionActivity.this, "G_status");
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPref", 0);
        if (L_status.equals("1") && frombtnMember) {

            // session.createMemberLoginSession(firstName,lastName,phone,imgName,upro_id,websiteUrl,hash,affLink,shopLink,emailId);
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(PrefrencesConstant.uproid, sharedPreferences.getString("L_uproId", ""));
            editor.putString(PrefrencesConstant.hash, sharedPreferences.getString("L_hash", ""));
            editor.putString("FName", sharedPreferences.getString("L_FName", ""));
            editor.putString("LName", sharedPreferences.getString("L_LName", ""));
            editor.putString("Phone", sharedPreferences.getString("L_Phone", ""));
            editor.putString("imgName", sharedPreferences.getString("L_imgName", ""));
            editor.putString("upro_subdomain_link", sharedPreferences.getString("L_upro_subdomain_link", ""));
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
            Intent i = new Intent(LoginSelectionActivity.this, GHomeActivity.class);
            startActivity(i);

            //finish();
        } else if (G_status.equals("1") && !frombtnMember) {
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
            editor.putString(PrefrencesConstant.hash, "");
            editor.commit();
            // session.setPreferences(HomeActivity.this, "G_status", "1");
            Intent intent = new Intent(LoginSelectionActivity.this, NinjaGuestHome.class);
            intent.putExtras(bundle);
            startActivity(intent);
            //  finish();
        }
    }
}