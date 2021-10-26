package com.prouman.test_db;//package com.app.td.loginscreen.test_db;
//
//import android.content.Context;
//import androidx.appcompat.app.AppCompatActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import com.app.td.loginscreen.R;
//
///**
// * Created by jcs on 12/13/2016.
// */
//
//public class CustomAdapter extends ArrayAdapter {
//
//    public static final int TYPE_ODD = 0;
//    public static final int TYPE_EVEN = 1;
//
//
//    private ContactTest[] objects;
//
//    @Override
//    public int getViewTypeCount() {
//        return 4;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return objects[position].getmOnline();
//    }
//
//    public CustomAdapter(Context context, int resource, ContactTest[] objects) {
//        super(context, resource, objects);
//        this.objects = objects;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        RecyclerView.ViewHolder viewHolder = null;
//        ContactTest listViewItem = objects[position];
//        int listViewItemType = getItemViewType(position);
//
//
//        if (convertView == null) {
//
//            if (listViewItemType == TYPE_EVEN) {
//                convertView = LayoutInflater.from(getContext()).inflate(R.layout.disconnecte_row, null);
//            } else if (listViewItemType == TYPE_ODD) {
//                convertView = LayoutInflater.from(getContext()).inflate(R.layout.connection_list_row, null);
//            }
//
//            TextView textView = (TextView) convertView.findViewById(R.id.text);
//            viewHolder = new RecyclerView.ViewHolder(textView);
//
//            convertView.setTag(viewHolder);
//
//        } else {
//            viewHolder = (RecyclerView.ViewHolder) convertView.getTag();
//        }
//
//        viewHolder.getText().setText(listViewItem.getText());
//
//        return convertView;
//    }
//
//}
//
//
