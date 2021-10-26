package com.prouman.member_all;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prouman.R;


/**
 * Created by jcs on 10/2/2016.
 */
public class PodcastActivity extends AppCompatActivity {


    private RelativeLayout backToGuest;
    //ImageView theWebinar,listenTheWebinar;
    ImageView backBtn;
    private TextView title;
    private RelativeLayout topLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.podcast_activity);
        backBtn =(ImageView)findViewById(R.id.layout_logo);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
