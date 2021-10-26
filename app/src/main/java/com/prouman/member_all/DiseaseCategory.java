package com.prouman.member_all;

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
import com.prouman.model.ItemObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jcs on 1/2/2017.
 */
public class DiseaseCategory extends AppCompatActivity {
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
        setContentView(R.layout.disease_info_view);
        initView();
    }

    private void initView() {
        SharedPreferences sharedPreferences =this.getSharedPreferences("MyPref",0);
        RelativeLayout recycler_layout=(RelativeLayout)findViewById(R.id.recycler_layout);
        if(sharedPreferences.getString(PrefrencesConstant.hash,"").equalsIgnoreCase("")){
            recycler_layout.setBackground(getResources().getDrawable(R.drawable.bg_guest));
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


        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(DiseaseCategory.this,rowListItem);
        androidGridView=(GridView)findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {

                switch (rowListItem.get(i).getName()) {
                    case R.string.Headache:
                        Bundle headache = new Bundle();
                        headache.putString("catID","35");
                        Intent intent1 = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                        intent1.putExtras(headache);
                        AppConstant.videoscreentitle=getString(R.string.Headache);
                        startActivity(intent1);
                        break;
                    case R.string.stress:
                        Bundle args = new Bundle();
                        args.putString("catID","36");
                        Intent intent2 = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                        intent2.putExtras(args);
                        AppConstant.videoscreentitle=getString(R.string.stress);
                        startActivity(intent2);
                        break;
                    case R.string.Cholesterol:
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("catID","38");
                        Intent intent3 = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                        intent3.putExtras(bundle3);
                        AppConstant.videoscreentitle=getString(R.string.Cholesterol);
                        startActivity(intent3);

                        break;

                    case R.string.insonnia:
                        Bundle bundle = new Bundle();
                        bundle.putString("catID","39");
                        Intent video = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                        AppConstant.videoscreentitle=getString(R.string.insonnia);
                        video.putExtras(bundle);
                        startActivity(video);
                        break;
                    case R.string.reumatismi:
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("catID","40");
                        Intent intent4 = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                        AppConstant.videoscreentitle=getString(R.string.reumatismi);
                        intent4.putExtras(bundle4);
                        startActivity(intent4);
                        break;
                    case R.string.Menstrual_pains:
                        Bundle bundle5 = new Bundle();
                        bundle5.putString("catID","41");
                        AppConstant.videoscreentitle=getString(R.string.Menstrual_pains);
                        Intent intent5 = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                        intent5.putExtras(bundle5);
                        startActivity(intent5);

                        break;
                    case R.string.Asthma:
                        Bundle bundle6 = new Bundle();
                        bundle6.putString("catID","42");
                        AppConstant.videoscreentitle=getString(R.string.Asthma);
                        Intent intent6 = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                        intent6.putExtras(bundle6);
                        startActivity(intent6);
                        break;
                    case R.string.no_sex:
                        Bundle bundle7 = new Bundle();
                        bundle7.putString("catID","43");
                        AppConstant.videoscreentitle=getString(R.string.no_sex);
                        Intent intent7 = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                        intent7.putExtras(bundle7);
                        startActivity(intent7);
                        break;
                    case R.string.Weight_loss:
                        Bundle bundle8 = new Bundle();
                        bundle8.putString("catID","44");
                        AppConstant.videoscreentitle=getString(R.string.Weight_loss);
                        Intent intent8 = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                        intent8.putExtras(bundle8);
                        startActivity(intent8);
                        break;

                    case R.string.Constipation:
                        Bundle constipationBundle = new Bundle();
                        constipationBundle.putString("catID","46");
                        AppConstant.videoscreentitle=getString(R.string.Constipation);
                        Intent constiIntent = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                        constiIntent.putExtras(constipationBundle);
                        startActivity(constiIntent);
                        break;
                    case R.string.Skincare:
                        Bundle SkincareBundle = new Bundle();
                        SkincareBundle.putString("catID","57");
                        AppConstant.videoscreentitle=getString(R.string.Skincare);
                        Intent skinIntent = new Intent(DiseaseCategory.this,MemSecVideoActivity.class);
                        skinIntent.putExtras(SkincareBundle);
                        startActivity(skinIntent);
                        break;


                }

                // Toast.makeText(GHomeActivity.this, "GridView Item: " +rowListItem.get(i).getName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<ItemObject> getAllItemList() {
        List<ItemObject> allItems = new ArrayList();
        allItems.add(new ItemObject(R.string.Headache,R.drawable.icon_headache));
        allItems.add(new ItemObject(R.string.stress, R.drawable.icon_stress));
        allItems.add(new ItemObject(R.string.Cholesterol, R.drawable.icon_cholesterol));
        allItems.add(new ItemObject(R.string.insonnia, R.drawable.icon_insomnia));

        allItems.add(new ItemObject(R.string.reumatismi, R.drawable.icon_rheumatism));

        allItems.add(new ItemObject(R.string.Menstrual_pains, R.drawable.icon_mestrual_pains));
        allItems.add(new ItemObject(R.string.Asthma,R.drawable.icon_asthma));
        allItems.add(new ItemObject(R.string.no_sex, R.drawable.icon_no_sex));
        allItems.add(new ItemObject(R.string.Weight_loss, R.drawable.icon_weight_loss));
        allItems.add(new ItemObject(R.string.Constipation ,R.drawable.icon_constipation));
        allItems.add(new ItemObject(R.string.Skincare ,R.drawable.skincare));
       /* allItems.add(new ItemObject(R.string.Webinar, R.drawable.webinar));
        allItems.add(new ItemObject(R.string.Push, R.drawable.push_messages));
        allItems.add(new ItemObject(R.string.join_viral_ninja, R.drawable.viira_ninja));
        allItems.add(new ItemObject(R.string.kyani_main, R.drawable.kyani));
        allItems.add(new ItemObject(R.string.vip_card, R.drawable.vip_card));
        allItems.add(new ItemObject(R.string.s_days_pack, R.drawable.seven_day));*/

        return allItems;
    }
}
