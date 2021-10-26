package com.prouman.contact_act;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.prouman.R;

import java.util.List;

/**
 * Created by jcs on 11/29/2016.
 */

public class ComplexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private static final int UNSELECTED = -1;
    ContactDbHandler db;
    private RecyclerView recyclerView;
    private int selectedItem = UNSELECTED;
    public List<ContactTest> contactVOList,filterList;
    FilterTest filter;
    //CustomMainFilter mainFilter;
    private Context mContext;

    private OnItemClickListener onItemClickListener;
// The items to display in your RecyclerView
    // private List<Object> items;

    private final static int SENT_MESSAGE = 0, RECEIVED_MESSAGE = 1;
// Provide a suitable constructor (depends on the kind of dataset)
    public ComplexAdapter(List<ContactTest> contactVOList, Context mContext, RecyclerView recyclerView){
    this.contactVOList = contactVOList;
    this.mContext = mContext;
    this.recyclerView = recyclerView;
    this.filterList=contactVOList;

    db= new ContactDbHandler(mContext);
}


// Return the size of your dataset (invoked by the layout manager)
@Override
public int getItemCount() {
        return contactVOList.size();
        }

@Override
public int getItemViewType(int position) {
  return Integer.parseInt(contactVOList.get(position).getmOnline());
}

@Override
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    RecyclerView.ViewHolder viewHolder = null;
    LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

    switch (viewType) {
        case SENT_MESSAGE:
            View v1 = inflater.inflate(R.layout.check_dis_row, viewGroup, false);
            viewHolder = new ViewHolder1(v1,recyclerView);
            break;
        case RECEIVED_MESSAGE:
            View v2 = inflater.inflate(R.layout.connection_list_row, viewGroup, false);
            viewHolder = new ViewHolder2(v2,recyclerView);
            break;
       /* default:
            *//*View v = inflater.inflate(android.R.layout.dis, viewGroup, false);
            viewHolder = new RecyclerViewSimpleTextViewHolder(v);*//*
            break;*/
    }
    return viewHolder;
        }

@Override
public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    switch (viewHolder.getItemViewType()) {
        case SENT_MESSAGE:
            ViewHolder1 vh1 = (ViewHolder1) viewHolder;
            configureViewHolder1(vh1, position);
            break;

        case RECEIVED_MESSAGE:
            ViewHolder2 vh2 = (ViewHolder2) viewHolder;
            configureViewHolder2(vh2, position);
            break;
    
    }
        }

    private void configureViewHolder2(ViewHolder2 vh2, int position) {
        final ContactTest contactVO = contactVOList.get(position);
        //SQLiteDatabase db = getWritableDatabase();
        vh2.bind(position);
        final  String name= contactVO.getName();
        vh2.tvContactName.setText(contactVO.getName());
        vh2.tvPhoneNumber.setText(contactVO.getPhone());
        //vh2.tvType.setText(contactVO.getmOnline());
        if ((contactVO.getContactImage()) != null) {
            Bitmap contactImage = getContactImage(contactVO.getContactImage());
            vh2.ivContactImage.setImageBitmap(contactImage);
        }else {
            vh2.ivContactImage.setImageResource(R.drawable.ic_account_circle);
        }
        vh2.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // contactVO.setType(false);
            }
        });
        vh2.msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,name ,Toast.LENGTH_LONG).show();
            }
        });
        vh2.inctiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,name ,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void configureViewHolder1(ViewHolder1 vh1, final int position) {
        final ContactTest contactVO = contactVOList.get(position);
        //SQLiteDatabase db = getWritableDatabase();
      vh1.bind(position);
        final  String name= contactVO.getName();
        vh1.tvContactName.setText(contactVO.getName());
        vh1.tvPhoneNumber.setText(contactVO.getPhone());
       // vh1.tvNew.setText("Disconect");
      //  vh1.tvType.setText(contactVO.getmOnline());
        if ((contactVO.getContactImage()) != null) {
            Bitmap contactImage = getContactImage(contactVO.getContactImage());
            vh1.ivContactImage.setImageBitmap(contactImage);
        }else {
            vh1.ivContactImage.setImageResource(R.drawable.ic_account_circle);
        }
        vh1.connecBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                {
                     contactVO.setmOnline("1");
                    // contactVOList.remove(position);
                    // contactVOList.add(contactVO);
                     // db.updateContact(contactVO);
                    //updateData(contactVOList);
                   // notifyItemChanged(position,contactVO);
                     // mContext.notifyAll();
                  //  recyclerView.notify();
                   refreshBlockOverlay(position);
                 // recyclerView.invalidate();
                }
               /* ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });*/


            }
        });
//        vh1.disConnBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext,name ,Toast.LENGTH_LONG).show();
//            }
//        });
    }
    private Bitmap getContactImage(byte[] photo) {
        int targetW = 50, targetH = 50;
        BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapFactory.decodeByteArray(photo, 0, photo.length, options);
        options.inJustDecodeBounds = true;
        int imageW = options.outWidth;
        int imageH = options.outHeight;
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(imageW / targetW, imageH / targetH);
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeByteArray(photo, 0, photo.length, options);
    }
    public void refreshBlockOverlay(int position) {
        notifyItemChanged(position);
    }
    public void updateData(List<ContactTest> viewModels) {
        //contactVOList.clcear();
        contactVOList.addAll(viewModels);
        notifyDataSetChanged();
    }
    public void addItem(int position, ContactTest viewModel) {
        contactVOList.add(position, viewModel);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        contactVOList.remove(position);
        notifyItemRemoved(position);
    }

   /* public void filterConnected()
    {


        mainFilter=new CustomMainFilter(this,filterList);
        if(mainFilter==null)
        {
            mainFilter=new CustomMainFilter(this,filterList);
        }
      //  return mainFilter;

    }*/
      /*  // Create new empty list to add matched elements to
        List<ContactTest> filtered = new ArrayList<>();

        // examine each element to build filtered list
        // remember to always use your original items list

        for(ContactTest s:  contactVOList   )
        {

            if ( s.getmOnline().equals("1"))
            {
                filtered.add(s);
            }
        }

        //set new (filterd) current list of items
        this.filterList = filtered;

        //notify ListView to Rebuild
        notifyDataSetChanged();*/


    private interface OnItemClickListener {
        void onItemClick(View view, ContactTest viewModel);
    }
/*
    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomTestFilter(this,filterList);
        }
        return filter;

    }*/
@Override
public Filter getFilter() {
    if(filter==null)
    {
        filter=new FilterTest(filterList,this);
    }
    return filter;

}
}