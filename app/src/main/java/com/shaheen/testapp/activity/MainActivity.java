package com.shaheen.testapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.shaheen.testapp.PrefManager;
import com.shaheen.testapp.R;
import com.shaheen.testapp.adapter.ViewPagerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    //  private AdView mAdView;
    TextView tv_country;
    ImageView flag;
    ViewPager viewPager;
    TabLayout tabLayout;
    LinearLayout LYT_county;
    PrefManager prefManager;
    String[] countries;
    TypedArray img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_country = findViewById(R.id.country);
        flag = findViewById(R.id.flag);
        tv_country.setOnClickListener(this);
        LYT_county = findViewById(R.id.county_lyt);
        prefManager = new PrefManager(this);

        countries = getResources().getStringArray(R.array.countries);
        img = getResources().obtainTypedArray(R.array.flags);
        tv_country.setText(countries[prefManager.getCountry()]);
        flag.setImageDrawable(getDrawable(img.getResourceId(prefManager.getCountry(), -1)));


        // MobileAds.initialize(this, "YOUR_ADMOB_APP_ID");

        //banner
        /*AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
*/


        viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        findViewById(R.id.tabs).bringToFront();


    }

    private void ṁCountrySelection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose a country");
        builder.setItems(countries, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    default:
                        tv_country.setText(countries[which]);
                        flag.setImageDrawable(getDrawable(img.getResourceId(which, -1)));
                        prefManager.setCountry(which);
                        viewPager.getAdapter().notifyDataSetChanged();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.country:
            case R.id.drop:
            case R.id.flag:
                ṁCountrySelection();
                break;

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position == 1) {
            LYT_county.setVisibility(View.GONE);
        } else {
            LYT_county.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
