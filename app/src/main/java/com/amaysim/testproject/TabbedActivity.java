package com.amaysim.testproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amaysim.testproject.model.Collection;

import org.json.JSONObject;

import java.util.Iterator;

public class TabbedActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed, menu);
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
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {

    }

    public final static String titles[] = new String[]{"ACCOUNTS", "SUBSCRIPTIONS", "SERVICES", "PRODUCTS"};

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            int pos = getArguments().getInt(ARG_SECTION_NUMBER);
            textView.setText(titles[pos - 1]);
            LinearLayout ll_attributes_list = (LinearLayout) rootView.findViewById(R.id.ll_attributes_list);
            processDataToViews(getActivity(), ll_attributes_list, pos);
            return rootView;
        }

        private void processDataToViews(Context context, LinearLayout ll, int position) {
            // JSONObject jsonObject = new JSONObject(collectionStr);
            Collection collection = Collection.first(Collection.class);
            ll.removeAllViews();
            if (collection != null) {

                String str = "";

                if (position == 1)
                    str = collection.getAccounts();
                else if (position == 2)
                    str = collection.getSubscription();
                else if (position == 3)
                    str = collection.getServices();
                else if (position == 4)
                    str = collection.getProducts();


                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Iterator<String> keysIterator = jsonObject.keys();
                    while (keysIterator.hasNext()) {
                        String key = keysIterator.next();
                        String value = jsonObject.getString(key);

                        LinearLayout row_attribute = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.row_attibute, null);
                        TextView tv_attribute = (TextView) row_attribute.findViewById(R.id.tv_attribute);
                        TextView tv_value = (TextView) row_attribute.findViewById(R.id.tv_value);


                        if (key.equalsIgnoreCase("included-data-balance") ||
                                key.equalsIgnoreCase("included-credit-balance") ||
                                key.equalsIgnoreCase("included-rollover-credit-balance") ||
                                key.equalsIgnoreCase("included-rollover-data-balance") ||
                                key.equalsIgnoreCase("included-international-talk-balance")
                                ) {
                            tv_value.setText(Utils.mbToGB(value));
                        } else if (key.equalsIgnoreCase("price") || key.equalsIgnoreCase("credit")) {
                            tv_value.setText(Utils.centsToDollar(value));
                        } else {
                            tv_value.setText(Utils.removeNull(value));
                        }
                        tv_attribute.setText(Utils.removeDashes(key));

                        ll.addView(row_attribute);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "ACCOUNTS";
                case 1:
                    return "SUBSCRIPTION";
                case 2:
                    return "SERVICES";
                case 3:
                    return "PRODUCTS";
            }
            return null;
        }
    }
}
