package com.prouman.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.prouman.R;

public class GuestNavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button callBtn;
    String firstName;
    Button homeBtn;
    String imgUrl;
    String img_name;
    String lastName;
    LinearLayout ll1;
    LinearLayout ll2;
    LinearLayout ll3;
    LinearLayout ll4;
    LinearLayout ll5;
    TextView tvLogout,tvHome;
    String phone;

    ImageView toolImg;
    TextView toolText;
    TextView tvEmail;
    TextView tvName;
    TextView tvPhoneNumber;
    String uproAffLink;
    String uproShopLink;
    String upro_ref, upro_id;
    RelativeLayout backToGuest;


    RelativeLayout topLayout;
    private String emailID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvPhoneNumber = (TextView)findViewById(R.id.textView5);
        tvEmail = (TextView)findViewById(R.id.textView6);
        tvName =(TextView)findViewById(R.id.contact_name_tv);
        toolImg=(ImageView)findViewById(R.id.contact_image);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            firstName = extras.getString("FName");
            lastName = extras.getString("LName");
            phone = extras.getString("Phone");
            img_name = extras.getString("imgName");
            upro_ref = extras.getString("uproRef");
            uproAffLink = extras.getString("uproAffLink");
            uproShopLink = extras.getString("uproShopLink");
            emailID = extras.getString("upro_main_email");
        } else {
            Toast.makeText(getApplicationContext(), "No Bundle", Toast.LENGTH_LONG).show();
        }
        imgUrl = img_name;//ConfigURL.PHOTO_URL +
        Picasso.with(getApplicationContext()).load(imgUrl).placeholder(R.drawable.round_corner).into(toolImg);
        tvPhoneNumber.setText(phone);
        tvEmail.setText(emailID);
        tvName.setText(firstName);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.guest_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
