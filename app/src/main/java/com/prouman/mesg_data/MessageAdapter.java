package com.prouman.mesg_data;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.provider.Telephony;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.prouman.R;
import com.prouman.test_db.ContactDbHandler;
import com.prouman.test_db.ContactTest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dsingh on 6/23/2016.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    //ArrayList<Category> cModels;
    Context context;
     List<AllTemplatesCampigns.Template> messageModels;
     String builderNumber;
     String messageBody,messageSubject;
     ArrayList<ContactTest> arrId;
    ContactDbHandler db;
    public MessageAdapter(List<AllTemplatesCampigns.Template> messageModels, Context context,String builderNumber,ArrayList<ContactTest> arrId) {
       // this.cModels= cModels;

        this.messageModels= messageModels;
        this.context= context;
        this.builderNumber=builderNumber;
        this.arrId=arrId;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_row_email,parent,false);

        return new MyViewHolder(itemView) ;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
      // final MessageDataObjectNew.Template messageModelTest = messageModels.get(position);
      //  String result = cPos.getBody();
       // String mainMsg= String.valueOf(Html.fromHtml(result));
       //messageModels = cPos.getProds();
       //Category.MessageModelTest model = me;
     //   holder.webView.setText(messageModelTest.getBody());
        holder.webView.getSettings().setJavaScriptEnabled(true);
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        // 0 - for private mode
        String jsonstring = pref.getString("jsonarray","");
        JSONArray arrayjson = null;
        try {
            arrayjson = new JSONArray(jsonstring);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(null != arrayjson){
            for(int i=0;i<arrayjson.length();i++){
                try {
                    String string=arrayjson.get(i).toString();
                    if(null !=  messageModels && null != messageModels.get(position).getBody() && messageModels.get(position).getBody().contains(string)) {
                        if (null != messageModels.get(position).getSubject()) {
                        if (arrId.size() > 0 && null != string) {

                                if (string.equalsIgnoreCase("prospect_surname")) {
                                    messageModels.get(position).setBody(messageModels.get(position).getBody().replaceAll("\\[\\[" + string + "\\]\\]", arrId.get(0).getName()));
                                    messageModels.get(position).setSubject(messageModels.get(position).getSubject().replaceAll("\\[\\[" + string + "\\]\\]", arrId.get(0).getName()));
                                } else if (string.equalsIgnoreCase("prospect_name")) {
                                    messageModels.get(position).setBody(messageModels.get(position).getBody().replaceAll("\\[\\[" + string + "\\]\\]", arrId.get(0).getName()));
                                    messageModels.get(position).setSubject(messageModels.get(position).getSubject().replaceAll("\\[\\[" + string + "\\]\\]", arrId.get(0).getName()));
                                } else if (string.equalsIgnoreCase("prospect_first_name")) {
                                    messageModels.get(position).setBody(messageModels.get(position).getBody().replaceAll("\\[\\[" + string + "\\]\\]", arrId.get(0).getName()));
                                    messageModels.get(position).setSubject(messageModels.get(position).getSubject().replaceAll("\\[\\[" + string + "\\]\\]", arrId.get(0).getName()));
                                } else if (string.equalsIgnoreCase("prospect_last_name")) {
                                    messageModels.get(position).setBody(messageModels.get(position).getBody().replaceAll("\\[\\[" + string + "\\]\\]", arrId.get(0).getName()));
                                    messageModels.get(position).setSubject(messageModels.get(position).getSubject().replaceAll("\\[\\[" + string + "\\]\\]", arrId.get(0).getName()));
                                } else if (string.equalsIgnoreCase("prospect_email")) {
                                    messageModels.get(position).setBody(messageModels.get(position).getBody().replaceAll("\\[\\[" + string + "\\]\\]", arrId.get(0).getEmail_Id()));
                                    messageModels.get(position).setSubject(messageModels.get(position).getSubject().replaceAll("\\[\\[" + string + "\\]\\]", arrId.get(0).getEmail_Id()));
                                } else if (string.equalsIgnoreCase("prospect_phone")) {
                                    messageModels.get(position).setBody(messageModels.get(position).getBody().replaceAll("\\[\\[" + string + "\\]\\]", arrId.get(0).getPhone()));
                                    messageModels.get(position).setSubject(messageModels.get(position).getSubject().replaceAll("\\[\\[" + string + "\\]\\]", arrId.get(0).getPhone()));
                                } else {
                                    messageModels.get(position).setBody(messageModels.get(position).getBody().replaceAll("\\[\\[" + string + "\\]\\]", ""));
                                    messageModels.get(position).setSubject(messageModels.get(position).getSubject().replaceAll("\\[\\[" + string + "\\]\\]", ""));
                                }
                            } else {
                                messageModels.get(position).setBody(messageModels.get(position).getBody().replaceAll("\\[\\[" + string + "\\]\\]", ""));
                                messageModels.get(position).setSubject(messageModels.get(position).getSubject().replaceAll("\\[\\[" + string + "\\]\\]", ""));
                            }
                            //                        else if(string.equalsIgnoreCase("prospect_address")){}
//                        else if(string.equalsIgnoreCase("prospect_sity")){}


                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
       }
        holder.webView.loadDataWithBaseURL(null, messageModels.get(position).getBody(), "text/html", "UTF-8", null);
        holder.titleTv.setText(messageModels.get(position).getSubject());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageBody=messageModels.get(position).getBody();
                messageSubject=messageModels.get(position).getSubject();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                alertDialogBuilder.setTitle("Choose an Option");
                String[] options = {"Email","Share" };
                // set title
                // alertDialogBuilder.setTitle("Your Title");
                alertDialogBuilder.setItems(options, actionListener);
                alertDialogBuilder.setNegativeButton("Cancel", null);

                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

    }



    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleTv;
        WebView webView;
        CardView cardView;
       // Button button;


        public MyViewHolder(View itemView) {
            super(itemView);
            titleTv =(TextView) itemView.findViewById(R.id.txtTitle);
            webView=(WebView) itemView.findViewById(R.id.webView);
            cardView=(CardView)itemView.findViewById(R.id.card_view);
            //button=(Button)itemView.findViewById(R.id.btnSale);

        }
    }
    DialogInterface.OnClickListener actionListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    composeEmail(builderNumber.toString(),messageSubject,messageBody);
                    break;
                case 1:
                    chooseIntent();
                    break;

                default:
                    break;
            }
        }
    };
    private void sendSMS() {
        if (Build.VERSION.SDK_INT >= 19) {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(context);
            Intent sendIntent = new Intent("android.intent.action.SEND", Uri.parse("tel:" + builderNumber.toString()));
            sendIntent.putExtra("address", builderNumber.toString());
            sendIntent.setType("text/plain");
            sendIntent.putExtra("android.intent.extra.TEXT",messageBody);
            if (defaultSmsPackageName != null) {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            context.startActivity(sendIntent);
            return;
        }
        Intent smsIntent = new Intent("android.intent.action.VIEW");
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", builderNumber.toString());
        smsIntent.putExtra("sms_body", messageBody);
        context.startActivity(smsIntent);
    }
    private void chooseIntent() {
        List<Intent> targetedShareIntents = new ArrayList<Intent>();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(messageBody).toString());

        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);

        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedShareIntent = new Intent(Intent.ACTION_SEND);
            targetedShareIntent.setType("text/plain");
            targetedShareIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(messageBody).toString());
            targetedShareIntent.setPackage(packageName);


            if (!packageName.equals("com.facebook.katana")) { // Remove Facebook Intent share
                targetedShareIntents.add(targetedShareIntent);
            }
        }

// Add my own activity in the share Intent chooser
        Intent i = new Intent(context, context.getClass());
        targetedShareIntents.add(i);

        Intent chooserIntent = Intent.createChooser(
                targetedShareIntents.remove(0), "Select app to share");

        chooserIntent.putExtra(
                Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));

        context.startActivity(chooserIntent);
        // Intents with SEND action
    /*    PackageManager packageManager = getApplicationContext().getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(sendIntent, 0);

        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
        Resources resources = getApplicationContext().getResources();

        for (int j = 0; j < resolveInfoList.size(); j++) {
            ResolveInfo resolveInfo = resolveInfoList.get(j);
            String packageName = resolveInfo.activityInfo.packageName;
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setComponent(new ComponentName(packageName,
                    resolveInfo.activityInfo.name));
            intent.setType("text/plain");

            if (packageName.contains("android.intent.action.SEND")) {
                sendSMS();

            } else {
               *//* // skip android mail and gmail to avoid adding to the list twice
                if (packageName.contains("android.email") || packageName.contains("android.gm")) {
                    continue;*//*

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,messageTitle);
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,messageBody);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
*/





    }
    public void composeEmail(String addresses, String subject,String body) {
        db = new ContactDbHandler(context);
        for(int i=0;i<arrId.size();i++)
        {
            ContactTest contactVO=arrId.get(i);
            contactVO.setEmailShared("1");
            db.updateSentStatus(contactVO,"1");
        }
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"+builderNumber)); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
       // intent.putExtra("body", body);
        intent.putExtra(Intent.EXTRA_TEXT,Html.fromHtml(body).toString());
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent,"Email"));
        }
    }
}
