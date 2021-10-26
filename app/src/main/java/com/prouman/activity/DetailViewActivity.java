package com.prouman.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.prouman.R;
import com.prouman.Util.ConfigURL;

public class DetailViewActivity extends AppCompatActivity {
    Button callBtn;
    String firstName;
    Button homeBtn;
    ImageView imageView1;
    ImageView img2;
    ImageView img3;
    String imgUrl;
    String img_name;
    String lastName;
    Button logoutBtn;
    String phone;
    String title;
    ImageView toolImg;
    TextView toolText;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tvLast;
    TextView tvName;
    TextView tvPhoneNumber;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_view);
        Toolbar topToolBar = (Toolbar) findViewById(R.id.detail_bar);
        setSupportActionBar(topToolBar);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            firstName = extras.getString("FName");
            lastName = extras.getString("LName");
            phone = extras.getString("Phone");
            img_name = extras.getString("imgName");
            title = extras.getString("name");
        } else {
            Toast.makeText(getApplicationContext(), "No Bundle", Toast.LENGTH_LONG).show();
        }
        topToolBar.setTitle(title);
        try {
            getSupportActionBar().setTitle("Presentation");
        } catch (Exception e) {
            e.printStackTrace();
        }
        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        homeBtn = (Button) findViewById(R.id.homeBtn);
        tvName = (TextView) findViewById(R.id.tvName);
        toolImg = (ImageView) findViewById(R.id.toolbar_image);
        tv1 = (TextView) findViewById(R.id.d_tv1);
        tv1.setText("Watch The Webinar");
        tv2 = (TextView) findViewById(R.id.d_tv2);
        tv2.setText("Listen To The Call");
        tv3 = (TextView) findViewById(R.id.d_tv3);
        tv3.setText("Just Push Play Business");
        imgUrl = ConfigURL.PHOTO_URL + img_name;
        Picasso.with(this).load(imgUrl).placeholder(R.drawable.round_corner).into(toolImg);
        toolImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
