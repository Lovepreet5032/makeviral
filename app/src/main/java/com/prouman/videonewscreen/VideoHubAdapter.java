package com.prouman.videonewscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.prouman.R;
import com.prouman.activity.WebPlayerActivity;

import java.util.List;

/**
 * Created by aseemchoudhary on 14/02/18.
 */

public class VideoHubAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<VideoData> mVideoList;
    Context context;

    public VideoHubAdapter(List<VideoData> mVideoList, Context context) {
        this.mVideoList = mVideoList;
        this.context = context;
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public EmptyViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.textView_main);
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private VideoData mVideoModels;
        TextView mVideoTitle;
        ImageView mVideoPlayer;
        public ViewHolder(View v) {
            super(v);
            mVideoTitle = (TextView)v.findViewById(R.id.v_title);
            mVideoPlayer = (ImageView) v.findViewById(R.id.video_view);
        }
        public  void bindVideo(VideoData videoModel)
        {

            mVideoModels = videoModel;
            mVideoTitle.setText(mVideoModels.getVideoName());
            // String frameVideo = mVideoModels.getAppIcon();
            Picasso.with(context).load(mVideoModels.getVideoImg()).into(mVideoPlayer);

            //mVideoPlayer.loadUrl(mVideoModels.getSource());
            //   mVideoPlayer.getSettings().setAllowFileAccess(true);

         /*   mVideoPlayer.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
            mVideoPlayer.setWebViewClient(new WebViewClient()
            {
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
                    Bundle args = new Bundle();
                    args.putParcelable("vdata",mVideoModels);
                    args.putString("videoUrl",mVideoModels.getEmbedUrl());
                    args.putString("title",mVideoModels.getVideoName());
                    args.putString("desc",mVideoModels.getVideoDetails());
                    args.putString("share_url", mVideoModels.getShare_url());
                    args.putParcelableArrayList("caption",mVideoModels.getCaptions());
//                    if(null != mVideoModels.getCaptions() && mVideoModels.getCaptions().size()>0) {
//                        for(Caption str:mVideoModels.getCaptions()) {
//                            if(str.getLabel().contains("google")){
//                            args.putString("Glink", str.getLink());}
//                            else if(str.getLabel().contains("facebook")){
//                                args.putString("Flink", str.getLink());
//                            }
//                        }
//
//                    }
                    Intent intent = new Intent(context, WebPlayerActivity.class);
                    intent.putExtras(args);

                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return mVideoList.size() > 0 ? mVideoList.size() : 1;
    }
    @Override
    public int getItemViewType(int position) {
        if (mVideoList.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vho, final int pos) {
        if (vho instanceof VideoHubAdapter.ViewHolder) {
            VideoHubAdapter.ViewHolder vh = (VideoHubAdapter.ViewHolder) vho;
            VideoData videoPos = mVideoList.get(pos);
            vh.bindVideo(videoPos);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == EMPTY_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list, parent, false);
            VideoHubAdapter.EmptyViewHolder evh = new VideoHubAdapter.EmptyViewHolder(v);
            return evh;
        }
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_view, parent, false);
        VideoHubAdapter.ViewHolder vh = new VideoHubAdapter.ViewHolder(v);
        return vh;
    }
    private static final int EMPTY_VIEW = 10;
}

