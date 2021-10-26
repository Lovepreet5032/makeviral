package com.prouman.message_list;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.prouman.R;
import com.prouman.model.MessageModel;

import java.util.ArrayList;


/**
 * Created by dsingh on 12/16/2016.
 */

public class MessageSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Context activity;
    private ArrayList<MessageModel> asr;

    public MessageSpinnerAdapter(Context context, ArrayList<MessageModel> asr) {
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
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(asr.get(position).getTitle());
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        TextView txt = new TextView(activity);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
        txt.setText(asr.get(i).getTitle());
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }


}
