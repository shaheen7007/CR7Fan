package com.shaheen.testapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.shaheen.testapp.PrefManager;
import com.shaheen.testapp.R;
import com.shaheen.testapp.adapter.ViewPagerAdapter;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    //  private AdView mAdView;
    ImageButton menu;
    TextView tv_country;
    ImageView flag;
    ImageView drop;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    TabLayout tabLayout;
    LinearLayout LYT_county;
    PrefManager prefManager;
    String[] countries;
    TypedArray img;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.account:
                        Toast.makeText(MainActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.settings:
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.mycart:
                        prefManager.setIsLoggedIn(false);
                        dl.closeDrawer(Gravity.LEFT);
                        Toast.makeText(MainActivity.this, "My Cart", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }


                return true;

            }
        });


    }


    private void init() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayHomeAsUpEnabled(false);

        menu = findViewById(R.id.menu);
        menu.setOnClickListener(this);
        tv_country = findViewById(R.id.country);
        flag = findViewById(R.id.flag);
        drop = findViewById(R.id.drop);
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
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        findViewById(R.id.tabs).bringToFront();


        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();


        nv = (NavigationView) findViewById(R.id.nv);

        mSetNav();


    }

    private void mSetNav() {
        dl.closeDrawer(Gravity.LEFT);
        View hView =  nv.getHeaderView(0);
        CircleImageView nav_user = (CircleImageView) hView.findViewById(R.id.nav_propic);
        TextView app_name = (TextView) hView.findViewById(R.id.nav_appname);
        TextView user_name = (TextView) hView.findViewById(R.id.nav_username);
        SignInButton signIn = (SignInButton)hView.findViewById(R.id.nav_signin_btn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager.setIsLoggedIn(true);
                dl.closeDrawer(Gravity.LEFT);
                mSetNav();
            }
        });


        if (prefManager.getIsLoggedIn()){

            nav_user.setVisibility(View.VISIBLE);
            user_name.setVisibility(View.VISIBLE);
            app_name.setVisibility(View.GONE);
            signIn.setVisibility(View.GONE);

            Glide.with(MainActivity.this)
                    .load("https://images.unsplash.com/photo-1518806118471-f28b20a1d79d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80")
                    .into(nav_user);
        }
        else {
            nav_user.setVisibility(View.GONE);
            user_name.setVisibility(View.GONE);
            app_name.setVisibility(View.VISIBLE);
            signIn.setVisibility(View.VISIBLE);
        }
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
                        Objects.requireNonNull(viewPager.getAdapter()).notifyDataSetChanged();

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

        if (t.onOptionsItemSelected(item))
            return true;

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
            case R.id.menu:
                mSetNav();
                dl.openDrawer(Gravity.LEFT);
                break;

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position == 1) {
            //  LYT_county.setVisibility(View.GONE);
            tv_country.setEnabled(false);
            drop.setEnabled(false);
            flag.setEnabled(false);

            tv_country.setText(countries[0]);
            flag.setImageDrawable(getDrawable(img.getResourceId(0, -1)));


        } else {
            //   LYT_county.setVisibility(View.VISIBLE);
            tv_country.setEnabled(true);
            drop.setEnabled(true);
            flag.setEnabled(true);

            tv_country.setText(countries[prefManager.getCountry()]);
            flag.setImageDrawable(getDrawable(img.getResourceId(prefManager.getCountry(), -1)));

        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
