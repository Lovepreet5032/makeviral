package com.prouman.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.prouman.R;
import com.prouman.activity.NinjaMemberHome;
import com.prouman.filter.CustomFilter;
import com.prouman.model.ContactVO;
import com.prouman.sms_template.SmsActivity;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by dsingh on 11/2/2016.
 */

public class SampleAdapterView extends RecyclerView.Adapter<SampleAdapterView.ViewHolder> implements Filterable {
   private static final int UNSELECTED = -1;

    private RecyclerView recyclerView;
    private int selectedItem = UNSELECTED;
    public List<ContactVO> contactVOList, filterList;
    private Context mContext;
    String number;
    ArrayList<String> numberList;
    CustomFilter filter;
    NinjaMemberHome activity;

    private final static int SENT_MESSAGE = 0, RECEIVED_MESSAGE = 1;

    public SampleAdapterView(List<ContactVO> contactVOList, Context mContext, RecyclerView recyclerView) {
        this.contactVOList = contactVOList;
        this.mContext = mContext;
        this.recyclerView = recyclerView;
        this.filterList = contactVOList;
        numberList= new ArrayList<>();
       // db= new ContactDbHandler(mContext);
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.connection_list_row, parent, false);
        return new ViewHolder(itemView);

    }


  @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ContactVO contactVO = contactVOList.get(position);
        holder.bind(position);
        final String name = contactVO.getContactName();
         number = contactVO.getContactNumber();
        holder.tvContactName.setText(contactVO.getContactName());
        holder.tvPhoneNumber.setText(contactVO.getContactNumber());
        if ((contactVO.getContactImage()) != null) {
//            Bitmap contactImage = getContactImage(contactVO.getContactImage());
//            holder.ivContactImage.setImageBitmap(contactImage);
            Picasso.with(mContext).load(contactVO.getContactImage()).into(holder.ivContactImage);
        } else {
            holder.ivContactImage.setImageResource(R.drawable.ic_account_circle);
        }
        holder.connecBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           // contactVOList.setType("Connected");
            }
        });
        holder.msgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent msgIntent = new Intent(mContext,SmsActivity.class);
                msgIntent.putExtra("phoneNumber",numberList);
                msgIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(msgIntent);
                //sendSMS(number);
            }
        });
        holder.disConnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactVOList.remove(position);
                notifyDataSetChanged();
            }
        });
      holder.chkSelected.setChecked(contactVO.isSelected());

      holder.chkSelected.setTag(contactVO);


      holder.chkSelected.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              CheckBox cb = (CheckBox) v;
              ContactVO contact = (ContactVO) cb.getTag();

              //  contact.setSelected(cb.isChecked());
              contactVO.setSelected(cb.isChecked());
              numberList.add(contact.getContactNumber());
               /* Toast.makeText(
                        v.getContext(),
                        "Clicked on Checkbox: " + cb.getText() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();*/
          }
      });
    }

    @Override
    public int getItemCount() {
        return contactVOList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
         //   filter=new CustomFilter(filterList,this);
        }
        return filter;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ExpandableLayout expandableLayout;
        ImageView ivContactImage;
        TextView tvContactName;
        TextView tvPhoneNumber;
        private int position;
        Button connecBtn,msgbtn;
        LinearLayout disConnBtn;
        public CheckBox chkSelected;
        public ViewHolder(View itemView) {
            super(itemView);


            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout2);
            ivContactImage = (ImageView) itemView.findViewById(R.id.ivContactImage);
            tvContactName = (TextView) itemView.findViewById(R.id.tvContactName);
            tvPhoneNumber = (TextView) itemView.findViewById(R.id.tvPhoneNumber);
            connecBtn= (Button)itemView.findViewById(R.id.btn_call);
            msgbtn= (Button)itemView.findViewById(R.id.btn_msg);
            disConnBtn= (LinearLayout)itemView.findViewById(R.id.layout_inactioe);
            chkSelected = (CheckBox) itemView.findViewById(R.id.chkSelected);
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
            ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
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
    private void sendSMS(String phone) {
        if (Build.VERSION.SDK_INT >= 19) {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(mContext);
            Intent sendIntent = new Intent("android.intent.action.SEND", Uri.parse("tel:" + phone));
            sendIntent.putExtra("address", phone);
            sendIntent.setType("text/plain");
            sendIntent.putExtra("android.intent.extra.TEXT", "");
            if (defaultSmsPackageName != null) {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(sendIntent);
            return;
        }
        Intent smsIntent = new Intent("android.intent.action.VIEW");
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", phone);
        smsIntent.putExtra("sms_body", "message");
        smsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(smsIntent);
    }

    private void onCall(String phone) {
        if (Build.VERSION.SDK_INT >= 19) {
            int permissionCheck = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);
            TelephonyManager tm = (TelephonyManager)mContext.getSystemService(TELEPHONY_SERVICE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        activity,
                        new String[]{Manifest.permission.CALL_PHONE},
                        123);
            } else {
                Intent sendIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
                sendIntent.putExtra("address", phone);
                if (tm != null && tm.getSimState() == 5) {
                    sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(sendIntent);
                    return;
                }
            }


            return;
        }
        Intent callIntent = new Intent("android.intent.action.DIAL");
        callIntent.setPackage("com.android.phone");
        callIntent.setData(Uri.parse(phone));
        callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(callIntent);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall(number);
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }
    public void refreshBlockOverlay(int position) {
        notifyItemChanged(position);
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

}