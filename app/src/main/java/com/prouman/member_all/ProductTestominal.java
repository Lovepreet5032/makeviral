package com.prouman.member_all;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.prouman.R;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.activity.ProductTestimonialSubsection;

/**
 * Created by jcs on 1/2/2017.
 */
public class ProductTestominal extends AppCompatActivity {
    LinearLayout ll1;
    LinearLayout ll2;

 ImageView backBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_testominal);
        initView();
    }

    private void initView() {

        ll1 = (LinearLayout) findViewById(R.id.layout_1);
        ll2 = (LinearLayout) findViewById(R.id.layout_2);
        LinearLayout layout_main=(LinearLayout)findViewById(R.id.layout_main);
        SharedPreferences sharedPreferences =this.getSharedPreferences("MyPref",0);
        if(sharedPreferences.getString(PrefrencesConstant.hash,"").equalsIgnoreCase("")){
            layout_main.setBackground(getResources().getDrawable(R.drawable.bg_guest));
        }
        backBtn =(ImageView)findViewById(R.id.layout_logo);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("catID","37");
                Intent intent = new Intent(ProductTestominal.this,ProductTestimonialSubsection.class);
                intent.putExtras(args);
                 startActivity(intent);
                // Toast.makeText(getApplicationContext(),"Contact Listing will be there",Toast.LENGTH_LONG).show();
            }
        });

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ProductTestominal.this,DiseaseCategory.class);
                startActivity(intent);
            }
        });

    }
}
