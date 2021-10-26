package com.prouman.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.prouman.R;
import com.prouman.Util.AppConstant;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.member_all.CustomGridViewActivity;
import com.prouman.member_all.MemSecVideoActivity;
import com.prouman.model.ItemObject;

import java.util.ArrayList;
import java.util.List;

public class ProductTestimonialSubsection extends AppCompatActivity {
    LinearLayout ll1;
    LinearLayout ll2;
    LinearLayout ll3;
    LinearLayout ll4;
    LinearLayout ll5;
    LinearLayout ll6;
    LinearLayout ll7;
    LinearLayout ll8;
    LinearLayout ll9;
    LinearLayout ll10;
    LinearLayout ll11;
    GridView androidGridView;
    ImageView backBtn;
    private List<ItemObject> rowListItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_testimonial_subsection);
        initView();
    }

    private void initView() {
        RelativeLayout layout_main=(RelativeLayout) findViewById(R.id.layout_main);
        SharedPreferences sharedPreferences =this.getSharedPreferences("MyPref",0);
        if(sharedPreferences.getString(PrefrencesConstant.hash,"").equalsIgnoreCase("")){
            layout_main.setBackground(getResources().getDrawable(R.drawable.bg_guest));
        }
   /*     ll1 = (LinearLayout) findViewById(R.id.layout_1);
        ll2 = (LinearLayout) findViewById(R.id.layout_2);
        ll3 = (LinearLayout) findViewById(R.id.layout_3);
        ll4 = (LinearLayout) findViewById(R.id.layout_4);
        ll5 = (LinearLayout) findViewById(R.id.layout_5);
        ll6 = (LinearLayout) findViewById(R.id.layout_6);
        ll7 = (LinearLayout) findViewById(R.id.layout_7);
        ll8 = (LinearLayout) findViewById(R.id.layout_8);
        ll9 = (LinearLayout) findViewById(R.id.layout_9);
        ll10 = (LinearLayout) findViewById(R.id.layout_10);
*/  rowListItem = getAllItemList();

        backBtn =(ImageView)findViewById(R.id.layout_logo);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
      /*  ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("catID","35");
                Intent intent = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                intent.putExtras(args);
                startActivity(intent);
                // Toast.makeText(getApplicationContext(),"Contact Listing will be there",Toast.LENGTH_LONG).show();
            }
        });

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("catID","36");
                Intent intent = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                intent.putExtras(args);
                startActivity(intent);
            }
        });
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("catID","38");
                Intent intent = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                intent.putExtras(args);
                startActivity(intent);

            }
        });
        ll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle args = new Bundle();
                args.putString("catID","39");
                Intent intent = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                intent.putExtras(args);
                startActivity(intent);
            }
        });
        ll4.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Bundle args = new Bundle();
                                       args.putString("catID","40");
                                       Intent intent = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                                       intent.putExtras(args);
                                       startActivity(intent);
                                   }
                               }
        );
        ll6.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Bundle args = new Bundle();
                                       args.putString("catID","41");
                                       Intent intent = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                                       intent.putExtras(args);
                                       startActivity(intent);
                                   }
                               }
        );
        ll7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("catID","42");
                Intent intent = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                intent.putExtras(args);
                startActivity(intent);
                // Toast.makeText(getApplicationContext(),"Contact Listing will be there",Toast.LENGTH_LONG).show();
            }
        });

        ll8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("catID","43");
                Intent intent = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                intent.putExtras(args);
                startActivity(intent);
            }
        });
        ll9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("catID","44");
                Intent intent = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                intent.putExtras(args);
                startActivity(intent);

            }
        });
        ll10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle args = new Bundle();
                args.putString("catID","45");
                Intent intent = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                intent.putExtras(args);
                startActivity(intent);
            }
        });
        ll10.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Bundle args = new Bundle();
                                       args.putString("catID","46");
                                       Intent intent = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                                       intent.putExtras(args);
                                       startActivity(intent);
                                   }
                               }
        );*/


        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(ProductTestimonialSubsection.this,rowListItem);
        androidGridView=(GridView)findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {

                switch (rowListItem.get(i).getName()) {
                    case R.string.sunrisetesttimonial:
                        Bundle headache = new Bundle();
                        headache.putString("catID","50");
                        Intent intent1 = new Intent(ProductTestimonialSubsection.this,MemSecVideoActivity.class);
                        AppConstant.videoscreentitle=getString(R.string.sunrisetesttimonial);
                        intent1.putExtras(headache);
                        startActivity(intent1);
                        break;
                    case R.string.sunsettestimonials:
                        Bundle args = new Bundle();
                        args.putString("catID","51");
                        Intent intent2 = new Intent(ProductTestimonialSubsection.this,MemSecVideoActivity.class);
                        AppConstant.videoscreentitle=getString(R.string.sunsettestimonials);
                        intent2.putExtras(args);
                        startActivity(intent2);
                        break;
                    case R.string.nitrofxtimonials:
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("catID","52");
                        Intent intent3 = new Intent(ProductTestimonialSubsection.this,MemSecVideoActivity.class);
                        AppConstant.videoscreentitle=getString(R.string.nitrofxtimonials);
                        intent3.putExtras(bundle3);
                        startActivity(intent3);

                        break;

                    case R.string.nitroxtremetimonials:
                        Bundle bundle = new Bundle();
                        bundle.putString("catID","53");
                        Intent video = new Intent(ProductTestimonialSubsection.this,MemSecVideoActivity.class);
                        AppConstant.videoscreentitle=getString(R.string.nitroxtremetimonials);
                        video.putExtras(bundle);
                        startActivity(video);
                        break;
                    case R.string.kyanipowdertimonials:
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("catID","54");
                        Intent intent4 = new Intent(ProductTestimonialSubsection.this,MemSecVideoActivity.class);
                        AppConstant.videoscreentitle=getString(R.string.kyanipowdertimonials);
                        intent4.putExtras(bundle4);
                        startActivity(intent4);
                        break;
                    case R.string.fleu_test:
                        Bundle bundle5 = new Bundle();
                        bundle5.putString("catID","56");
                        Intent intent5 = new Intent(ProductTestimonialSubsection.this,MemSecVideoActivity.class);
                        AppConstant.videoscreentitle=getString(R.string.fleu_test);
                        intent5.putExtras(bundle5);
                        startActivity(intent5);
                        break;
                }

                // Toast.makeText(GHomeActivity.this, "GridView Item: " +rowListItem.get(i).getName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<ItemObject> getAllItemList() {
        List<ItemObject> allItems = new ArrayList();
        allItems.add(new ItemObject(R.string.sunrisetesttimonial,R.drawable.icon_sunrise));
        allItems.add(new ItemObject(R.string.sunsettestimonials, R.drawable.icon_sunset));
        allItems.add(new ItemObject(R.string.nitrofxtimonials, R.drawable.icon_nitro_fx));
        allItems.add(new ItemObject(R.string.nitroxtremetimonials, R.drawable.icon_nitro_extreme));
        allItems.add(new ItemObject(R.string.kyanipowdertimonials, R.drawable.icon_kyani_powder));
        allItems.add(new ItemObject(R.string.fleu_test, R.drawable.fleuresse));
       /* allItems.add(new ItemObject(R.string.Webinar, R.drawable.webinar));
        allItems.add(new ItemObject(R.string.Push, R.drawable.push_messages));
        allItems.add(new ItemObject(R.string.join_viral_ninja, R.drawable.viira_ninja));
        allItems.add(new ItemObject(R.string.kyani_main, R.drawable.kyani));
        allItems.add(new ItemObject(R.string.vip_card, R.drawable.vip_card));
        allItems.add(new ItemObject(R.string.s_days_pack, R.drawable.seven_day));*/

        return allItems;
    }
}


