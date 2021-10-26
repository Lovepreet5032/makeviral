package com.prouman.omspages;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Parcelable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.prouman.R;
import com.prouman.videonewscreen.InappWebview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aseemchoudhary on 24/04/18.
 */

public class PagesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PagesListDataobject.OMSPage> mVideoList;
    Context context;

    public PagesListAdapter(List<PagesListDataobject.OMSPage> mVideoList, Context context) {
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
        private PagesListDataobject.OMSPage mVideoModels;
        TextView mVideoTitle,v_title_desc,txt_hits,txt_visit
                ,txt_middleplays,txt_videoplays,v_title_link,txt_view_views;//v_date
           ImageView img_picture;Button btn_share;
        public ViewHolder(View v) {
            super(v);
            mVideoTitle = (TextView)v.findViewById(R.id.v_title);
           // v_date = (TextView) v.findViewById(R.id.v_date);
            v_title_desc = (TextView) v.findViewById(R.id.v_title_desc);
            txt_hits= (TextView) v.findViewById(R.id.txt_hits);
            txt_visit= (TextView) v.findViewById(R.id.txt_visit);
            txt_middleplays= (TextView) v.findViewById(R.id.txt_middleplays);
            txt_videoplays= (TextView) v.findViewById(R.id.txt_videoplays);
            v_title_link= (TextView) v.findViewById(R.id.v_title_link);
            txt_view_views= (TextView) v.findViewById(R.id.txt_view_views);
            img_picture=(ImageView)v.findViewById(R.id.img_picture);
            btn_share=(Button)v.findViewById(R.id.btn_share);
        }
        public  void bindVideo(PagesListDataobject.OMSPage videoModel)
        {

            mVideoModels = videoModel;
            v_title_desc.setText(Html.fromHtml(Html.fromHtml(mVideoModels.getPostContent()).toString()));
          //  v_date.setText(mVideoModels.getPostDate());
            mVideoTitle.setText(mVideoModels.getPostTitle());
            txt_hits.setText(mVideoModels.getStats().getHits());
            txt_visit.setText(mVideoModels.getStats().getFormHits());
            txt_middleplays.setText(mVideoModels.getStats().getVideoMiddleViews());
            txt_videoplays.setText(mVideoModels.getStats().getVideoTotalViews());
            v_title_link.setText(mVideoModels.getPostUrl());
            txt_view_views.setText(mVideoModels.getStats().getHits());
            Glide.with(context).load(mVideoModels.getImage()).into(new DrawableImageViewTarget(img_picture));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Bundle args = new Bundle();
//                    args.putString("videoId",mVideoModels.getId());
//                    args.putString("title",mVideoModels.getDirectoryName());
//                    Intent intent = new Intent(context, WEB.class);
//                    intent.putExtras(args);

                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  //  context.  startActivity(new Intent("android.intent.action.VIEW", Uri.parse(mVideoModels.getPostUrl())));
                    Intent intent=new Intent(context, InappWebview.class);
                    intent.putExtra("url",mVideoModels.getPostUrl());
                    intent.putExtra("title",mVideoModels.getPostTitle());
                    context.startActivity(intent);
                }
            });
            btn_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Intent> targetedShareIntents = new ArrayList<Intent>();

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, mVideoModels.getPostUrl());

                    List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);

                    for (ResolveInfo resolveInfo : resInfo) {
                        String packageName = resolveInfo.activityInfo.packageName;

                        Intent targetedShareIntent = new Intent(Intent.ACTION_SEND);
                        targetedShareIntent.setType("text/plain");
                        targetedShareIntent.putExtra(Intent.EXTRA_TEXT, mVideoModels.getPostUrl());
                        targetedShareIntent.setPackage(packageName);

                        targetedShareIntents.add(targetedShareIntent);
                    }

// Add my own activity in the share Intent chooser

                    Intent chooserIntent = Intent.createChooser(
                            targetedShareIntents.remove(0), "Select app to share");

                    chooserIntent.putExtra(
                            Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));

                    context.startActivity(chooserIntent);
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
        if (vho instanceof PagesListAdapter.ViewHolder) {
            PagesListAdapter.ViewHolder vh = (PagesListAdapter.ViewHolder) vho;
            PagesListDataobject.OMSPage videoPos = mVideoList.get(pos);
            vh.bindVideo(videoPos);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == EMPTY_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list, parent, false);
            PagesListAdapter.EmptyViewHolder evh = new PagesListAdapter.EmptyViewHolder(v);
            return evh;
        }
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_list_row, parent, false);
        PagesListAdapter.ViewHolder vh = new PagesListAdapter.ViewHolder(v);
        return vh;
    }
    private static final int EMPTY_VIEW = 10;
}

