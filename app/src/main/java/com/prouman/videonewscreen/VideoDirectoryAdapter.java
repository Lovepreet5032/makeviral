package com.prouman.videonewscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.prouman.R;

import java.util.List;

/**
 * Created by aseemchoudhary on 14/02/18.
 */

public class VideoDirectoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<VideoDirectoryData> mVideoList;
    Context context;

    public VideoDirectoryAdapter(List<VideoDirectoryData> mVideoList, Context context) {
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
        private VideoDirectoryData mVideoModels;
        TextView mVideoTitle;
        ImageView mVideoPlayer;
        public ViewHolder(View v) {
            super(v);
            mVideoTitle = (TextView)v.findViewById(R.id.v_title);
            mVideoPlayer = (ImageView) v.findViewById(R.id.video_view);
        }
        public  void bindVideo(VideoDirectoryData videoModel)
        {

            mVideoModels = videoModel;
            mVideoTitle.setText(Html.fromHtml(Html.fromHtml(mVideoModels.getDirectoryName()).toString()));
           // String frameVideo = mVideoModels.getAppIcon();
            Picasso.with(context).load(mVideoModels.getAppIcon()).into(mVideoPlayer);

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
                    args.putString("videoId",mVideoModels.getId());
                    args.putString("title",mVideoModels.getDirectoryName());
                    Intent intent = new Intent(context, VideoHub.class);
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
        if (vho instanceof VideoDirectoryAdapter.ViewHolder) {
            VideoDirectoryAdapter.ViewHolder vh = (VideoDirectoryAdapter.ViewHolder) vho;
            VideoDirectoryData videoPos = mVideoList.get(pos);
            vh.bindVideo(videoPos);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == EMPTY_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list, parent, false);
            VideoDirectoryAdapter.EmptyViewHolder evh = new VideoDirectoryAdapter.EmptyViewHolder(v);
            return evh;
        }
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_view_list, parent, false);
        VideoDirectoryAdapter.ViewHolder vh = new VideoDirectoryAdapter.ViewHolder(v);
        return vh;
    }
    private static final int EMPTY_VIEW = 10;
}
