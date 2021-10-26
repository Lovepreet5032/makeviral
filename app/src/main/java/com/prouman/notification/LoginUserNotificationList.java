package com.prouman.notification;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.prouman.R;

public class LoginUserNotificationList extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    ImageView layout_logo;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user_notification_list);

        layout_logo=(ImageView)findViewById(R.id.layout_logo);
        layout_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        if(getIntent().getExtras()!=null){
        mViewPager.setCurrentItem(getIntent().getExtras().getInt("index"));
        }
        for(int i=0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(10, 10,10, 10);
            //p.height=20;
            //p.width=20;
            tab.setLayoutParams(p);
//            tab.setMinimumWidth(100);
//            tab.setMinimumHeight(50);
            tab.setPadding(0,0,0,0);
            tab.setBackground(getResources().getDrawable(R.drawable.actionbarnormalbutton));
            tab.requestLayout();
        }

    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_user_notification_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    /**
     * A placeholder fragment containing a simple view.
     */
//    public static class PlaceholderFragment extends Fragment {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        public PlaceholderFragment() {
//        }
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_login_user_notification_list, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//            return rootView;
//        }
//    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position==0) {
                NotificationListFragment fragment = new NotificationListFragment();
                Bundle args = new Bundle();
                //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
               // fragment.setArguments(args);
                return fragment;
                //return PlaceholderFragment.newInstance(position + 1);
            }
            else if(position==1)
            {
                AdminNotification fragment = new AdminNotification();
                Bundle args = new Bundle();
                //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
                // fragment.setArguments(args);
                return fragment;
            }
            else
            {
                SentNotification fragment = new SentNotification();
                Bundle args = new Bundle();
                //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
                // fragment.setArguments(args);
                return fragment;
            }
            }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return LoginUserNotificationList.this.getString(R.string.received);
                case 1:
                    return LoginUserNotificationList.this.getString(R.string.admin);

                case 2:
                    return LoginUserNotificationList.this.getString(R.string.sent);
            }
            return null;
        }
    }
}
