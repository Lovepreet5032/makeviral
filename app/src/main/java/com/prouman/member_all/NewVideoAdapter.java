package com.prouman.member_all;

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
 * Created by jcs on 1/8/2017.
 */

public class NewVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PrivateVideoModel> mVideoList;
    Context context;

    public NewVideoAdapter(List<PrivateVideoModel> mVideoList, Context context) {
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
        private PrivateVideoModel mVideoModels;
        TextView mVideoTitle;
        ImageView mVideoPlayer;
        public ViewHolder(View v) {
            super(v);
            mVideoTitle = (TextView)v.findViewById(R.id.v_title);
            mVideoPlayer = (ImageView) v.findViewById(R.id.video_view);
        }
        public  void bindVideo(PrivateVideoModel videoModel)
        {

            mVideoModels = videoModel;
            mVideoTitle.setText(mVideoModels.getTitle());
            String frameVideo = mVideoModels.getSource();
            Picasso.with(context).load(Uri.parse(mVideoModels.getThumbnail())).into(mVideoPlayer);

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
        if (vho instanceof ViewHolder) {
            ViewHolder vh = (ViewHolder) vho;
            PrivateVideoModel videoPos = mVideoList.get(pos);
            vh.bindVideo(videoPos);
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
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    private static final int EMPTY_VIEW = 10;
}
