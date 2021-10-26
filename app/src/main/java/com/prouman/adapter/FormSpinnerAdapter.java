package com.prouman.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prouman.R;
import com.prouman.model.FormData;

import java.util.ArrayList;

/**
 * Created by jcs on 12/15/2016.
 */
public class FormSpinnerAdapter extends BaseAdapter  {
    private Activity context;
    ArrayList<FormData> data ;

    public FormSpinnerAdapter(Activity context,ArrayList<FormData> data) {

        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(context);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(data.get(position).getForm_name());
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(context);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
        txt.setText(data.get(position).getForm_name());
        txt.setTextColor(Color.parseColor("#000000"));
        return  txt;

       /*   View row = convertView;
        if (row == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.simple_spinner_item, parent, false);
        }

        FormData item = data.get(position);

        if (item != null) { // Parse the data from each object and set it.
            //TextView CountryId = (TextView) row.findViewById(R.id.item_id);
            TextView CountryName = (TextView) row.findViewById(R.id.item_value);
            CountryName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
            if (CountryName != null) {
                CountryName.setText(item.getForm_name());
            }

        }
        return row;
*/
    }
}

