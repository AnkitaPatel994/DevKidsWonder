package com.iteration.devkidswonder.activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iteration.devkidswonder.R;
import com.iteration.devkidswonder.adapter.ProductListAdapter;
import com.iteration.devkidswonder.model.Cart;
import com.iteration.devkidswonder.model.CartList;
import com.iteration.devkidswonder.model.Product;
import com.iteration.devkidswonder.model.ProductList;
import com.iteration.devkidswonder.network.GetProductDataService;
import com.iteration.devkidswonder.network.RetrofitInstance;
import com.iteration.devkidswonder.network.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rvSubCategoryProduct;
    ArrayList<Product> ProductListArray = new ArrayList<>();
    SessionManager session;
    int flag = 0;
    String ip_address,cate_id,cate_name,brand_id,brand_name,min_price,max_price;
    TextView textCartItemCount;
    int mCartItemCount = 1;
    ArrayList<Cart> cartProductListArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new SessionManager(SubCategoryActivity.this);
        flag = session.checkLogin();

        GetProductDataService productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);

        HashMap<String,String> user = session.getUserDetails();
        String user_id = user.get(SessionManager.user_id);
        String user_name = user.get(SessionManager.user_name);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview = navigationView.getHeaderView(0);
        TextView txt_login = (TextView)headerview.findViewById(R.id.txt_login);
        LinearLayout nav_header_ll = (LinearLayout)headerview.findViewById(R.id.nav_header_ll);

        if (flag == 1)
        {
            txt_login.setText(user_name);
            nav_header_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(SubCategoryActivity.this,MyProfileActivity.class);
                    startActivity(i);
                }
            });

            Call<CartList> CartListCall = productDataService.getCartData(user_id);
            CartListCall.enqueue(new Callback<CartList>() {
                @Override
                public void onResponse(Call<CartList> call, Response<CartList> response) {
                    String status = response.body().getStatus();
                    if (status.equals("1"))
                    {
                        cartProductListArray = response.body().getCartList();
                        mCartItemCount = cartProductListArray.size();
                        textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                        Log.d("CartItemCount",""+mCartItemCount);
                    }
                    else
                    {
                        mCartItemCount = 0;
                    }
                }

                @Override
                public void onFailure(Call<CartList> call, Throwable t) {
                    Toast.makeText(SubCategoryActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (flag == 0)
        {
            txt_login.setText("Login / Register");
            nav_header_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(SubCategoryActivity.this,SignInActivity.class);
                    startActivity(i);
                }
            });
            mCartItemCount = 0;
        }

        @SuppressLint("WifiManagerLeak")
        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        ip_address = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        cate_id = getIntent().getExtras().getString("cate_id");
        cate_name = getIntent().getExtras().getString("cate_name");
        brand_id = getIntent().getExtras().getString("brand_id");
        brand_name = getIntent().getExtras().getString("brand_name");
        min_price = getIntent().getExtras().getString("min_price");
        max_price = getIntent().getExtras().getString("max_price");



        rvSubCategoryProduct = (RecyclerView)findViewById(R.id.rvSubCategoryProduct);
        rvSubCategoryProduct.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),2);
        rvSubCategoryProduct.setLayoutManager(manager);

        Call<ProductList> ProductListCall = productDataService.getProductListData(cate_id,brand_id,min_price,max_price);
        ProductListCall.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                ProductListArray.clear();
                String Status = response.body().getStatus();
                String Message = response.body().getMessage();
                if (Status.equals("1"))
                {
                    ProductListArray = response.body().getProductList();
                    ProductListAdapter productListAdapter = new ProductListAdapter(SubCategoryActivity.this,ProductListArray,ip_address);
                    rvSubCategoryProduct.setAdapter(productListAdapter);
                }
                else
                {
                    Toast.makeText(SubCategoryActivity.this, Message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                Toast.makeText(SubCategoryActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(getIntent());
        finish();
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
        getMenuInflater().inflate(R.menu.sub_category, menu);

        final MenuItem menuItem = menu.findItem(R.id.menu_cart_pro);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    private void setupBadge() {
        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_filter_pro)
        {
            Intent i = new Intent(getApplicationContext(),FilterActivity.class);
            i.putExtra("cate_id",cate_id);
            i.putExtra("cate_name",cate_name);
            i.putExtra("brand_id",brand_id);
            i.putExtra("brand_name",brand_name);
            i.putExtra("min_price",min_price);
            i.putExtra("max_price",max_price);
            startActivity(i);
        }
        else if (id == R.id.menu_search_pro)
        {
            Intent i = new Intent(getApplicationContext(),SearchActivity.class);
            startActivity(i);
        }
        else if (id == R.id.menu_cart_pro)
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
        else if (id == R.id.nav_wishlist)
        {
            Intent i = new Intent(getApplicationContext(),WishListActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_order)
        {
            Intent i = new Intent(getApplicationContext(), MyOrderActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_website)
        {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://devkidswonder.com"));
            if(!MyStartActivity(i))
            {
                i.setData(Uri.parse("http://devkidswonder.com"));
                if(!MyStartActivity(i))
                {
                    Log.d("Like","Could not open browser");
                }
            }
        }
        else if (id == R.id.nav_aboutus)
        {
            Intent i = new Intent(getApplicationContext(), AboutUsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_contactus)
        {
            Intent i = new Intent(getApplicationContext(), ContactUsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_terms)
        {
            Intent i = new Intent(getApplicationContext(), TermsActivity.class);
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
