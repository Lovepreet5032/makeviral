package com.prouman.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prouman.R;
import com.prouman.Util.AppConstant;
import com.prouman.activity.ContactListActivity;
import com.prouman.activity.EmailListActivity;
import com.prouman.activity.PrivateCategoryActivity;
import com.prouman.member_all.FunnelBusinessActivity;
import com.prouman.member_all.FunnelProdottoActivity;
import com.prouman.member_all.MemSecVideoActivity;
import com.prouman.member_all.PodcastActivity;
import com.prouman.member_all.ProductInfoCategory;
import com.prouman.member_all.ProductTestominal;
import com.prouman.member_all.PushActivity;
import com.prouman.member_all.WebinarActivity;
import com.prouman.model.ItemObject;
import com.prouman.ninjaforms.NativeFormActivity;
import com.prouman.ninjainterface.ItemClickListener;

import java.util.List;

import static com.prouman.R.string.Podcast;
import static com.prouman.R.string.Push;
import static com.prouman.R.string.Webinar;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    ItemClickListener clickListener;
    private Context context;
    private List<ItemObject> itemList;

    public class RecyclerViewHolder extends RecyclerView.ViewHolder  {
        public TextView countryName;
        public ImageView countryPhoto;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            this.countryName = (TextView) itemView.findViewById(R.id.country_name);
            this.countryPhoto = (ImageView) itemView.findViewById(R.id.country_photo);
        }

      /*  public void onClick(View view) {
            if (RecyclerViewAdapter.this.clickListener != null) {
                RecyclerViewAdapter.this.clickListener.onItemClick(getAdapterPosition(), view);
            }
        }*/
    }

    public RecyclerViewAdapter(Context context, List<ItemObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.guest_home, null));
    }

    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final int name = this.itemList.get(position).getName();

        holder.countryName.setText(this.itemList.get(position).getName());
        holder.countryPhoto.setImageResource(this.itemList.get(position).getPhoto());
        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (name)
                {
                    case R.string.viral_mail:
                        context.startActivity(new Intent(context, EmailListActivity.class));
                        break;
                    case R.string.Viral_Message:
                        context.startActivity(new Intent(context, ContactListActivity.class));
                        break;
                    case R.string.product_info:
                       context.startActivity(new Intent(context,ProductInfoCategory.class));

                        break;
                    case R.string.pro_testominal:
                        context.startActivity(new Intent(context,ProductTestominal.class));
                        break;
                    case R.string.business_testominals:
                        Bundle args = new Bundle();
                        args.putString("catID","47");
                        Intent intent = new Intent(context,MemSecVideoActivity.class);
                        AppConstant.videoscreentitle=context.getString(R.string.business_testominals);
                        intent.putExtras(args);
                        context.startActivity(intent);
                        break;
                    case R.string.private_video:
                        context.startActivity(new Intent(context,PrivateCategoryActivity.class));
                        break;
                    case R.string.shop_product:
                     // context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.moneysite.net/auth/register_account/" + uproShopLink)));
                        break;
                    case R.string.accademy:
                        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://academy.ninjaviralteam.com")));

                        break;
                    case R.string.Funne_business:
                        context.startActivity(new Intent(context,FunnelBusinessActivity.class));
                        break;
                    case R.string.Form:
                        context.startActivity(new Intent(context,NativeFormActivity.class));

                        break;
                    case R.string.Funnel_prodotto:
                        context.startActivity(new Intent(context,FunnelProdottoActivity.class));
                        break;
                    case Podcast:
                        context.startActivity(new Intent(context,PodcastActivity.class));
                        break;
                    case Webinar:
                        context.startActivity(new Intent(context,WebinarActivity.class));
                        break;
                    case Push:
                        context.startActivity(new Intent(context,PushActivity.class));
                    break;
                    case R.string.join_viral_ninja:
                        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://moneysite.net/auth/login")));

                        break;
                    case R.string.kyani_main:
                        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.kyani.com/it-it/")));

                        break;
                    case R.string.vip_card:
                        context.startActivity(new Intent(context,FunnelBusinessActivity.class));
                        break;

                    case R.string.s_days_pack:
                        context.startActivity(new Intent(context,FunnelBusinessActivity.class));
                        break;




                }
            }
        });
    }

    public int getItemCount() {
        return this.itemList.size();
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
