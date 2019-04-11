package com.iteration.devkidswonder.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.iteration.devkidswonder.R;
import com.iteration.devkidswonder.adapter.BrandListAdapter;
import com.iteration.devkidswonder.adapter.CategoryListAdapter;
import com.iteration.devkidswonder.model.Brand;
import com.iteration.devkidswonder.model.BrandList;
import com.iteration.devkidswonder.model.Category;
import com.iteration.devkidswonder.model.CategoryList;
import com.iteration.devkidswonder.network.GetProductDataService;
import com.iteration.devkidswonder.network.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity {

    TextView txtMinPrice,txtMaxPrice;
    ListView lvCategoryFilter,lvBrandFilter;
    RecyclerView rvBrandFilter;
    ArrayList<Category> CategoryArray = new ArrayList<>();
    ArrayList<String> CategoryIdArray = new ArrayList<>();
    ArrayList<String> CategoryNameArray = new ArrayList<>();
    ArrayList<Brand> BrandArray = new ArrayList<>();
    ArrayList<String> BrandIdArray = new ArrayList<>();
    ArrayList<String> BrandNameArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        GetProductDataService productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);

        txtMinPrice = (TextView) findViewById(R.id.txtMinPrice);
        txtMaxPrice = (TextView) findViewById(R.id.txtMaxPrice);

        lvCategoryFilter = (ListView) findViewById(R.id.lvCategoryFilter);
        lvBrandFilter = (ListView) findViewById(R.id.lvBrandFilter);

        //rvBrandFilter = (RecyclerView)findViewById(R.id.rvBrandFilter);

        Call<CategoryList> categoryListCall = productDataService.getCategoryData();
        categoryListCall.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                CategoryArray = response.body().getCategoryArrayList();
                for (int i=0;i<CategoryArray.size();i++)
                {
                    String Cat_Id = CategoryArray.get(i).getCategory_id();
                    CategoryIdArray.add(Cat_Id);
                    String Cat_Name = CategoryArray.get(i).getCategory_title();
                    CategoryNameArray.add(Cat_Name);
                }
                ArrayAdapter<String> CategoryAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, CategoryNameArray);
                lvCategoryFilter.setAdapter(CategoryAdapter);
            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {
                Toast.makeText(FilterActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        Call<BrandList> brandListCall = productDataService.getBrandData();
        brandListCall.enqueue(new Callback<BrandList>() {
            @Override
            public void onResponse(Call<BrandList> call, Response<BrandList> response) {
                BrandArray = response.body().getBrandArrayList();
                for (int i=0;i<BrandArray.size();i++)
                {
                    String Brand_Id = BrandArray.get(i).getBrand_id();
                    BrandIdArray.add(Brand_Id);
                    String Brand_Name = BrandArray.get(i).getBrand_name();
                    BrandNameArray.add(Brand_Name);
                }
                ArrayAdapter<String> CategoryAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, BrandNameArray);
                lvBrandFilter.setAdapter(CategoryAdapter);

            }

            @Override
            public void onFailure(Call<BrandList> call, Throwable t) {
                Toast.makeText(FilterActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

}
