package com.prouman.activity;



import com.prouman.fragment.CategoryFragment;

import androidx.fragment.app.Fragment;

/**
 * Created by dsingh on 9/1/2016.
 */
public class NinjaCategoryActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {

        return new CategoryFragment();
    }
}
