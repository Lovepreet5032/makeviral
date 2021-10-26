package com.prouman.ninjaforms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.prouman.R;
import com.prouman.model.CountryListDataObject;

import java.util.List;

/**
 * Created by aseemchoudhary on 09/04/18.
 */

public class CustomAdapter extends ArrayAdapter<CountryListDataObject.CountryData> {

    LayoutInflater flater;
    Context context;
    public CustomAdapter(Context context, int resouceId, int textviewId, List<CountryListDataObject.CountryData> list){

        super(context,resouceId,textviewId, list);
        this.context=context;
//        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView,position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    private View rowview(View convertView , int position){

        CountryListDataObject.CountryData rowItem = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.row_countrycode, null, false);

            holder.txtTitle = (TextView) rowview.findViewById(R.id.txt_countrycode);
            holder.imageView = (ImageView) rowview.findViewById(R.id.imgflag);

            rowview.setTag(holder);
        }else{
            holder = (viewHolder) rowview.getTag();
        }
     //   holder.imageView.setImageResource(rowItem.getCode());
        if(null != rowItem) {
            holder.txtTitle.setText(rowItem.getDialcode() + " " + rowItem.getCountry() + " ");
            if (rowItem.getIso().equalsIgnoreCase("do")) {
                rowItem.setIso("doo");
            }
            int id = context.getResources().getIdentifier(context.getPackageName() + ":drawable/" + rowItem.getIso(), null, null);
            if (id > 0) {
                holder.imageView.setImageResource(id);
            }
        }
        else{
            holder.txtTitle.setText("Select Country");
        }
//        else{holder.imageView.setImageDrawable(null);}
        return rowview;
    }

    private class viewHolder{
        TextView txtTitle;
        ImageView imageView;
    }
}

