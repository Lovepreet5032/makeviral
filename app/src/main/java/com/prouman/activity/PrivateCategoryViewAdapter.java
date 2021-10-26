package com.prouman.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prouman.R;
import com.prouman.model.CategoryModel;

import java.util.List;

/**
 * Created by jcs on 12/7/2016.
 */
public class PrivateCategoryViewAdapter  extends RecyclerView.Adapter<PrivateCategoryViewAdapter.CatViewHolder> {
    List<CategoryModel> mCatList;
    Context context;

    public PrivateCategoryViewAdapter(List<CategoryModel> mCatList, Context context) {
        this.mCatList = mCatList;
        this.context = context;
    }

    @Override
    public PrivateCategoryViewAdapter.CatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View catView = LayoutInflater.from(context).inflate(R.layout.cat_view, parent, false);
        return new CatViewHolder(catView);
    }

    @Override
    public void onBindViewHolder(PrivateCategoryViewAdapter.CatViewHolder holder, int position) {
        CategoryModel catPos = mCatList.get(position);
        holder.bindCat(catPos);

    }

    @Override
    public int getItemCount() {
        return mCatList.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder {
        TextView catName;
        private CategoryModel vhCatModel;

        public CatViewHolder(View itemView) {
            super(itemView);
            catName = (TextView) itemView.findViewById(R.id.cat_name);
        }

        public void bindCat(CategoryModel cModel) {
            vhCatModel = cModel;
            final String catHolID = vhCatModel.getId();
            catName.setText(vhCatModel.getCategeoryName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putString("catID", catHolID);
                    Intent intent = new Intent(context,MemberVideoActivity.class);
                    intent.putExtras(args);
                    context.startActivity(intent);
                }
            });

        }

    }
}