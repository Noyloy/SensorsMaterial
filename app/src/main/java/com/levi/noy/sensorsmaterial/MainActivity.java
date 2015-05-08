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
                // accelerometer
                setContentView(R.layout.fragment_main);
                mxAccelTV = (TextView)findViewById(R.id.x_a_tv);
                myAccelTV = (TextView)findViewById(R.id.y_a_tv);
                mzAccelTV = (TextView)findViewById(R.id.z_a_tv);
                mSensorManager.unregisterListener(MainActivity.this);
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                mSensorManager.registerListener(MainActivity.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case 1:
                // gravity
                setContentView(R.layout.grav_frag);
                mxGravTV = (TextView)findViewById(R.id.x_g_tv);
                myGravTV = (TextView)findViewById(R.id.y_g_tv);
                mzGravTV = (TextView)findViewById(R.id.z_g_tv);
                mSensorManager.unregisterListener(MainActivity.this);
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
                mSensorManager.registerListener(MainActivity.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case 2:
                // Gyro
                setContentView(R.layout.gyro_frag);
                mxGyroTV = (TextView)findViewById(R.id.x_gy_tv);
                myGyroTV = (TextView)findViewById(R.id.y_gy_tv);
                mzGyroTV = (TextView)findViewById(R.id.z_gy_tv);
                mSensorManager.unregisterListener(MainActivity.this);
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                mSensorManager.registerListener(MainActivity.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case 3:
                // linear
                setContentView(R.layout.linear_frag);
                mxLinearTV = (TextView)findViewById(R.id.x_l_tv);
                myLinearTV = (TextView)findViewById(R.id.y_l_tv);
                mzLinearTV = (TextView)findViewById(R.id.z_l_tv);
                mSensorManager.unregisterListener(MainActivity.this);
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                mSensorManager.registerListener(MainActivity.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
                break;
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.commit();
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.commit();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            mxAccelTV.setText(event.values[0]+"");
            myAccelTV.setText(event.values[1]+"");
            mzAccelTV.setText(event.values[2]+"");
        }
        else if (event.sensor.getType() == Sensor.TYPE_GRAVITY){
            mxGravTV.setText(event.values[0]+"");
            myGravTV.setText(event.values[1]+"");
            mzGravTV.setText(event.values[2] + "");
        }
        else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            mxGyroTV.setText(event.values[0]+"");
            myGyroTV.setText(event.values[1]+"");
            mzGyroTV.setText(event.values[2] + "");
        }
        else if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
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

    @Override
    protected void onResume() {
        super.onResume();
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(MainActivity.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
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
            switch(position)
            {
                case 0:
                    return new FragMain();
                case 1:
                    return new Grav();
                case 2:
                    return new Gyro();
                case 3:
                    return new Linear();
                default:
                    return new FragMain();
            }
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
}
