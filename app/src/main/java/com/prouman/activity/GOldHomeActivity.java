package com.prouman.activity;/*
package com.makeviral.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.makeviral.R;
import com.makeviral.Util.ConfigURL;
import com.makeviral.adapter.RecyclerViewAdapter;
import com.makeviral.model.ItemObject;
import com.makeviral.ninjainterface.ItemClickListener;


public class GHomeActivity extends AppCompatActivity {
    Button callBtn;
    String firstName;
    Button homeBtn;
    String imgUrl;
    String img_name;
    private GridLayoutManager lLayout;
    String lastName;
    Button logoutBtn;
    String phone;
    RecyclerViewAdapter rcAdapter;
    List<ItemObject> rowListItem;
    ImageView toolImg;
    TextView toolText;
    TextView tvHome;
    TextView tvLogout;
    TextView tvPhoneNumber;

    private RelativeLayout backToGuest;


    private RelativeLayout topLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setSupportActionBar((Toolbar) findViewById(R.id.tool_bar_main));
       */
/* try {
            getSupportActionBar().setTitle("Guest");
        } catch (Exception e) {
            e.printStackTrace();
        }*//*

        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            firstName = extras.getString("FName");
            lastName = extras.getString("LName");
            phone = extras.getString("Phone");
            img_name = extras.getString("imgName");
        } else {
            Toast.makeText(getApplicationContext(), "No Bundle",Toast.LENGTH_LONG).show();
        }
      */
/*  logoutBtn = (Button) findViewById(R.id.logoutBtn);
        homeBtn = (Button) findViewById(R.id.homeBtn);
        tvName = (TextView) findViewById(R.id.tvName);*//*

        toolImg = (ImageView) findViewById(R.id.layout_logo);
        backToGuest = (RelativeLayout)findViewById(R.id.layout_back_to_guest);
        topLayout = ((RelativeLayout)findViewById(R.id.layout_top));

     tvHome =(TextView)findViewById(R.id.text_guest_right_menu_title);
     //tvLogout = (TextView)findViewById(R.id.text_guest_left_menu_title);
        backToGuest.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                finish();

            }
        });
        imgUrl = ConfigURL.PHOTO_URL + img_name;
        Picasso.with(this).load(imgUrl).placeholder(R.drawable.round_corner).into(toolImg);
        rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(this, 3);
       RecyclerView rView = (RecyclerView) findViewById(R.id.message_recyler);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);
        rcAdapter = new RecyclerViewAdapter(this, rowListItem);
        rView.setAdapter(rcAdapter);
        rView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rView, new ItemClickListener() {
            @Override
            public void onItemClick(int i, View view) {
                Bundle bundle = new Bundle();
                bundle.putString("FName",firstName);
                bundle.putString("LName", lastName);
                bundle.putString("Phone", phone);
                bundle.putString("imgName", img_name);
                Intent intent = new Intent(GHomeActivity.this, ContactActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);


            }

            @Override
            public void onLongClick(View view, int i) {

            }
        }
        ));
        toolImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
        });
    }

    private List<ItemObject> getAllItemList() {
        List<ItemObject> allItems = new ArrayList();
        allItems.add(new ItemObject(R.string.viral_mail,R.drawable.viral_mail));
        allItems.add(new ItemObject(R.string.Viral_Message, R.drawable.viral_sms));
        allItems.add(new ItemObject(R.string.product_info, R.drawable.products_info));
        allItems.add(new ItemObject(R.string.pro_testominal, R.drawable.products_testimonials));
        allItems.add(new ItemObject(R.string.business_testominals,R.drawable.business_testimonials));
        allItems.add(new ItemObject(R.string.private_video, R.drawable.private_video));
        allItems.add(new ItemObject(R.string.shop_product, R.drawable.shop));
        allItems.add(new ItemObject(R.string.accademy, R.drawable.academy));
        allItems.add(new ItemObject(R.string.Funne_business,R.drawable.funnel_business));
        allItems.add(new ItemObject(R.string.Funnel_prodotto, R.drawable.funnel_prodicts));
        allItems.add(new ItemObject(R.string.product_info, R.drawable.products_info));
        allItems.add(new ItemObject(R.string.form, R.drawable.form));
        allItems.add(new ItemObject(R.string.Podcast ,R.drawable.podcast ));
        allItems.add(new ItemObject(R.string.Webinar, R.drawable.webinar));
        allItems.add(new ItemObject(R.string.Push, R.drawable.push_messages));
        allItems.add(new ItemObject(R.string.join_viral_ninja, R.drawable.viira_ninja));
        allItems.add(new ItemObject(R.string.kyani, R.drawable.kyani));
        allItems.add(new ItemObject(R.string.vip_card, R.drawable.vip_card));
        allItems.add(new ItemObject(R.string.s_days_pack, R.drawable.seven_day));

        return allItems;
    }
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ItemClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ItemClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onItemClick(rv.getChildPosition(child), child);

            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
*/
