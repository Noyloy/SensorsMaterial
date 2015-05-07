package com.levi.noy.sensorsmaterial;

import java.util.Locale;
import java.util.zip.Inflater;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MainActivity extends Activity implements ActionBar.TabListener,SensorEventListener {
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView mxAccelTV,myAccelTV,mzAccelTV;
    private TextView mxGyroTV,myGyroTV,mzGyroTV;
    private TextView mxLinearTV,myLinearTV,mzLinearTV;
    private TextView mxGravTV,myGravTV,mzGravTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.

        mViewPager.setCurrentItem(tab.getPosition());
        switch (mViewPager.getCurrentItem()){
            case 0:
                Log.d("DEBUG", "now accel");
                // accelerometer
                mSensorManager.unregisterListener(MainActivity.this);
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                mSensorManager.registerListener(MainActivity.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case 1:
                // gravity

                mSensorManager.unregisterListener(MainActivity.this);
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
                mSensorManager.registerListener(MainActivity.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case 2:
                // Gyro


                mSensorManager.unregisterListener(MainActivity.this);
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                mSensorManager.registerListener(MainActivity.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case 3:
                // linear


                mSensorManager.unregisterListener(MainActivity.this);
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                mSensorManager.registerListener(MainActivity.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                break;
        }

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        PlaceholderFragment frag = null;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            setContentView(R.layout.fragment_main);
            mxAccelTV = (TextView)findViewById(R.id.x_a_tv);
            myAccelTV = (TextView)findViewById(R.id.y_a_tv);
            mzAccelTV = (TextView)findViewById(R.id.z_a_tv);
            mxAccelTV.setText(event.values[0]+"");
            myAccelTV.setText(event.values[1]+"");
            mzAccelTV.setText(event.values[2]+"");
        }
        else if (event.sensor.getType() == Sensor.TYPE_GRAVITY){
            setContentView(R.layout.grav_frag);
            mxGravTV = (TextView)findViewById(R.id.x_g_tv);
            myGravTV = (TextView)findViewById(R.id.y_g_tv);
            mzGravTV = (TextView)findViewById(R.id.z_g_tv);

            mxGravTV.setText(event.values[0]+"");
            myGravTV.setText(event.values[1]+"");
            mzGravTV.setText(event.values[2] + "");
        }
        else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            setContentView(R.layout.gyro_frag);
            mxGyroTV = (TextView)findViewById(R.id.x_gy_tv);
            myGyroTV = (TextView)findViewById(R.id.y_gy_tv);
            mzGyroTV = (TextView)findViewById(R.id.z_gy_tv);

            mxGyroTV.setText(event.values[0]+"");
            myGyroTV.setText(event.values[1]+"");
            mzGyroTV.setText(event.values[2] + "");
        }
        else if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            setContentView(R.layout.linear_frag);
            mxLinearTV = (TextView)findViewById(R.id.x_l_tv);
            myLinearTV = (TextView)findViewById(R.id.y_l_tv);
            mzLinearTV = (TextView)findViewById(R.id.z_l_tv);
            mxLinearTV.setText(event.values[0]+"");
            myLinearTV.setText(event.values[1]+"");
            mzLinearTV.setText(event.values[2] + "");
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
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
        public PlaceholderFragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);
            }
            return null;
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private TextView xTV,yTV,zTV;

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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int pos = 0;
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            if (savedInstanceState != null) pos = savedInstanceState.getInt(ARG_SECTION_NUMBER, 0);
            if (pos == 0) {
                rootView = inflater.inflate(R.layout.fragment_main, container, false);
                xTV = (TextView) rootView.findViewById(R.id.x_a_tv);
                yTV = (TextView) rootView.findViewById(R.id.y_a_tv);
                zTV = (TextView) rootView.findViewById(R.id.z_a_tv);
            }
            if (pos == 1) {
                rootView = inflater.inflate(R.layout.grav_frag, container, false);
                xTV = (TextView) rootView.findViewById(R.id.x_g_tv);
                yTV = (TextView) rootView.findViewById(R.id.y_g_tv);
                zTV = (TextView) rootView.findViewById(R.id.z_g_tv);
            } else if (pos == 2) {
                rootView = inflater.inflate(R.layout.gyro_frag, container, false);
                xTV = (TextView) rootView.findViewById(R.id.x_gy_tv);
                yTV = (TextView) rootView.findViewById(R.id.y_gy_tv);
                zTV = (TextView) rootView.findViewById(R.id.z_gy_tv);
            } else if (pos == 3) {
                rootView = inflater.inflate(R.layout.linear_frag, container, false);
                xTV = (TextView) rootView.findViewById(R.id.x_l_tv);
                yTV = (TextView) rootView.findViewById(R.id.y_l_tv);
                zTV = (TextView) rootView.findViewById(R.id.z_l_tv);
            }
            return rootView;
        }
        public void setTextViewText(float[] value){
            xTV.setText(value[0]+"");
            yTV.setText(value[1]+"");
            zTV.setText(value[2]+"");
        }
    }

}
