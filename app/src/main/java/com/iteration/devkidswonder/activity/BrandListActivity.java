package com.iteration.devkidswonder.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iteration.devkidswonder.R;
import com.iteration.devkidswonder.adapter.BrandAllListAdapter;
import com.iteration.devkidswonder.model.Brand;
import com.iteration.devkidswonder.model.BrandList;
import com.iteration.devkidswonder.network.GetProductDataService;
import com.iteration.devkidswonder.network.RetrofitInstance;
import com.iteration.devkidswonder.network.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rvAllBrandList;
    ArrayList<Brand> BrandAllListArray = new ArrayList<>();
    SessionManager session;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BrandAllListArray.clear();

        session = new SessionManager(BrandListActivity.this);
        flag = session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();
        String user_name = user.get(SessionManager.user_name);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview = navigationView.getHeaderView(0);
        TextView txt_login = (TextView) headerview.findViewById(R.id.txt_login);
        LinearLayout nav_header_ll = (LinearLayout) headerview.findViewById(R.id.nav_header_ll);

        if (flag == 1) {
            txt_login.setText(user_name);
            nav_header_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(BrandListActivity.this, MyProfileActivity.class);
                    startActivity(i);
                }
            });
        } else if (flag == 0) {
            txt_login.setText("Login / Register");
            nav_header_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(BrandListActivity.this, SignInActivity.class);
                    startActivity(i);
                }
            });
        }

        rvAllBrandList = (RecyclerView) findViewById(R.id.rvAllBrandList);
        rvAllBrandList.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 1);
        rvAllBrandList.setLayoutManager(manager);

        GetProductDataService productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);

        Call<BrandList> brandListCall = productDataService.getBrandData();

        brandListCall.enqueue(new Callback<BrandList>() {
            @Override
            public void onResponse(Call<BrandList> call, Response<BrandList> response) {
                BrandAllListArray = response.body().getBrandArrayList();
                BrandAllListAdapter brandAllListAdapter = new BrandAllListAdapter(BrandListActivity.this, BrandAllListArray);
                rvAllBrandList.setAdapter(brandAllListAdapter);
            }

            @Override
            public void onFailure(Call<BrandList> call, Throwable t) {
                Toast.makeText(BrandListActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_search) {
            Intent i = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(i);
        } else if (id == R.id.menu_cart) {
            Intent i = new Intent(getApplicationContext(), CartActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_cart) {
            Intent i = new Intent(getApplicationContext(), CartActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_wishlist) {
            Intent i = new Intent(getApplicationContext(), WishListActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_order) {
            Intent i = new Intent(getApplicationContext(), MyOrderActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_website) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://devkidswonder.com"));
            if (!MyStartActivity(i)) {
                i.setData(Uri.parse("http://devkidswonder.com"));
                if (!MyStartActivity(i)) {
                    Log.d("Like", "Could not open browser");
                }
            }
        } else if (id == R.id.nav_aboutus) {
            Intent i = new Intent(getApplicationContext(), AboutUsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_contactus) {
            Intent i = new Intent(getApplicationContext(), ContactUsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_terms) {
            Intent i = new Intent(getApplicationContext(), TermsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_rate) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.iteration.devkidswonder"));
            if (!MyStartActivity(i)) {
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.iteration.devkidswonder"));
                if (!MyStartActivity(i)) {
                    Log.d("Like", "Could not open browser");
                }
            }
        } else if (id == R.id.nav_share) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String body = "https://play.google.com/store/apps/details?id=com.iteration.devkidswonder";
            i.putExtra(Intent.EXTRA_SUBJECT, body);
            i.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(i, "Share using"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean MyStartActivity(Intent i) {
        try {
            startActivity(i);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }
}
