package com.prouman.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prouman.R;


public class BusinessView extends AppCompatActivity {
    private RelativeLayout backToGuest;
    ImageView theWebinar,listenTheWebinar;

    private TextView title;
    private RelativeLayout topLayout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_view);
        backToGuest = (RelativeLayout)findViewById(R.id.layout_back_to_guest);
        topLayout = ((RelativeLayout)findViewById(R.id.layout_top));
        theWebinar = (ImageView)findViewById(R.id.watch_the_webinar);
        listenTheWebinar =(ImageView)findViewById(R.id.listen_the_webinar);

        backToGuest.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                finish();

            }
        });
        theWebinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wTWintent = new Intent(BusinessView.this, WatchTheWebinar.class);
                startActivity(wTWintent);

            }
        });
        listenTheWebinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lTWintent = new Intent(BusinessView.this, WTheWebinar.class);
                startActivity(lTWintent);
            }
        });
    }
}
