package com.prouman.member_all;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.prouman.R;
import com.prouman.Util.AppConstant;
import com.prouman.Util.SessionManager;
import com.prouman.model.HomeItemObjectModel;

import java.util.List;

/**
 * Created by om on 2/25/2017.
 */
public class MemeberCustomGridView extends BaseAdapter {

    private Context mContext;
    /* private final String[] gridViewString;
     private final int[] gridViewImageId;*/
    List<HomeItemObjectModel> itemObjects;
    SessionManager manager;
  /*  public CustomGridViewActivity(Context context, String[] gridViewString, int[] gridViewImageId) {
        mContext = context;
        this.gridViewImageId = gridViewImageId;
        this.gridViewString = gridViewString;
    }*/

    public MemeberCustomGridView(Context mContext, List<HomeItemObjectModel> itemObjects) {
        this.mContext = mContext;
        this.itemObjects = itemObjects;
        manager =new SessionManager(mContext);
    }

    @Override
    public int getCount() {
        return itemObjects.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        // View gridViewAndroid;

        HomeItemObjectModel itemPos=itemObjects.get(i);
        ViewHolder holder = null;


        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // gridViewAndroid = inflater.inflate(R.layout.grid_view_layout, null);
        convertView = inflater.inflate(R.layout.grid_view_layout, null);
        holder = new ViewHolder();
        holder.textViewAndroid = (TextView) convertView.findViewById(R.id.android_gridview_text);
        holder.imageViewAndroid = (ImageView) convertView.findViewById(R.id.android_gridview_image);
        holder.txt_batch=(TextView)convertView.findViewById(R.id.txt_batch);
        if(itemPos.getStrFromname()!=null) {
            if(itemPos.getStrFromname().equals("")) {
                if(AppConstant.count>0&&mContext.getResources().getString(itemPos.getName()).equalsIgnoreCase("push"))
                {
                    holder.txt_batch.setVisibility(View.VISIBLE);
                    holder.txt_batch.setText(String.valueOf(AppConstant.count));
                }
                else{holder.txt_batch.setVisibility(View.GONE);}
                holder.textViewAndroid.setText(itemPos.getName());
                holder.imageViewAndroid.setImageResource(itemPos.getPhoto());
            }
            else
            {
                if(itemPos.getStrFromname().equalsIgnoreCase("push")&&AppConstant.count>0)
                {
                    holder.txt_batch.setVisibility(View.VISIBLE);
                    holder.txt_batch.setText(String.valueOf(AppConstant.count));
                }
                else{holder.txt_batch.setVisibility(View.GONE);}
                holder.textViewAndroid.setText(itemPos.getStrFromname());
                Picasso.with(mContext).load(itemPos.getImg_url()).placeholder(R.drawable.round_corner).into(holder.imageViewAndroid);
                //  holder.imageViewAndroid.setImageResource(itemPos.getPhoto());
            }
          }

      /* else {
            holder = (ViewHolder)convertView.getTag();
        }*/

        return convertView;
    }
    static class ViewHolder {
        private TextView textViewAndroid;
        private ImageView imageViewAndroid;
        private  TextView txt_batch;
    }

}
