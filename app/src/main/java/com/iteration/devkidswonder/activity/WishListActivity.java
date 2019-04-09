package com.iteration.devkidswonder.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iteration.devkidswonder.R;
import com.iteration.devkidswonder.adapter.WishListAdapter;
import com.iteration.devkidswonder.model.Wishlist;
import com.iteration.devkidswonder.model.WishlistList;
import com.iteration.devkidswonder.network.GetProductDataService;
import com.iteration.devkidswonder.network.RetrofitInstance;
import com.iteration.devkidswonder.network.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SessionManager session;
    int flag = 0;
    RecyclerView rvProductWishlist;
    LinearLayout llWishlistEmpty;
    String user_id;
    ArrayList<Wishlist> wishListProductListArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new SessionManager(WishListActivity.this);
        flag = session.checkLogin();

        HashMap<String,String> user = session.getUserDetails();
        user_id = user.get(SessionManager.user_id);
        String user_name = user.get(SessionManager.user_name);

        wishListProductListArray.clear();
        GetProductDataService productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        llWishlistEmpty = (LinearLayout)findViewById(R.id.llWishlistEmpty);
        rvProductWishlist = (RecyclerView)findViewById(R.id.rvProductWishlist);

        View headerview = navigationView.getHeaderView(0);
        TextView txt_login = (TextView)headerview.findViewById(R.id.txt_login);
        LinearLayout nav_header_ll = (LinearLayout)headerview.findViewById(R.id.nav_header_ll);

        if (flag == 1)
        {
            txt_login.setText(user_name);
            nav_header_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(WishListActivity.this,MyProfileActivity.class);
                    startActivity(i);
                }
            });
            llWishlistEmpty.setVisibility(View.GONE);
            rvProductWishlist.setVisibility(View.VISIBLE);
        }
        else if (flag == 0)
        {
            txt_login.setText("Login / Register");
            nav_header_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(WishListActivity.this,SignInActivity.class);
                    startActivity(i);
                }
            });
            llWishlistEmpty.setVisibility(View.VISIBLE);
            rvProductWishlist.setVisibility(View.GONE);
        }

        rvProductWishlist.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        rvProductWishlist.setLayoutManager(manager);

        Call<WishlistList> WishlistListCall = productDataService.getWishlistData(user_id);
        WishlistListCall.enqueue(new Callback<WishlistList>() {
            @Override
            public void onResponse(Call<WishlistList> call, Response<WishlistList> response) {
                String status = response.body().getStatus();
                wishListProductListArray = response.body().getWishlistList();
                if (status.equals("1"))
                {
                    WishListAdapter wishListAdapter = new WishListAdapter(WishListActivity.this,wishListProductListArray,user_id);
                    rvProductWishlist.setAdapter(wishListAdapter);
                    llWishlistEmpty.setVisibility(View.GONE);
                    rvProductWishlist.setVisibility(View.VISIBLE);
                }
                else
                {
                    llWishlistEmpty.setVisibility(View.VISIBLE);
                    rvProductWishlist.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<WishlistList> call, Throwable t) {
                Toast.makeText(WishListActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnEmptyWhishlist = (Button)findViewById(R.id.btnEmptyWhishlist);
        btnEmptyWhishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WishListActivity.this,HomeActivity.class);
                startActivity(i);
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

        Intent i = new Intent(WishListActivity.this,HomeActivity.class);
        startActivity(i);
        finishAffinity();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_cart_wish)
        {
            Intent i = new Intent(getApplicationContext(),CartActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {
            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_cart)
        {
            Intent i = new Intent(getApplicationContext(),CartActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_order)
        {
            Intent i = new Intent(getApplicationContext(), MyOrderActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_rate)
        {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.iteration.devkidswonder"));
            if(!MyStartActivity(i))
            {
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.iteration.devkidswonder"));
                if(!MyStartActivity(i))
                {
                    Log.d("Like","Could not open browser");
                }
            }
        }
        else if (id == R.id.nav_share)
        {
            Intent i=new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String body="https://play.google.com/store/apps/details?id=com.iteration.devkidswonder";
            i.putExtra(Intent.EXTRA_SUBJECT,body);
            i.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(i,"Share using"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean MyStartActivity(Intent i) {
        try
        {
            startActivity(i);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }
}
