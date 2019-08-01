package com.shaheen.testapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;
import com.shaheen.testapp.R;
import com.shaheen.testapp.adapter.ViewPagerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //  private AdView mAdView;
    private Toolbar toolbar;
    private String[] category = {"hello", "this", "is", "me"};
    TextView tv_country;
    ImageView flag;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_country = (TextView) findViewById(R.id.country);
        flag = (ImageView) findViewById(R.id.flag);
        tv_country.setOnClickListener(this);

        category = getResources().getStringArray(R.array.countries);

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, category);

        // MobileAds.initialize(this, "YOUR_ADMOB_APP_ID");

        //banner
        /*AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
*/


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        findViewById(R.id.tabs).bringToFront();



    }

    private void ṁCountrySelection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose a country");

        final String[] countries = getResources().getStringArray(R.array.countries);
        final TypedArray img;
        img = getResources().obtainTypedArray(R.array.flags);
        builder.setItems(countries, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    default:
                        tv_country.setText(countries[which]);
                        flag.setImageDrawable(getDrawable(img.getResourceId(which, -1)));

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
                ṁCountrySelection();
                break;
            case R.id.drop:
                ṁCountrySelection();
                break;
                case R.id.flag:
                ṁCountrySelection();
                break;
        }
    }
}
