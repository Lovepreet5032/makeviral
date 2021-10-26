package com.prouman.sms_template;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.prouman.mesg_data.AllTemplatesCampigns;

import java.util.ArrayList;

/**
 * Created by dsingh on 12/16/2016.
 */

public class CategorySpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Context activity;
    private ArrayList<AllTemplatesCampigns.Category> asr;

    public CategorySpinnerAdapter(Context context, ArrayList<AllTemplatesCampigns.Category> asr) {
        this.asr=asr;
        activity = context;
    }




    public int getCount()
    {
        return asr.size();
    }

    public Object getItem(int i)
    {
        return asr.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }



    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(activity);
        txt.setPadding(14, 14, 14, 14);
        txt.setTextSize(17);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(asr.get(position).getName());
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        TextView txt = new TextView(activity);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(14, 14, 14, 14);
        txt.setTextSize(17);
        //txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
        txt.setText(asr.get(i).getName());
        txt.setTextColor(Color.parseColor("#FFFFFF"));
        return  txt;
    }


}
