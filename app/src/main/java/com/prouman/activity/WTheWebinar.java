package com.prouman.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prouman.R;

/**
 * Created by jcs on 10/2/2016.
 */
public class WTheWebinar extends AppCompatActivity {
    private RelativeLayout backToGuest;
    //ImageView theWebinar,listenTheWebinar;

    private TextView title;
    private RelativeLayout topLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listen_the_webinar);
        backToGuest = (RelativeLayout)findViewById(R.id.layout_back_to_guest);
        topLayout = ((RelativeLayout)findViewById(R.id.layout_top));

    }

}
