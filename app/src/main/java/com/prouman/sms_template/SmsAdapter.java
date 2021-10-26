package com.prouman.sms_template;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prouman.R;

import java.util.List;


/**
 * Created by dsingh on 6/23/2016.
 */
public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.MyViewHolder> {
    //ArrayList<Category> cModels;
    Context context;
     List<SmsModel> messageModels;


    public SmsAdapter(List<SmsModel> messageModels, Context context) {
       // this.cModels= cModels;

        this.messageModels= messageModels;
        this.context= context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_row,parent,false);

        return new MyViewHolder(itemView) ;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       SmsModel messageModelTest = messageModels.get(position);
      //  String result = cPos.getBody();
       // String mainMsg= String.valueOf(Html.fromHtml(result));
       //messageModels = cPos.getProds();
       //Category.MessageModelTest model = me;
        holder.textView.setText(messageModelTest.getSms_body());
        holder.titleTv.setText(messageModelTest.getDescription());


    }



    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleTv;
        TextView textView;
       // Button button;


        public MyViewHolder(View itemView) {
            super(itemView);
            titleTv =(TextView) itemView.findViewById(R.id.txtTitle);
            textView=(TextView)itemView.findViewById(R.id.txtText);
            //button=(Button)itemView.findViewById(R.id.btnSale);

        }
    }
}
