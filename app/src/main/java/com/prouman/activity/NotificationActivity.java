package com.prouman.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.prouman.R;

/**
 * Created by jcs on 10/23/2016.
 */

public class NotificationActivity extends AppCompatActivity {

    private ImageView backBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_view);
        backBtn =(ImageView)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
