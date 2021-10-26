package com.prouman.member_all;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prouman.R;

/**
 * Created by jcs on 1/7/2017.
 */

public class EmptyListAdapter extends RecyclerView.Adapter<EmptyListAdapter.ViewHolder> {

    private String mMessage;

    public EmptyListAdapter(){}

    public EmptyListAdapter(String message){
        mMessage = message;
    }

    @Override
    public EmptyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        if(mMessage != null){
            viewHolder.mMessageView.setText(mMessage);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EmptyListAdapter.ViewHolder holder, int position) {}

    @Override
    public int getItemCount() {
        return 1;//must return one otherwise none item is shown
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mMessageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMessageView = (TextView) view.findViewById(R.id.textView_main);
        }
    }
}