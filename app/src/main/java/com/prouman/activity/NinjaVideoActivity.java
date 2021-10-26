package com.prouman.activity;

import android.content.Intent;

import com.prouman.fragment.NinjaVideoFragment;

import androidx.fragment.app.Fragment;

/**
 * Created by dsingh on 9/1/2016.
 */
public class NinjaVideoActivity extends SingleFragmentActivity {
    String catVID;
    @Override
    public Fragment createFragment() {
        Intent intent= getIntent();
        catVID =  intent.getStringExtra("catID");

        return  NinjaVideoFragment.newInstance(catVID);
    }
}
