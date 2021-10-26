package com.prouman.contact_act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.prouman.R;

import net.cachapa.expandablelayout.ExpandableLayout;

/**
 * Created by jcs on 11/29/2016.
 */
public class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener{
    private static final int UNSELECTED = -1;

    private RecyclerView recyclerView;
    private int selectedItem = UNSELECTED;
    private ExpandableLayout expandableLayout;
    ImageView ivContactImage;
    TextView tvContactName;
    TextView tvPhoneNumber,tvType,tvNew;
    private int position;
    Button connecBtn;
    public CheckBox chkSelected;

    public ViewHolder1(View v, RecyclerView recyclerView) {
        super(v);
        this.recyclerView = recyclerView;

        expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout);
        ivContactImage = (ImageView) itemView.findViewById(R.id.ivContactImage);
        tvContactName = (TextView) itemView.findViewById(R.id.tvContactName);
        tvPhoneNumber = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
       // tvType = (TextView) itemView.findViewById(R.id.tvType);
        chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);
        //tvNew = (TextView) itemView.findViewById(R.id.tvNew);
        connecBtn= (Button)itemView.findViewById(R.id.btn_connect);
     //   disConnBtn= (Button)itemView.findViewById(R.id.btn_inactive_contact);
      itemView.setOnClickListener(this);

    }
    public void bind(int position) {
        this.position = position;



        itemView.setSelected(false);
        expandableLayout.collapse(false);
    }

    @Override
    public void onClick(View view) {
       ViewHolder1 holder = (ViewHolder1) recyclerView.findViewHolderForAdapterPosition(selectedItem);
        if (holder != null) {
            holder.itemView.setSelected(false);
            holder.expandableLayout.collapse();
        }

        if (position == selectedItem) {
            selectedItem = UNSELECTED;
        } else {
            itemView.setSelected(true);
            expandableLayout.expand();
            selectedItem = position;
        }
    }
}

