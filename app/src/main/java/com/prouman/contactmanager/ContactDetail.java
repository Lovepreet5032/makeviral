package com.prouman.contactmanager;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.prouman.R;

public class ContactDetail extends AppCompatActivity {
 TextView txt_fname_lname,txt_date_created,txt_email,txt_phone,txt_address
         ,txt_city,txt_state,txt_zip,txt_country,txt_location,txt_device,
    txt_browser,txt_sourcetype;
 ImageView img_picture,layout_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        txt_fname_lname=(TextView)findViewById(R.id.txt_fname_lname);
        txt_date_created=(TextView)findViewById(R.id.txt_date_created);
        txt_email=(TextView)findViewById(R.id.txt_email);
        txt_phone=(TextView)findViewById(R.id.txt_phone);
        txt_address=(TextView)findViewById(R.id.txt_address);
        txt_city=(TextView)findViewById(R.id.txt_city);
        txt_state=(TextView)findViewById(R.id.txt_state);
        txt_zip=(TextView)findViewById(R.id.txt_zip);
        txt_country=(TextView)findViewById(R.id.txt_country);
        txt_location=(TextView)findViewById(R.id.txt_location);
        txt_device=(TextView)findViewById(R.id.txt_device);
        txt_browser=(TextView)findViewById(R.id.txt_browser);
        txt_sourcetype=(TextView)findViewById(R.id.txt_sourcetype);
        layout_logo=(ImageView)findViewById(R.id.layout_logo);
        img_picture=(ImageView)findViewById(R.id.img_picture);
        ContactManagerDataObject.ContactManagerListData contactManagerListData=getIntent().getExtras().getParcelable("c");
        txt_fname_lname.setText("Name : "+ contactManagerListData.getName());
        txt_email.setText("Email : "+contactManagerListData.getEmail());
        txt_phone.setText("Phone : "+contactManagerListData.getPhone());
        txt_date_created.setText("Date Time : "+contactManagerListData.getDateCreated());

        txt_address.setText("Address : "+contactManagerListData.getAddress());
        txt_city.setText("City : "+contactManagerListData.getCity());
        txt_state.setText("State : "+contactManagerListData.getState());
        txt_country.setText("Country : "+contactManagerListData.getCountry());
       // txt_zip.setText(contactManagerListData.getZip());

        txt_location.setText("Location : "+contactManagerListData.getLocation());
        txt_device.setText("Device : "+contactManagerListData.getDevice());
        txt_browser.setText("Browser : "+contactManagerListData.getBrowser());
        txt_sourcetype.setText("Source : "+contactManagerListData.getSourceType());
        layout_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        try{
        Picasso.with(this).load(contactManagerListData.getPicture()).placeholder(R.drawable.icon_user).into(img_picture);
        }catch (Exception e)
        {
            img_picture.setBackground(getResources().getDrawable(R.drawable.icon_user));
        }
    }
}
