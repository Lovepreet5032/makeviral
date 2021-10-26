package com.prouman.contactmanager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prouman.R;
import com.prouman.Util.PrefrencesConstant;

import java.util.ArrayList;

/**
 * Created by aseemchoudhary on 11/06/18.
 */

public class SearchTagAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<SearchTagLocalObject> mNotifcationList;
    // ArrayList<TagDataObject.Email> filterList;
    String strtype;
    Context context;
    private static final int EMPTY_VIEW = 10;
    String uproID,hash;
    ProgressDialog progressDialog;
    boolean checkVisible=false;
    ContactManagerFilter filter;
    SearchTagRemoveListener listener;
    //int position=0;
    public SearchTagAdapter(Context context,ArrayList<SearchTagLocalObject> mNotifcationList,SearchTagRemoveListener listener){//,ContactAdapterInterface listner) {
        this.mNotifcationList = mNotifcationList;
       // this.data=data;
        this.strtype=strtype;
        // filterList=mNotifcationList;
        this.context = context;
        SharedPreferences sharedPreferences =context.getSharedPreferences("MyPref",0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid,null);
        hash=sharedPreferences.getString(PrefrencesConstant.hash,null);
        this.listener=listener;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vho, final int pos) {
        if (vho instanceof SearchTagAdapter.ViewHolder) {
            SearchTagAdapter.ViewHolder vh = (SearchTagAdapter.ViewHolder) vho;
            SearchTagLocalObject videoPos = mNotifcationList.get(pos);
            vh.bindVideo(videoPos,pos);
        }
        else if(vho instanceof SearchTagAdapter.EmptyViewHolder)
        {
            ((SearchTagAdapter.EmptyViewHolder)vho).textView.setText(context.getString(R.string.no_campign));
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
        private SearchTagLocalObject mVideoModels;
        TextView txt_tag_name;
        ImageView img_close;

        public ViewHolder(View v) {
            super(v);
            txt_tag_name = (TextView) itemView.findViewById(R.id.txt_tag_name);
            img_close= (ImageView) itemView.findViewById(R.id.img_close);
        }

        public void bindVideo(SearchTagLocalObject videoModel,final int position) {

            mVideoModels = videoModel;
            txt_tag_name.setText(mVideoModels.getValue());
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // addToGroup(mVideoModels);
                    listener.removeText(mVideoModels.getKey());
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == EMPTY_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list, parent, false);
            SearchTagAdapter.EmptyViewHolder evh = new SearchTagAdapter.EmptyViewHolder(v);
            return evh;
        }
        else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_searchtag, parent, false);
            SearchTagAdapter.ViewHolder vh = new SearchTagAdapter.ViewHolder(v);
            return vh;
        }
    }

//    private void addToGroup(final TagDataObject.Broadcast groups) {
//        progressDialog=new ProgressDialog(context);
//        progressDialog.setMessage("Loading");
//
//        if(progressDialog!=null&&!progressDialog.isShowing()){
//            progressDialog.show();}
//        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//        final String regId = pref.getString("regId", null);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigURL.ADDPROTOBROADCAST, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                response= AppConstant.fixEncoding(response);
//                if (progressDialog != null) {
//                    progressDialog.dismiss();
//                }
//                Gson gson=new Gson();
//                SucessDataObject sucessDataObject=gson.fromJson(response,SucessDataObject.class);
//                if(sucessDataObject.getSuccess().equalsIgnoreCase("true"))
//                {
//                    Toast.makeText(context,sucessDataObject.getMessage(),Toast.LENGTH_LONG).show();
//                }
//                if(null !=sucessDataObject)
//                    Toast.makeText(context,sucessDataObject.getMessage(),Toast.LENGTH_LONG).show();
//                //  ArrayList<NotifcationdataObj> notifcationdataObjList = new ArrayList<>();
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if (progressDialog != null) {
//                    progressDialog.hide();
//                }
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                super.getParams();
//                Map<String, String> params = new HashMap<>();
//                params.put("upro_id", uproID);
//                params.put("hash", hash);
//                String userId="";
//                String camptype="";
//                for(int i=0;i<data.size();i++)
//                {
//                    if(data.get(i).isSelected())
//                    {
//                        ContactManagerDataObject.ContactManagerListData contactManagerListData= data.get(i);
//                        if(userId.equalsIgnoreCase("")){userId=contactManagerListData.getProId();}
//                        else{userId=userId+","+contactManagerListData.getProId();}
//                    }
//
//                }
//
//                params.put("pro_id",userId);
//                //   params.put("pro_id",campName);
//                params.put("broadcast_id",groups.getId());
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                10000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(stringRequest);
//
//    }
////    @Override
////    public Filter getFilter() {
//////        if(filter==null)
//////        {
//////            filter=new ContactManagerFilter(filterList,this);
//////        }
////        return filter;
////
////    }

}


