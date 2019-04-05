package com.iteration.devkidswonder.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.iteration.devkidswonder.R;
import com.iteration.devkidswonder.adapter.BestSellingProductListAdapter;
import com.iteration.devkidswonder.adapter.BrandListAdapter;
import com.iteration.devkidswonder.adapter.CategoryListAdapter;
import com.iteration.devkidswonder.model.Product;
import com.iteration.devkidswonder.model.BestSellingList;
import com.iteration.devkidswonder.model.Brand;
import com.iteration.devkidswonder.model.BrandList;
import com.iteration.devkidswonder.model.Category;
import com.iteration.devkidswonder.model.CategoryList;
import com.iteration.devkidswonder.model.Slider;
import com.iteration.devkidswonder.model.SliderList;
import com.iteration.devkidswonder.network.GetProductDataService;
import com.iteration.devkidswonder.network.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SliderLayout slBannerSlider;
    ArrayList<Slider> sliderListArray = new ArrayList<>();
    ArrayList<String> sliderImgArray = new ArrayList<>();

    RecyclerView rvCategoryList,rvBrandList,rvSellerProduct;
    ArrayList<Category> categoryListArray = new ArrayList<>();
    ArrayList<Brand> BrandListArray = new ArrayList<>();
    ArrayList<Product> BestSellingListArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        nav_header_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,SignInActivity.class);
                startActivity(i);
            }
        });

        GetProductDataService productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);

        /*================== Slider ========================*/
        slBannerSlider = (SliderLayout)findViewById(R.id.slBannerSlider);

        Call<SliderList> sliderListCall = productDataService.getSliderData();

        sliderListCall.enqueue(new Callback<SliderList>() {
            @Override
            public void onResponse(Call<SliderList> call, Response<SliderList> response) {
                sliderListArray = response.body().getSliderArrayList();

                for (int i=0;i<sliderListArray.size();i++)
                {
                    String banner = sliderListArray.get(i).getBanner();
                    String banner_path = RetrofitInstance.BASE_URL +banner;
                    sliderImgArray.add(banner_path);
                }

                for (String name : sliderImgArray) {
                    DefaultSliderView textSliderView = new DefaultSliderView(HomeActivity.this);
                    // initialize a SliderLayout
                    textSliderView
                            .image(String.valueOf(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    slBannerSlider.startAutoCycle();
                                }
                            });

                    slBannerSlider.addSlider(textSliderView);
                }
                slBannerSlider.setCustomAnimation(new DescriptionAnimation());
                slBannerSlider.setDuration(5000);
            }

            @Override
            public void onFailure(Call<SliderList> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        /*================== Category ========================*/
        rvCategoryList = (RecyclerView)findViewById(R.id.rvCategoryList);
        rvCategoryList.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),3);
        rvCategoryList.setLayoutManager(manager);

        Call<CategoryList> categoryListCall = productDataService.getCategoryData();

        categoryListCall.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                categoryListArray = response.body().getCategoryArrayList();
                CategoryListAdapter categoryListAdapter = new CategoryListAdapter(HomeActivity.this,categoryListArray);
                rvCategoryList.setAdapter(categoryListAdapter);
            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        /*================= Brand List =================*/
        rvBrandList = (RecyclerView)findViewById(R.id.rvBrandList);
        rvBrandList.setHasFixedSize(true);

        RecyclerView.LayoutManager manager2 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        rvBrandList.setLayoutManager(manager2);

        Call<BrandList> brandListCall = productDataService.getBrandData();

        brandListCall.enqueue(new Callback<BrandList>() {
            @Override
            public void onResponse(Call<BrandList> call, Response<BrandList> response) {
                BrandListArray = response.body().getBrandArrayList();
                BrandListAdapter brandListAdapter = new BrandListAdapter(HomeActivity.this,BrandListArray);
                rvBrandList.setAdapter(brandListAdapter);
            }

            @Override
            public void onFailure(Call<BrandList> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        /*================= Best Selling Product List =================*/
        rvSellerProduct = (RecyclerView)findViewById(R.id.rvSellerProduct);
        rvSellerProduct.setHasFixedSize(true);

        RecyclerView.LayoutManager manager3 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        rvSellerProduct.setLayoutManager(manager3);

        Call<BestSellingList> bestSellingListCall = productDataService.getBestSellingData();

        bestSellingListCall.enqueue(new Callback<BestSellingList>() {
            @Override
            public void onResponse(Call<BestSellingList> call, Response<BestSellingList> response) {
                BestSellingListArray = response.body().getBestSellingArrayList();
                BestSellingProductListAdapter bestSellingProductListAdapter = new BestSellingProductListAdapter(HomeActivity.this,BestSellingListArray);
                rvSellerProduct.setAdapter(bestSellingProductListAdapter);
            }

            @Override
            public void onFailure(Call<BestSellingList> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_search)
        {

        }
        else if (id == R.id.menu_cart)
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

        }
        else if (id == R.id.nav_cart)
        {
            Intent i = new Intent(getApplicationContext(),CartActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_wishlist)
        {
            /*Intent i = new Intent(getApplicationContext(),WishListActivity.class);
            startActivity(i);*/
        }
        else if (id == R.id.nav_order)
        {
           /* Intent i = new Intent(getApplicationContext(),MyOrderActivity.class);
            startActivity(i);*/
        }
        else if (id == R.id.nav_notification)
        {
           /* Intent i = new Intent(getApplicationContext(),NotificationActivity.class);
            startActivity(i);*/
        }
        else if (id == R.id.nav_offerZone)
        {
          /*  Intent i = new Intent(getApplicationContext(),OfferZoneActivity.class);
            startActivity(i);*/
        }
        else if (id == R.id.nav_rate)
        {
           /* Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.iteration.wondering"));
            if(!MyStartActivity(i))
            {
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.iteration.wondering"));
                if(!MyStartActivity(i))
                {
                    Log.d("Like","Could not open browser");
                }
            }*/
        }
        else if (id == R.id.nav_share)
        {
            Intent i=new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String body="https://play.google.com/store/apps/details?id=com.iteration.wondering";
            i.putExtra(Intent.EXTRA_SUBJECT,body);
            i.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(i,"Share using"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
