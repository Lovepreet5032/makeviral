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

import com.prouman.R;
import com.prouman.Util.AppConstant;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.model.ItemObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcs on 1/2/2017.
 */
public class ProductInfoCategory extends AppCompatActivity {
    LinearLayout ll1;
    LinearLayout ll2;
    LinearLayout ll3;
    LinearLayout ll4;
    LinearLayout ll5;
    LinearLayout ll6;
 ImageView backBtn;
    GridView androidGridView;

    private List<ItemObject> rowListItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_info_view);
        initView();
    }

    private void initView() {
       /* ll1 = (LinearLayout) findViewById(R.id.layout_1);
        ll2 = (LinearLayout) findViewById(R.id.layout_2);
        ll3 = (LinearLayout) findViewById(R.id.layout_3);
        ll4 = (LinearLayout) findViewById(R.id.layout_4);
        ll5 = (LinearLayout) findViewById(R.id.layout_5);
        ll6 = (LinearLayout) findViewById(R.id.layout_6);*/
        rowListItem = getAllItemList();
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
    /*    ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("catID","29");
                Intent intent = new Intent(ProductInfoCategory.this,MemSecVideoActivity.class);
                intent.putExtras(args);
                startActivity(intent);
                // Toast.makeText(getApplicationContext(),"Contact Listing will be there",Toast.LENGTH_LONG).show();
            }
        });

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("catID","30");
                Intent intent = new Intent(ProductInfoCategory.this,MemSecVideoActivity.class);
                intent.putExtras(args);
                startActivity(intent);
            }
        });
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("catID","31");
                Intent intent = new Intent(ProductInfoCategory.this,MemSecVideoActivity.class);
                intent.putExtras(args);
                startActivity(intent);

            }
        });
        ll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle args = new Bundle();
                args.putString("catID","32");
                Intent intent = new Intent(ProductInfoCategory.this,MemSecVideoActivity.class);
                intent.putExtras(args);
                startActivity(intent);
            }
        });
        ll4.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Bundle args = new Bundle();
                                       args.putString("catID","33");
                                       Intent intent = new Intent(ProductInfoCategory.this,MemSecVideoActivity.class);
                                       intent.putExtras(args);
                                       startActivity(intent);
                                   }
                               }
        );
*/
        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(ProductInfoCategory.this,rowListItem);
        androidGridView=(GridView)findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {

                switch (rowListItem.get(i).getName()) {
                    case R.string.sunrise:
                        Bundle args = new Bundle();
                        args.putString("catID","29");
                        Intent intent = new Intent(ProductInfoCategory.this,MemSecVideoActivity.class);
                        AppConstant.videoscreentitle=getString(R.string.sunrise);
                        intent.putExtras(args);
                        startActivity(intent);
                        break;
                    case R.string.sunset:
                        Bundle args2 = new Bundle();
                        args2.putString("catID","30");
                        Intent intent2 = new Intent(ProductInfoCategory.this,MemSecVideoActivity.class);
                        AppConstant.videoscreentitle=getString(R.string.sunset);
                        intent2.putExtras(args2);
                        startActivity(intent2);
                        break;
                    case R.string.nitro_fx:
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("catID","31");
                        Intent intent3 = new Intent(ProductInfoCategory.this,MemSecVideoActivity.class);
                        AppConstant.videoscreentitle=getString(R.string.nitro_fx);
                        intent3.putExtras(bundle3);
                        startActivity(intent3);

                        break;

                    case R.string.knitro_xtrime:
                        Bundle bundle = new Bundle();
                        bundle.putString("catID","32");
                        Intent video = new Intent(ProductInfoCategory.this,MemSecVideoActivity.class);
                        AppConstant.videoscreentitle=getString(R.string.knitro_xtrime);
                        video.putExtras(bundle);
                        startActivity(video);
                        break;
                    case R.string.kyani_powder:
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("catID","33");
                        Intent intent4 = new Intent(ProductInfoCategory.this,MemSecVideoActivity.class);
                        AppConstant.videoscreentitle=getString(R.string.kyani_powder);
                        intent4.putExtras(bundle4);
                        startActivity(intent4);
                        break;
                    case R.string.kyani_flour:
                        Bundle bundle5 = new Bundle();
                        bundle5.putString("catID","55");
                        Intent intent5 = new Intent(ProductInfoCategory.this,MemSecVideoActivity.class);
                        AppConstant.videoscreentitle=getString(R.string.kyani_flour);
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
        allItems.add(new ItemObject(R.string.sunrise,R.drawable.icon_sunrise));
        allItems.add(new ItemObject(R.string.sunset, R.drawable.icon_sunset));
        allItems.add(new ItemObject(R.string.nitro_fx, R.drawable.icon_nitro_fx));
        allItems.add(new ItemObject(R.string.knitro_xtrime, R.drawable.icon_nitro_extreme));
        allItems.add(new ItemObject(R.string.kyani_powder,R.drawable.icon_kyani_powder));
        allItems.add(new ItemObject(R.string.kyani_flour,R.drawable.fleuresse));
       /* allItems.add(new ItemObject(R.string.Webinar, R.drawable.webinar));
        allItems.add(new ItemObject(R.string.Push, R.drawable.push_messages));
        allItems.add(new ItemObject(R.string.join_viral_ninja, R.drawable.viira_ninja));
        allItems.add(new ItemObject(R.string.kyani_main, R.drawable.kyani));
        allItems.add(new ItemObject(R.string.vip_card, R.drawable.vip_card));
        allItems.add(new ItemObject(R.string.s_days_pack, R.drawable.seven_day));*/

        return allItems;
    }

}

