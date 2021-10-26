package com.prouman.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.prouman.model.PrivateVideoModel;

import java.util.List;

/**
 * Created by dsingh on 9/1/2016.
 */
public class PrivateVideoAdapter extends  RecyclerView.Adapter<PrivateVideoAdapter.VideoViewHolder>
{
    private List<PrivateVideoModel> mVideoList;
    Context context;

    public PrivateVideoAdapter(List<PrivateVideoModel> mVideoList, Context context) {
        this.mVideoList = mVideoList;
        this.context= context;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View videoView = LayoutInflater.from(context).inflate(R.layout.video_list_view,parent,false);
        return new VideoViewHolder(videoView);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        PrivateVideoModel videoPos = mVideoList.get(position);
        holder.bindVideo(videoPos);

    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder
    {

        private PrivateVideoModel mVideoModels;
        TextView mVideoTitle;
        ImageView mVideoPlayer;
        public VideoViewHolder(View itemView) {
            super(itemView);
            mVideoTitle = (TextView)itemView.findViewById(R.id.v_title);
            mVideoPlayer = (ImageView) itemView.findViewById(R.id.video_view);


        }
        public  void bindVideo(PrivateVideoModel videoModel)
        {

            mVideoModels = videoModel;
            mVideoTitle.setText(mVideoModels.getTitle());
            String frameVideo = mVideoModels.getSource();
           Picasso.with(context).load(Uri.parse(mVideoModels.getThumbnail())).into(mVideoPlayer);

            //mVideoPlayer.loadUrl(mVideoModels.getSource());
            //   mVideoPlayer.getSettings().setAllowFileAccess(true);

           // mVideoPlayer.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
         /*   mVideoPlayer.setWebViewClient(new WebViewClient()
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
                    args.putString("videoUrl",mVideoModels.getSource());
                    args.putString("title",mVideoModels.getTitle());
                    Intent intent = new Intent(context, WebPlayerActivity.class);
                    intent.putExtras(args);

                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}