package com.prouman.contactmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.prouman.R;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.model.NotifcationdataObj;

import java.util.List;

/**
 * Created by aseemchoudhary on 09/05/18.
 */

public class ContactManagerAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    List<ContactManagerDataObject.ContactManagerListData> mNotifcationList;
    List<ContactManagerDataObject.ContactManagerListData> filterList;
    Context context;
    private static final int EMPTY_VIEW = 10;
    String uproID,hash;
    ProgressDialog progressDialog;
    boolean checkVisible=false;
    ContactManagerFilter filter;
    ContactAdapterInterface listner;
    //int position=0;
    public ContactManagerAdapter(List<ContactManagerDataObject.ContactManagerListData> mNotifcationList, Context context,ContactAdapterInterface listner) {
        this.mNotifcationList = mNotifcationList;
        filterList=mNotifcationList;
        this.context = context;
        SharedPreferences sharedPreferences =context.getSharedPreferences("MyPref",0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid,null);
        hash=sharedPreferences.getString(PrefrencesConstant.hash,null);
        this.listner=listner;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vho, final int pos) {
        if (vho instanceof ContactManagerAdapter.ViewHolder) {
            ContactManagerAdapter.ViewHolder vh = (ContactManagerAdapter.ViewHolder) vho;
            ContactManagerDataObject.ContactManagerListData videoPos = mNotifcationList.get(pos);
            vh.bindVideo(videoPos,pos);
        }
        else if(vho instanceof ContactManagerAdapter.EmptyViewHolder)
        {
            ((ContactManagerAdapter.EmptyViewHolder)vho).textView.setText(context.getString(R.string.no_contact));
        }
    }

    @Override
    public int getItemCount() {
        return mNotifcationList.size() > 0 ? mNotifcationList.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mNotifcationList.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView_main);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ContactManagerDataObject.ContactManagerListData mVideoModels;
        TextView text_prosname,text_pros_phone,text_prosemail;
        CheckBox chkSelected;
        ImageView img_user;

        public ViewHolder(View v) {
            super(v);
            text_prosname = (TextView) itemView.findViewById(R.id.text_prosname);
            text_pros_phone = (TextView) itemView.findViewById(R.id.text_pros_phone);
            text_prosemail = (TextView) itemView.findViewById(R.id.text_prosemail);
            chkSelected=(CheckBox)itemView.findViewById(R.id.chkSelected);
            img_user=(ImageView)itemView.findViewById(R.id.img_user);
        }

        public void bindVideo(ContactManagerDataObject.ContactManagerListData videoModel,final int position) {

            mVideoModels = videoModel;
            text_prosname.setText(mVideoModels.getName());
            text_pros_phone.setText(mVideoModels.getPhone());
            text_prosemail.setText(mVideoModels.getEmail());
          if (checkVisible) {
              chkSelected.setVisibility(View.VISIBLE);

                  chkSelected.setChecked(mVideoModels.isSelected());

          }
          else{
              chkSelected.setVisibility(View.GONE);
              mVideoModels.setSelected(false);
          }
            chkSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mNotifcationList.get(position).isSelected()) {
                        mNotifcationList.get(position).setSelected(false);
                        listner.updateListselection(false,position);
                    }
                    else{
                        mNotifcationList.get(position).setSelected(true);
                        listner.updateListselection(true,position);
                    }
                    notifyDataSetChanged();
                }

            });
          try {
              Picasso.with(context).load(mVideoModels.getPicture()).placeholder(R.drawable.icon_txt_user).into(img_user);
          }catch (Exception e)
          {
              img_user.setBackground(context.getResources().getDrawable(R.drawable.icon_txt_user));
          }
//            txt_tittle.setText(Html.fromHtml(Html.fromHtml(mVideoModels.getStr_tittle()).toString()));
//            txt_message.setText(Html.fromHtml(Html.fromHtml(mVideoModels.getStr_description()).toString()));
//            txt_date.setText(mVideoModels.getStr_date());
//            String frameVideo = mVideoModels.getSource();
//            Picasso.with(context).load(Uri.parse( mVideoModels.getThumbnail())).into(mVideoPlayer);

            //mVideoPlayer.loadUrl(mVideoModels.getSource());
            //   mVideoPlayer.getSettings().setAllowFileAccess(true);

            //       mVideoPlayer.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
           /* mVideoPlayer.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
            mVideoPlayer.getSettings().setJavaScriptEnabled(true);
            mVideoPlayer.loadData(frameVideo, "text/html", "utf-8");*/
//            if(mVideoModels.getStr_isread()!=null&&mVideoModels.getStr_isread().equals("0"))
//            {
//                itemView.setBackgroundColor(Color.parseColor("#BCBFCA"));
//            }
//            else{
                itemView.setBackgroundColor(Color.parseColor("#33333D61"));
          //  }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // AdminNotificationListAdapter.this.position=position;
//                    if(mVideoModels.getStr_isread().equals("0"))
//                    {
//                       // sendRead(mVideoModels,position);
//                    }
//                    else {
                        Intent intent = new Intent(context, ContactDetail.class);
                        intent.putExtra("c", mVideoModels);
                        context.startActivity(intent);
//                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    checkVisible=true;
                    notifyDataSetChanged();
                    return false;
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == EMPTY_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list, parent, false);
            ContactManagerAdapter.EmptyViewHolder evh = new ContactManagerAdapter.EmptyViewHolder(v);
            return evh;
        }
        else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contact_list_layout, parent, false);
            ContactManagerAdapter.ViewHolder vh = new ContactManagerAdapter.ViewHolder(v);
            return vh;
        }
    }
    private void sendRead(final NotifcationdataObj mVideoModels,final int position) {
//        progressDialog=new ProgressDialog(context);
//        progressDialog.setMessage("Loading");
//
//        if(progressDialog!=null&&!progressDialog.isShowing()){
//            progressDialog.show();}
//        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//        final String regId = pref.getString("regId", null);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.SeenNotification_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                if(progressDialog!=null){
//                    progressDialog.hide();}
//                JSONObject jsonObject;
//                try {
//                    jsonObject=new JSONObject(response);
//                    if(jsonObject.getString("success").equals("true"))
//                    {
//                        mNotifcationList.get(position).setStr_isread("1");
//                        //mVideoModels.setStr_isread("1");
//                        AppConstant.count=AppConstant.count-1;
//                        notifyDataSetChanged();
//                        Intent intent = new Intent(context, NotificationDetail.class);
//                        intent.putExtra("notification_id", mVideoModels.getNotification_id());
//                        intent.putExtra("notification_data", mVideoModels);
//                        context.startActivity(intent);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if(progressDialog!=null){
//                    progressDialog.hide();}
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                super.getParams();
//                Map<String, String> params = new HashMap<>();
//                params.put("upro_id", uproID);
//                params.put("id",String.valueOf(mVideoModels.getNotification_id()));
//                //    params.put("hash", hash);
//                //    params.put("page",String.valueOf(currentPage));
////                params.put("reg_id",regId);
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(stringRequest);

    }
    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new ContactManagerFilter(filterList,this);
        }
        return filter;

    }

}



