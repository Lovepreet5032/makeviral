package com.prouman.omspages;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prouman.R;
import com.prouman.Util.IntentConstant;

import java.util.List;

/**
 * Created by aseemchoudhary on 03/01/19.
 */

public class PageMainListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PageMainDataSection> mVideoList;
    Context context;

    public PageMainListAdapter(List<PageMainDataSection> mVideoList, Context context) {
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
        private PageMainDataSection mVideoModels;
        TextView link_pages;//,v_date,v_title_desc;

        public ViewHolder(View v) {
            super(v);
            link_pages = (TextView)v.findViewById(R.id.link_pages);
            //v_date = (TextView) v.findViewById(R.id.v_date);
            //v_title_desc = (TextView) v.findViewById(R.id.v_title_desc);
        }
        public  void bindVideo(PageMainDataSection videoModel)
        {

            mVideoModels = videoModel;
            link_pages.setText(mVideoModels.getSectionName());
           // v_title_desc.setText(Html.fromHtml(Html.fromHtml(mVideoModels.getPostContent()).toString()));
            //  v_date.setText(mVideoModels.getPostDate());
           // mVideoTitle.setText(mVideoModels.getPostTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent=new Intent(context,PageDetail.class);
                intent.putExtra(IntentConstant.SECTION_ID,mVideoModels.getId());
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
            PageMainDataSection videoPos = mVideoList.get(pos);
            vh.bindVideo(videoPos);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == EMPTY_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_list, parent, false);
            PageMainListAdapter.EmptyViewHolder evh = new PageMainListAdapter.EmptyViewHolder(v);
            return evh;
        }
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlink_pages, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    private static final int EMPTY_VIEW = 10;
}


