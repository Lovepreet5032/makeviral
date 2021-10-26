package com.prouman.member_all;

/**
 * Created by jcs on 1/4/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.prouman.R;
import com.prouman.Util.AppConstant;
import com.prouman.Util.SessionManager;
import com.prouman.model.ItemObject;

import java.util.List;

public class CustomGridViewActivity extends BaseAdapter {

    private Context mContext;
   /* private final String[] gridViewString;
    private final int[] gridViewImageId;*/
      List<ItemObject> itemObjects;
    SessionManager manager;
    //String count;
  /*  public CustomGridViewActivity(Context context, String[] gridViewString, int[] gridViewImageId) {
        mContext = context;
        this.gridViewImageId = gridViewImageId;
        this.gridViewString = gridViewString;
    }*/

//    public CustomGridViewActivity(Context mContext, List<ItemObject> itemObjects,String count) {
//        this.mContext = mContext;
//        this.itemObjects = itemObjects;
//    this.count=count;
//        manager =new SessionManager(mContext);
//    }
    public CustomGridViewActivity(Context mContext, List<ItemObject> itemObjects) {
        this.mContext = mContext;
        this.itemObjects = itemObjects;
        manager =new SessionManager(mContext);
       // count="0";
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

        ItemObject itemPos=itemObjects.get(i);
        ViewHolder holder = null;


            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           // gridViewAndroid = inflater.inflate(R.layout.grid_view_layout, null);
            convertView = inflater.inflate(R.layout.grid_view_layout, null);
            holder = new ViewHolder();

            holder.textViewAndroid = (TextView) convertView.findViewById(R.id.android_gridview_text);
            holder.imageViewAndroid = (ImageView) convertView.findViewById(R.id.android_gridview_image);
            holder.txt_batch=(TextView)convertView.findViewById(R.id.txt_batch);
        if(AppConstant.count>0&&mContext.getResources().getString(itemPos.getName()).equalsIgnoreCase("push"))
        {
            holder.txt_batch.setVisibility(View.VISIBLE);
            holder.txt_batch.setText(String.valueOf(AppConstant.count));
        }
        else{holder.txt_batch.setVisibility(View.GONE);}
            holder.textViewAndroid.setText(itemPos.getName());
            holder.imageViewAndroid.setImageResource(itemPos.getPhoto());
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