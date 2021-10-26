package com.prouman.test_db;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prouman.R;

import net.cachapa.expandablelayout.ExpandableLayout;

/**
 * Created by om on 1/27/2017.
 */

public class ViewHolderDefaultContact extends RecyclerView.ViewHolder implements View.OnClickListener{
    private static final int UNSELECTED = -1;

    private RecyclerView recyclerView;
    private int selectedItem = UNSELECTED;
    public ExpandableLayout expandableLayout;
    ImageView ivContactImage;
    TextView tvContactName;
    TextView tvPhoneNumber,tvType,tvNew;
    private int position;
    LinearLayout connecBtn;
    public CheckBox chkSelected;
    public LinearLayout disConnBtn;
    public LinearLayout vhdefaultlayout;
    public Button btn_connec,btn_inactive_contact;
    public ViewHolderDefaultContact(View v, RecyclerView recyclerView) {
        super(v);
        this.recyclerView = recyclerView;

        expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout);
        ivContactImage = (ImageView) itemView.findViewById(R.id.ivContactImage);
        tvContactName = (TextView) itemView.findViewById(R.id.tvContactName);
        tvPhoneNumber = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
        // tvType = (TextView) itemView.findViewById(R.id.tvType);
        chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);
        //tvNew = (TextView) itemView.findViewById(R.id.tvNew);
        connecBtn= (LinearLayout) itemView.findViewById(R.id.btn_connect);
        disConnBtn= (LinearLayout) itemView.findViewById(R.id.btn_inactive_contact);
        btn_connec= (Button) itemView.findViewById(R.id.btn_connec);
        btn_inactive_contact= (Button) itemView.findViewById(R.id.btn_inactive_contac);
        vhdefaultlayout=(LinearLayout)itemView.findViewById(R.id.vhdefaultlayout);
        itemView.setOnClickListener(this);

    }
    public void bind(int position) {
        this.position = position;



        itemView.setSelected(false);
        expandableLayout.collapse(false);
    }

    @Override
    public void onClick(View view) {
        ViewHolderDefaultContact holder = (ViewHolderDefaultContact) recyclerView.findViewHolderForAdapterPosition(selectedItem);
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

    public LinearLayout getDisConnBtn() {
        return disConnBtn;
    }

    public void setDisConnBtn(LinearLayout disConnBtn) {
        this.disConnBtn = disConnBtn;
    }
}

