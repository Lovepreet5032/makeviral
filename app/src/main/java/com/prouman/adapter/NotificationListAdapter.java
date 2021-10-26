package com.prouman.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prouman.R;
import com.prouman.Util.AppConstant;
import com.prouman.Util.ConfigURL;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.app.Config;
import com.prouman.model.NotifcationdataObj;
import com.prouman.notification.NotificationDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by om on 2/18/2017.
 */
public class NotificationListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NotifcationdataObj> mNotifcationList;
    Context context;
    private static final int EMPTY_VIEW = 10;
    String uproID,hash;
    ProgressDialog progressDialog;
    //int position=0;
    public NotificationListAdapter(List<NotifcationdataObj> mNotifcationList, Context context) {
        this.mNotifcationList = mNotifcationList;
        this.context = context;
        SharedPreferences sharedPreferences =context.getSharedPreferences("MyPref",0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid,null);
        hash=sharedPreferences.getString(PrefrencesConstant.hash,null);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vho, final int pos) {
        if (vho instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) vho;
            NotifcationdataObj videoPos = mNotifcationList.get(pos);
            vh.bindVideo(videoPos,pos);
        }
        else if(vho instanceof  EmptyViewHolder)
        {
            ((EmptyViewHolder)vho).textView.setText(context.getString(R.string.no_noti_rec));
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
        private NotifcationdataObj mVideoModels;
        TextView txt_message,txt_tittle,txt_date;
//        ImageView mVideoPlayer;

        public ViewHolder(View v) {
            super(v);
            txt_tittle = (TextView) itemView.findViewById(R.id.txt_tittle);
            txt_message = (TextView) itemView.findViewById(R.id.txt_message);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
        }

        public void bindVideo(NotifcationdataObj videoModel, final int position) {

            mVideoModels = videoModel;
            txt_tittle.setText(Html.fromHtml(Html.fromHtml(mVideoModels.getStr_tittle()).toString()));
            txt_message.setText(Html.fromHtml(Html.fromHtml(mVideoModels.getStr_description()).toString()));
            txt_date.setText(mVideoModels.getStr_date());
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
            if(mVideoModels.getStr_isread()!=null&&mVideoModels.getStr_isread().equals("0"))
            {
                itemView.setBackgroundColor(Color.parseColor("#BCBFCA"));
            }
            else{itemView.setBackgroundColor(Color.parseColor("#33333D61"));}
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //    NotificationListAdapter.this.position=position;
                    if(mVideoModels.getStr_isread().equals("0"))
                    {
                        sendRead(mVideoModels,position);
                    }
                    else {
                        Intent intent = new Intent(context, NotificationDetail.class);
                        intent.putExtra("notification_id", mVideoModels.getNotification_id());
                        intent.putExtra("notification_data", mVideoModels);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == EMPTY_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list, parent, false);
            EmptyViewHolder evh = new EmptyViewHolder(v);
            return evh;
        }
        else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_view, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }
    }
    private void sendRead(final NotifcationdataObj mVideoModels,final int position) {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Loading");

            if(progressDialog!=null&&!progressDialog.isShowing()){
                progressDialog.show();}
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        final String regId = pref.getString("regId", null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.SeenNotification_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    if(progressDialog!=null){
                        progressDialog.hide();}
                JSONObject jsonObject;
                try {
                    jsonObject=new JSONObject(response);
                    if(jsonObject.getString("success").equals("true"))
                    {
                        mNotifcationList.get(position).setStr_isread("1");
                        //mVideoModels.setStr_isread("1");
                        AppConstant.count=AppConstant.count-1;
                        notifyDataSetChanged();
                        Intent intent = new Intent(context, NotificationDetail.class);
                        intent.putExtra("notification_id", mVideoModels.getNotification_id());
                        intent.putExtra("notification_data", mVideoModels);
                        context.startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(progressDialog!=null){
                    progressDialog.hide();}
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("upro_id", uproID);
                params.put("id",String.valueOf(mVideoModels.getNotification_id()));
            //    params.put(PrefrencesConstant.hash, hash);
            //    params.put("page",String.valueOf(currentPage));
//                params.put("reg_id",regId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

}

