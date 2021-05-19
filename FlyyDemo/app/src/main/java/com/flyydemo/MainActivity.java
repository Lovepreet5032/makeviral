package com.flyydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import theflyy.com.flyy.Flyy;
import theflyy.com.flyy.helpers.FlyyUtility;

public class MainActivity extends AppCompatActivity {
    TextInputEditText etName,etPhone;
    MaterialButton btnNext;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName=findViewById(R.id.etName);
        etPhone=findViewById(R.id.etPhone);
        btnNext=findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etName.getText().toString().isEmpty()){
                    Toast.makeText(context,"Please enter your name!",Toast.LENGTH_LONG).show();
                }else if(etPhone.getText().toString().isEmpty()){
                    Toast.makeText(context,"Please enter your phone number!",Toast.LENGTH_LONG).show();
                }else if(etPhone.getText().toString().trim().length() !=10){
                    Toast.makeText(context,"Please enter your valid phone number!",Toast.LENGTH_LONG).show();
                }else {
                    Flyy.setUser(etPhone.getText().toString().trim());
                    Flyy.setUsername(etName.getText().toString().trim());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            callNavigation();
                        }
                    }, 2000);
                }
            }
        });
    }

    private void callNavigation(){

        FlyyUtility.getAppInfoFromSP(context);
        Flyy.navigateToTournamentListActivity(this,"Tournaments");
        finish();
    }
}