package com.prouman.group;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prouman.R;
import com.prouman.Util.AppConstant;
import com.prouman.Util.PrefrencesConstant;
import com.prouman.contactmanager.ContactList;
import com.prouman.contactmanager.SearchTagLocalObject;
import com.prouman.notification.AdminNotificationListAdapter;
import com.prouman.tagmanager.TagDataPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aseemchoudhary on 09/01/19.
 */

public class GroupListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TagDataPojo.TagDataObject> mNotifcationList;
    Context context;
    private static final int EMPTY_VIEW = 10;
    String uproID,hash;
    ProgressDialog progressDialog;
    //int position=0;
    public GroupListAdapter(List<TagDataPojo.TagDataObject> mNotifcationList, Context context) {
        this.mNotifcationList = mNotifcationList;
        this.context = context;
        SharedPreferences sharedPreferences =context.getSharedPreferences("MyPref",0);
        uproID = sharedPreferences.getString(PrefrencesConstant.uproid,null);
        hash=sharedPreferences.getString(PrefrencesConstant.hash,null);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vho, final int pos) {
        if (vho instanceof GroupListAdapter.ViewHolder) {
            GroupListAdapter.ViewHolder vh = (GroupListAdapter.ViewHolder) vho;
            TagDataPojo.TagDataObject videoPos = mNotifcationList.get(pos);
            vh.bindVideo(videoPos,pos);
        }
        else if(vho instanceof AdminNotificationListAdapter.EmptyViewHolder)
        {
            ((GroupListAdapter.EmptyViewHolder)vho).textView.setText(context.getString(R.string.no_noti_rec));
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
        private TagDataPojo.TagDataObject mVideoModels;
        TextView tag_name,tag_cateria,total_prospects,start_campigns;
//        ImageView mVideoPlayer;

        public ViewHolder(View v) {
            super(v);
            tag_name = (TextView) itemView.findViewById(R.id.tag_name);
            tag_cateria = (TextView) itemView.findViewById(R.id.tag_cateria);
            total_prospects = (TextView) itemView.findViewById(R.id.total_prospects);
            start_campigns = (TextView) itemView.findViewById(R.id.start_campigns);
        }

        public void bindVideo(TagDataPojo.TagDataObject videoModel, final int position) {

            mVideoModels = videoModel;
            tag_name.setText(mVideoModels.getTagName());
            //  tag_cateria.setText(AppConstant.makeStringBold("Creteria").append(": ").append(mVideoModels.strCateria()));
            total_prospects.setText(AppConstant.makeStringBold("Total Prospects").append(": ").append(mVideoModels.getTotalProspects()));
            start_campigns.setVisibility(View.GONE);
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // AdminNotificationListAdapter.this.position=position;
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("search[group]=",mVideoModels.getId());
                    ArrayList<SearchTagLocalObject> arrsearLocal=new ArrayList<>();
                    SearchTagLocalObject searchTagLocalObject=new SearchTagLocalObject();
                    searchTagLocalObject.setValue("Group:"+mVideoModels.getTagName());
                    searchTagLocalObject.setKey("search[group]=");
                    arrsearLocal.add(searchTagLocalObject);
                    Intent intent=new Intent(context, ContactList.class);
                    intent.putExtra("query",hashMap);
                    intent.putParcelableArrayListExtra("querylist",arrsearLocal);
                    context.startActivity(intent);

                }});
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == EMPTY_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list, parent, false);
            GroupListAdapter.EmptyViewHolder evh = new GroupListAdapter.EmptyViewHolder(v);
            return evh;
        }
        else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tagdatalist, parent, false);
            GroupListAdapter.ViewHolder vh = new GroupListAdapter.ViewHolder(v);
            return vh;
        }
    }


}



