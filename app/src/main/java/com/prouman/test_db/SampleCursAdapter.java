package com.prouman.test_db;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prouman.R;
import com.prouman.filter.CustomFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcs on 11/29/2016.
 */





    public class SampleCursAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int UNSELECTED = -1;

        private RecyclerView recyclerView;
        private int selectedItem = UNSELECTED;
        public List<ContactTest> contactVOList,filterList;
        private Context mContext;
        CustomFilter filter;
    OnItemClickListener onItemClickListener;
// The items to display in your RecyclerView
        // private List<Object> items;

        private final int SENT_MESSAGE = 0, RECEIVED_MESSAGE = 1;
        // Provide a suitable constructor (depends on the kind of dataset)
        public SampleCursAdapter(List<ContactTest> contactVOList, Context mContext, RecyclerView recyclerView){
            this.contactVOList = contactVOList;
            this.mContext = mContext;
            this.recyclerView = recyclerView;
            //this.filterList=contactVOList;
        }


        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return contactVOList.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (Boolean.valueOf(contactVOList.get(position).getEmail_mOnline())) {
                return RECEIVED_MESSAGE;
            } else {
                return SENT_MESSAGE;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            RecyclerView.ViewHolder viewHolder = null;
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

            switch (viewType) {
                case SENT_MESSAGE:
                    View v1 = inflater.inflate(R.layout.connection_list_row, viewGroup, false);
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
            vh2.tvType.setText(contactVO.getEmail_mOnline());
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
            vh2.chkSelected.isClickable();
        }

        private void configureViewHolder1(ViewHolder1 vh1, final int position) {
            final ContactTest contactVO = contactVOList.get(position);
            //SQLiteDatabase db = getWritableDatabase();
            vh1.bind(position);
            final  String name= contactVO.getName();
            vh1.tvContactName.setText(contactVO.getName());
            vh1.tvPhoneNumber.setText(contactVO.getPhone());
            vh1.tvType.setText(contactVO.getEmail_mOnline());
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
                        contactVO.setEmail_mOnline("1");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                contactVOList.notify();
                                refreshBlockOverlay(position);
                            }
                        }, 0);
                    }
               /* ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });*/


                }
            });
            vh1.btn_connec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    {
                        contactVO.setEmail_mOnline("1");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                contactVOList.notify();
                                refreshBlockOverlay(position);
                            }
                        }, 0);
                    }
               /* ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });*/


                }
            });
           /* vh1.disConnBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,name ,Toast.LENGTH_LONG).show();
                }
            });*/
            vh1.chkSelected.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       contactVO.setEmail_mOnline("1");
                                                       new Handler().postDelayed(new Runnable() {
                                                           @Override
                                                           public void run() {

                                                               contactVOList.notify();
                                                               refreshBlockOverlay(position);
                                                           }
                                                       }, 0);
                                                   }
                                               }
            );
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
        public void updateData(ArrayList<ContactTest> viewModels) {
            contactVOList.clear();
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

        private interface OnItemClickListener {
            void onItemClick(View view, ContactTest viewModel);
        }

}
