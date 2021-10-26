package com.prouman.adapter;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prouman.R;
import com.prouman.model.MessageModel;

import java.util.List;

/**
 * Created by jcs on 12/13/2016.
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MsgViewHolder> {
    Context mContext;
    List<MessageModel> messageList;

    public MessageListAdapter(Context mContext, List<MessageModel> messageList) {
        this.mContext = mContext;
        this.messageList = messageList;
    }

    @Override
    public MessageListAdapter.MsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View msgView= LayoutInflater.from(mContext).inflate(R.layout.message_list_row,parent,false);
        return new MsgViewHolder(msgView);
    }

    @Override
    public void onBindViewHolder(MessageListAdapter.MsgViewHolder holder, int position) {
        MessageModel messageModel= messageList.get(position);
        holder.msgDesc.setText(messageModel.getMsgText());
        holder.msgTitle.setText(messageModel.getTitle());

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MsgViewHolder extends RecyclerView.ViewHolder{
        TextView msgTitle,msgDesc;
        public MsgViewHolder(View itemView) {
            super(itemView);
            msgTitle=(TextView)itemView.findViewById(R.id.txtTitle);
            msgDesc=(TextView)itemView.findViewById(R.id.txtText);
        }
    }
}
