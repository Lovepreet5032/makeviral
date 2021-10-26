package com.prouman.activity;

import androidx.fragment.app.Fragment;

/**
 * Created by jcs on 12/7/2016.
 */
public class PrivateCategoryActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new PrivateCategoryFragment();
    }
}
