package com.prouman.contactmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.prouman.R;
import com.prouman.Util.AppConstant;
import com.prouman.Util.ConfigURL;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.app.Config;
import com.prouman.common.SucessDataObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aseemchoudhary on 18/05/18.
 */

public class AddtoGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<TagDataObject.Groups> mNotifcationList;
   // ArrayList<TagDataObject.Email> filterList;
    ArrayList<ContactManagerDataObject.ContactManagerListData> data;
    String strtype;
    Context context;
    private static final int EMPTY_VIEW = 10;
    String uproID,hash;
    ProgressDialog progressDialog;
    boolean checkVisible=false;
    ContactManagerFilter filter;
    ContactAdapterInterface listner;
    //int position=0;
    public AddtoGroupAdapter(ArrayList<TagDataObject.Groups> mNotifcationList, Context context,ArrayList<ContactManagerDataObject.ContactManagerListData> data){//,ContactAdapterInterface listner) {
        this.mNotifcationList = mNotifcationList;
        this.data=data;
        this.strtype=strtype;
       // filterList=mNotifcationList;
        this.context = context;
        SharedPreferences sharedPreferences =context.getSharedPreferences("MyPref",0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid,null);
        hash=sharedPreferences.getString(PrefrencesConstant.hash,null);
        this.listner=listner;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vho, final int pos) {
        if (vho instanceof AddtoGroupAdapter.ViewHolder) {
            AddtoGroupAdapter.ViewHolder vh = (AddtoGroupAdapter.ViewHolder) vho;
            TagDataObject.Groups videoPos = mNotifcationList.get(pos);
            vh.bindVideo(videoPos,pos);
        }
        else if(vho instanceof AddtoGroupAdapter.EmptyViewHolder)
        {
            ((AddtoGroupAdapter.EmptyViewHolder)vho).textView.setText(context.getString(R.string.no_campign));
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
        private TagDataObject.Groups mVideoModels;
        TextView txt_campignname;
        Button btn_action;
        ImageView img_user;

        public ViewHolder(View v) {
            super(v);
            txt_campignname = (TextView) itemView.findViewById(R.id.txt_campignname);
            btn_action = (Button) itemView.findViewById(R.id.btn_action);
        }

        public void bindVideo(TagDataObject.Groups videoModel,final int position) {

            mVideoModels = videoModel;
            txt_campignname.setText(mVideoModels.getGroupName());
            btn_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToGroup(mVideoModels);
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == EMPTY_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list, parent, false);
            AddtoGroupAdapter.EmptyViewHolder evh = new AddtoGroupAdapter.EmptyViewHolder(v);
            return evh;
        }
        else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_addtocapmpign, parent, false);
            AddtoGroupAdapter.ViewHolder vh = new AddtoGroupAdapter.ViewHolder(v);
            return vh;
        }
    }

    private void addToGroup(final TagDataObject.Groups groups) {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Loading");

            if(progressDialog!=null&&!progressDialog.isShowing()){
                progressDialog.show();}
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        final String regId = pref.getString("regId", null);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.ADDPROTOGROUP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response= AppConstant.fixEncoding(response);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                Gson gson=new Gson();
                SucessDataObject sucessDataObject=gson.fromJson(response,SucessDataObject.class);
                if(sucessDataObject.getSuccess().equalsIgnoreCase("true"))
                {
                     Toast.makeText(context,sucessDataObject.getMessage(),Toast.LENGTH_LONG).show();
                }
                if(null !=sucessDataObject)
                    Toast.makeText(context,sucessDataObject.getMessage(),Toast.LENGTH_LONG).show();
                //  ArrayList<NotifcationdataObj> notifcationdataObjList = new ArrayList<>();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog != null) {
                    progressDialog.hide();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> params = new HashMap<>();
                params.put("upro_id", uproID);
                params.put("hash", hash);
                String userId="";
                String camptype="";
                for(int i=0;i<data.size();i++)
                {
                    if(data.get(i).isSelected())
                    {
                        ContactManagerDataObject.ContactManagerListData contactManagerListData= data.get(i);
                        if(userId.equalsIgnoreCase("")){userId=contactManagerListData.getProId();}
                        else{userId=userId+","+contactManagerListData.getProId();}
                    }

                }

                params.put("pro_id",userId);
                //   params.put("pro_id",campName);
                params.put("group_id",groups.getId());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }
//    @Override
//    public Filter getFilter() {
////        if(filter==null)
////        {
////            filter=new ContactManagerFilter(filterList,this);
////        }
//        return filter;
//
//    }

}
