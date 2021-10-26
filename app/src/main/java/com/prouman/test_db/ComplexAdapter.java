package com.prouman.test_db;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.prouman.R;
import com.prouman.mesg_data.MessageListActivity;
import com.prouman.sms_template.SmsActivity;

import java.util.ArrayList;
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
    CustomTestFilter filter;
   // ArrayList<ContactTest> numberList;
   // CustomMainFilter mainFilter;
    private Context mContext;
    private boolean fromEmail=false;
    private OnItemClickListener onItemClickListener;
    private static boolean showCallButton=true;
// The items to display in your RecyclerView
    // private List<Object> items;

    private final static int SENT_MESSAGE = 0, RECEIVED_MESSAGE = 1,DEFAULT_VIEW=2;
// Provide a suitable constructor (depends on the kind of dataset)
    public ComplexAdapter(List<ContactTest> contactVOList, Context mContext, RecyclerView recyclerView){
    this.contactVOList = contactVOList;
    this.mContext = mContext;
    this.recyclerView = recyclerView;
        this.filterList = contactVOList;
      // numberList= new ArrayList<>();
        fromEmail = ((AppCompatActivity) mContext).getIntent().getExtras().getString("from").equals("emailclick");
       db= new ContactDbHandler(mContext);
}


// Return the size of your dataset (invoked by the layout manager)
@Override
public int getItemCount() {
        return contactVOList.size();
        }

@Override
public int getItemViewType(int position) {

        if (fromEmail) {
            if(contactVOList.get(position).getEmail_defaut().equals("1"))
            {
                return DEFAULT_VIEW;
            }
            else{
            return Integer.parseInt(contactVOList.get(position).getEmail_mOnline());}
        } else {
            if(contactVOList.get(position).getMobile_defaut().equals("1"))
            {
                return DEFAULT_VIEW;
            }
            else{
            return Integer.parseInt(contactVOList.get(position).getMobile_mOnline());}
        }

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
        case DEFAULT_VIEW:
            View v3 = inflater.inflate(R.layout.row_default_contact, viewGroup, false);
            viewHolder = new ViewHolderDefaultContact(v3,recyclerView);
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
        case DEFAULT_VIEW:
            ViewHolderDefaultContact vh3 = (ViewHolderDefaultContact) viewHolder;
            configureViewHolder3(vh3, position);
            break;
    
    }
        }

    private void configureViewHolder2(ViewHolder2 vh2, final int position) {
        final ContactTest contactVO = contactVOList.get(position);
        //SQLiteDatabase db = getWritableDatabase();
        vh2.bind(position);
        if(fromEmail&&contactVO.getEmailShared().equals("1")){
        vh2.vhconnectmainlayout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.round_messages));}//Color.parseColor("#32cd32"));}
        else if(!fromEmail&&contactVO.getMobileShared().equals("1"))
        {
            vh2.vhconnectmainlayout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.round_messages));
        }
        else {vh2.vhconnectmainlayout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.round_messagewhite));
            }


//       if(fromEmail) {
//           if (contactVO.getEmailShared().equals("1")) {
//               vh2.btn_share.setVisibility(View.VISIBLE);
//           }
//           else {
//               vh2.btn_share.setVisibility(View.GONE);
//           }
//       }
        if(fromEmail) {
                vh2.layout_call.setVisibility(View.GONE);
                vh2.text_message_email.setText("Email");
            }
//        else{
//           if (contactVO.getMobileShared().equals("1")) {
//               vh2.btn_share.setVisibility(View.VISIBLE);
//           }
//           else {
//               vh2.btn_share.setVisibility(View.GONE);
//           }
//       }
            if(contactVO.getIsPositionExpanded()==1)
        {
            vh2.expandableLayout.expand();
        }else{vh2.expandableLayout.collapse();}
        final  String name= contactVO.getName();
        if(fromEmail){vh2.tvPhoneNumber.setText(contactVO.getEmail_Id());}
        else{vh2.tvPhoneNumber.setText(contactVO.getPhone());}
        vh2.tvContactName.setText(contactVO.getName());

        if(showCallButton&&!fromEmail){
            vh2.callBtn.setVisibility(View.VISIBLE);
        }
        else{
            vh2.callBtn.setVisibility(View.GONE);
        }
        //vh2.tvType.setText(contactVO.getmOnline());
        if ((contactVO.getContactImage()) != null) {
            Bitmap contactImage = getContactImage(contactVO.getContactImage());
            vh2.ivContactImage.setImageBitmap(contactImage);
        }else {
            vh2.ivContactImage.setImageResource(R.drawable.ic_account_circle);
        }
        vh2.vhconnectmainlayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                expandCollapseView(position);
            }
        });
        vh2.layout_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // contactVO.setType(false);
                mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", contactVO.getPhone(), null)));
            }
        });
        vh2.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // contactVO.setType(false);
                mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", contactVO.getPhone(), null)));
            }
        });
        vh2.msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean invalidEntry=false;
                ArrayList<ContactTest> numberList =new ArrayList<ContactTest>();
                for(int i=0;i<contactVOList.size();i++)
                {
                   ContactTest tcontactVO=contactVOList.get(i);
                 if(tcontactVO.isSelected()) {
                     if (fromEmail) {
                         if (tcontactVO.getEmail_mOnline().equals("0") ||
                                 tcontactVO.getEmail_defaut().equals("1")) {
                             invalidEntry = true;
                         }
                     } else {
                         if (tcontactVO.getMobile_mOnline().equals("0") ||
                                 tcontactVO.getMobile_defaut().equals("1")) {
                             invalidEntry = true;
                         }
                     }
                     numberList.add(tcontactVO);
                 }
                }
                if(!invalidEntry&&numberList.size()>0) {
                    Intent msgIntent;
                    if(!fromEmail) {
                        msgIntent=new Intent(mContext, SmsActivity.class);
                        msgIntent.putParcelableArrayListExtra("phoneNumber", numberList);
                        msgIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(msgIntent);
                    }
                    else
                    {
                        msgIntent=new Intent(mContext, MessageListActivity.class);
                        msgIntent.putParcelableArrayListExtra("phoneNumber", numberList);
                        msgIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(msgIntent);
                    }
                }
                else{
                    Toast.makeText(mContext,"Please select valid contact",Toast.LENGTH_LONG).show();
                }
            }
        });
        vh2.btn_ms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean invalidEntry=false;
                ArrayList<ContactTest> numberList =new ArrayList<ContactTest>();
                for(int i=0;i<contactVOList.size();i++)
                {
                    ContactTest tcontactVO=contactVOList.get(i);
                    if(tcontactVO.isSelected()) {
                        if (fromEmail) {
                            if (tcontactVO.getEmail_mOnline().equals("0") ||
                                    tcontactVO.getEmail_defaut().equals("1")) {
                                invalidEntry = true;
                            }
                        } else {
                            if (tcontactVO.getMobile_mOnline().equals("0") ||
                                    tcontactVO.getMobile_defaut().equals("1")) {
                                invalidEntry = true;
                            }
                        }
                        numberList.add(tcontactVO);
                    }
                }
                if(!invalidEntry&&numberList.size()>0) {
                    Intent msgIntent;
                    if(!fromEmail) {
                        msgIntent=new Intent(mContext, SmsActivity.class);
                        msgIntent.putParcelableArrayListExtra("phoneNumber", numberList);
                        msgIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(msgIntent);
                    }
                    else
                    {
                        msgIntent=new Intent(mContext, MessageListActivity.class);
                        msgIntent.putParcelableArrayListExtra("phoneNumber", numberList);
                        msgIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(msgIntent);
                    }
                }
                else{
                    Toast.makeText(mContext,"Please select valid contact",Toast.LENGTH_LONG).show();
                }
            }
        });
        vh2.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             sharebuttonactin(contactVO,position);
            }
        });
        vh2.inctiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                disconnectbuttonactin(contactVO,position);
            }
        });
        vh2.btn_inactio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                disconnectbuttonactin(contactVO,position);
            }
        });
        vh2.chkSelected.setChecked(contactVO.isSelected());

        vh2.chkSelected.setTag(contactVO);


        vh2.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
          //      ContactTest contact = (ContactTest) cb.getTag();
//                if(cb.isChecked()) {
                checkAction(cb,position);
                    //  contact.setSelected(cb.isChecked());
                    //contactVO.setSelected(cb.isChecked());
                 //   numberList.add(contact);
//                }
//               else
//                {
//                    contactVO.setSelected(cb.isChecked());
///
//                }
            }
        });
    }

    private void configureViewHolder1(ViewHolder1 vh1, final int position) {
        final ContactTest contactVO = contactVOList.get(position);
      vh1.bind(position);
        if(contactVO.getIsPositionExpanded()==1)
        {
            vh1.expandableLayout.expand();
        }else{vh1.expandableLayout.collapse();}
        final  String name= contactVO.getName();
        vh1.tvContactName.setText(contactVO.getName());
        if(fromEmail){
        vh1.tvPhoneNumber.setText(contactVO.getEmail_Id());}
        else{vh1.tvPhoneNumber.setText(contactVO.getPhone());}
       // vh1.tvNew.setText("Disconect");
      //  vh1.tvType.setText(contactVO.getmOnline());
        if ((contactVO.getContactImage()) != null) {
            Bitmap contactImage = getContactImage(contactVO.getContactImage());
            vh1.ivContactImage.setImageBitmap(contactImage);
        }else {
            vh1.ivContactImage.setImageResource(R.drawable.ic_account_circle);
        }
        vh1.chkSelected.setChecked(contactVO.isSelected());

        vh1.chkSelected.setTag(contactVO);

        vh1.vh1mainlayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                expandCollapseView(position);
            }
        });
        vh1.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
//                ContactTest contact = (ContactTest) cb.getTag();
//                contactVO.setSelected(cb.isChecked());
//                refreshBlockOverlay(position);
                checkAction(cb,position);
                refreshBlockOverlay(position);

            }
        });
        vh1.connecBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                {
//                    if(fromEmail) {
//                        contactVO.setEmail_mOnline("1");
//                        contactVO.setEmail_defaut("0");
//                    }
//                    else{
//                        contactVO.setMobile_mOnline("1");
//                    contactVO.setMobile_defaut("0");}
//                    int i= db.updateContact(contactVO);
//                    if(i>0){refreshBlockOverlay(position);}
                    connectConnectbuttonaction(contactVO,position);
                }            }
        });
     /*   vh1.disConnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,name ,Toast.LENGTH_LONG).show();
            }
        });*/
    }
    private void configureViewHolder3(ViewHolderDefaultContact vh1, final int position) {
        final ContactTest contactVO = contactVOList.get(position);
        vh1.bind(position);
        if(contactVO.getIsPositionExpanded()==1)
        {
            vh1.expandableLayout.expand();
        }else{vh1.expandableLayout.collapse();}
        final  String name= contactVO.getName();
        vh1.tvContactName.setText(contactVO.getName());
        if(fromEmail){
        vh1.tvPhoneNumber.setText(contactVO.getEmail_Id());}
        else{vh1.tvPhoneNumber.setText(contactVO.getPhone());}
        // vh1.tvNew.setText("Disconect");
        //  vh1.tvType.setText(contactVO.getmOnline());
        if ((contactVO.getContactImage()) != null) {
            Bitmap contactImage = getContactImage(contactVO.getContactImage());
            vh1.ivContactImage.setImageBitmap(contactImage);
        }else {
            vh1.ivContactImage.setImageResource(R.drawable.ic_account_circle);
        }
        vh1.chkSelected.setChecked(contactVO.isSelected());

        vh1.chkSelected.setTag(contactVO);

        vh1.vhdefaultlayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                expandCollapseView(position);
            }
        });
        vh1.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
   //             ContactTest contact = (ContactTest) cb.getTag();
//                contactVO.setSelected(cb.isChecked());
                checkAction(cb,position);

            }
        });
        vh1.btn_connec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectConnectbuttonaction(contactVO,position);
            }
        });
        vh1.connecBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                connectConnectbuttonaction(contactVO,position);
                }
        });
        vh1.disConnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnectbuttonactin(contactVO,position);
            }
        });
        vh1.btn_inactive_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnectbuttonactin(contactVO,position);
            }
        });
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
        //contactVOList.clear();
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
        // Create new empty list to add matched elements to
        List<ContactTest> filtered = new ArrayList<>();

        // examine each element to build filtered list
        // remember to always use your original items list

        for(ContactTest s:  contactVOList   )
        {

            if ( s.getmOnline().equals("1"))
            {
                filtered.add(s);
            }
        }*/



    private interface OnItemClickListener {
        void onItemClick(View view, ContactTest viewModel);
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomTestFilter(filterList,this);
        }
        return filter;

    }
    private void checkAction(CheckBox cb,int position)
    {
//        int count=0;
//        ContactTest contactVO=contactVOList.get(position);
//        contactVO.setSelected(cb.isChecked());
//        for (int i = 0; i < contactVOList.size(); i++) {
//            ContactTest contactTest = contactVOList.get(i);
//            if (contactTest.getIsPositionExpanded() == 1) {
//                contactVOList.get(i).setIsPositionExpanded(-1);
//                notifyItemChanged(i);
//                count=count+1;
//            }
//            if(contactTest.isSelected())
//            {
//                count=count+1;
//            }
//
//        }
//        if(cb.isChecked()){
//            contactVOList.get(position).setIsPositionExpanded(1);
//        }
//        else{contactVOList.get(position).setIsPositionExpanded(-1);}
//        if(count>=1){showCallButton=false;}
//        else{showCallButton=true;}
//        notifyDataSetChanged();
//        //refreshBlockOverlay(position);
        expandCollapseView(position);
    }
    private void disconnectbuttonactin(ContactTest contact,int position)
    {
        int selected=0;
        ContactTest contactVO;
        for(int i=0;i<contactVOList.size();i++) {
            contactVO = contactVOList.get(i);
            if (contactVO.isSelected()) {
                selected=1;
                if (fromEmail) {
                    contactVO.setEmail_mOnline("0");
                    contactVO.setEmail_defaut("0");
                    contact.setEmailShared("0");
                } else {
                    contactVO.setMobile_mOnline("0");
                    contactVO.setMobile_defaut("0");
                    contact.setMobileShared("0");
                }
                int ii = db.updateContact(contactVO);
                if (ii > 0) {
                    notifyItemChanged(position);
                }
            }
        }
        if(selected==0)
        {
            if (fromEmail) {
                contact.setEmail_mOnline("0");
                contact.setEmail_defaut("0");
                contact.setEmailShared("0");
            } else {
                contact.setMobile_mOnline("0");
                contact.setMobile_defaut("0");
                contact.setMobileShared("0");
            }
            int ii = db.updateContact(contact);
            if (ii > 0) {
                notifyItemChanged(position);
            }
        }
    }
    private void connectConnectbuttonaction(ContactTest contact,int position)
    {
        ContactTest contactVO;
        int selected=0;
        for(int i=0;i<contactVOList.size();i++) {
            contactVO = contactVOList.get(i);
            if (contactVO.isSelected()) {
                selected=1;
                if (fromEmail) {
                    contactVO.setEmail_mOnline("1");
                    contactVO.setEmail_defaut("0");
                } else {
                    contactVO.setMobile_mOnline("1");
                    contactVO.setMobile_defaut("0");
                }
                int ii = db.updateContact(contactVO);
                if (ii > 0) {
                    refreshBlockOverlay(position);
                }
            }
        }
        if(selected==0)
        {
            if (fromEmail) {
                contact.setEmail_mOnline("1");
                contact.setEmail_defaut("0");
            } else {
                contact.setMobile_mOnline("1");
                contact.setMobile_defaut("0");
            }
            int ii = db.updateContact(contact);
            if (ii > 0) {
                refreshBlockOverlay(position);
            }
        }
    }
    private void expandCollapseView(int position)
    {
        int count=0;
        ContactTest contactVO=contactVOList.get(position);

            for (int i = 0; i < contactVOList.size(); i++) {
                ContactTest contactTest = contactVOList.get(i);
                if (contactTest.getIsPositionExpanded() == 1) {
                    contactVOList.get(i).setIsPositionExpanded(-1);
                    notifyItemChanged(i);
                }
                if(contactTest.isSelected())
                {
                    count=count+1;
                }
            }
            if(contactVO.isSelected())
            {
                contactVO.setSelected(false);
                contactVOList.get(position).setIsPositionExpanded(-1);
                count=count-1;
            }
            else{contactVO.setSelected(true);
                contactVOList.get(position).setIsPositionExpanded(1);
                count=count+1;
            }
        if(count>0)
        {

        }
        //}

        //notifyItemChanged(position);
        showCallButton = count < 2;
        notifyDataSetChanged();
    }
    private void sharebuttonactin(ContactTest contact,int position)
    {
//        int selected=0;
//        ContactTest contactVO;
//        for(int i=0;i<contactVOList.size();i++) {
//            contactVO = contactVOList.get(i);
//            if (contactVO.isSelected()) {
//                selected=1;
//                if (fromEmail) {
//                    contactVO.setEmailShared("1");
//                } else {
//                    contactVO.setMobileShared("1");
//                }
//                int ii = db.updateContact(contactVO);
//                if (ii > 0) {
//                    notifyItemChanged(position);
//                }
//            }
//        }
//        if(selected==0)
//        {
//            if (fromEmail) {
//                contact.setEmailShared("1");
//            } else {
//                contact.setMobileShared("1");
//            }
//            int ii = db.updateContact(contact);
//            if (ii > 0) {
//                notifyItemChanged(position);
//            }
//        }
    }

}