package com.prouman.contact_act;

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
 * Created by jcs on 11/29/2016.
 */
public class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int UNSELECTED = -1;

    private RecyclerView recyclerView;
    private int selectedItem = UNSELECTED;
    private ExpandableLayout expandableLayout;
    ImageView ivContactImage;
    TextView tvContactName;
    TextView tvPhoneNumber,tvType;
    private int position;
    Button callBtn,msgBtn;
    LinearLayout inctiveBtn;
    public CheckBox chkSelected;

    public ViewHolder2(View v, RecyclerView recyclerView) {
        super(v);
        this.recyclerView = recyclerView;
        expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout2);
        ivContactImage = (ImageView) itemView.findViewById(R.id.ivContactImage);
        tvContactName = (TextView) itemView.findViewById(R.id.tvContactName);
        tvPhoneNumber = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
        chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);
      //  tvType = (TextView) itemView.findViewById(R.id.tvType);
        callBtn= (Button)itemView.findViewById(R.id.btn_call);
        msgBtn= (Button)itemView.findViewById(R.id.btn_msg);
        inctiveBtn= (LinearLayout)itemView.findViewById(R.id.layout_inactioe);
          itemView.setOnClickListener(this);

    }
    public void bind(int position) {
        this.position = position;

        //t.setText(position + ". Tap to expand");

        itemView.setSelected(false);
        expandableLayout.collapse(false);
    }

    @Override
    public void onClick(View view) {
        ViewHolder2 holder = (ViewHolder2) recyclerView.findViewHolderForAdapterPosition(selectedItem);
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