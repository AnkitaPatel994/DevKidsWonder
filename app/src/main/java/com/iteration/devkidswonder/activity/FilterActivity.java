package com.iteration.devkidswonder.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity {

    TextView txtMinPrice,txtMaxPrice,txtCategory,txtBrand;
    RangeSeekBar rangeseekbar;
    LinearLayout llFilterCategory,llFilterBrand;
    ArrayList<Category> CategoryArray = new ArrayList<>();
    ArrayList<String> CategoryIdArray = new ArrayList<>();
    ArrayList<String> CategoryNameArray = new ArrayList<>();
    ArrayList<Brand> BrandArray = new ArrayList<>();
    ArrayList<String> BrandIdArray = new ArrayList<>();
    ArrayList<String> BrandNameArray = new ArrayList<>();
    GetProductDataService productDataService;
    String CategoryId,BrandId;
    Button btnFilter;

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

        String cate_id = getIntent().getExtras().getString("cate_id");
        String brand_id = getIntent().getExtras().getString("brand_id");

        Toast.makeText(FilterActivity.this, cate_id+brand_id, Toast.LENGTH_SHORT).show();

        productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);

        txtMinPrice = (TextView) findViewById(R.id.txtMinPrice);
        txtMaxPrice = (TextView) findViewById(R.id.txtMaxPrice);

        rangeseekbar = (RangeSeekBar)findViewById(R.id.rangeseekbar);

        rangeseekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Number min_value = bar.getSelectedMinValue();
                Number max_value = bar.getSelectedMaxValue();

                int min =(int)min_value;
                int max =(int)max_value;

                txtMinPrice.setText(String.valueOf(min));
                txtMaxPrice.setText(String.valueOf(max));
            }
        });

        txtCategory = (TextView) findViewById(R.id.txtCategory);
        txtBrand = (TextView) findViewById(R.id.txtBrand);
        llFilterCategory = (LinearLayout)findViewById(R.id.llFilterCategory);

        llFilterCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryArray.clear();
                CategoryIdArray.clear();
                CategoryNameArray.clear();
                final Dialog dialog = new Dialog(FilterActivity.this,android.R.style.Theme_Light_NoTitleBar);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.type_dialog);
                dialog.setCancelable(true);
                TextView txtTypeName = (TextView)dialog.findViewById(R.id.txtTypeName);
                txtTypeName.setText("Category");
                ImageView ivTypeClose = (ImageView)dialog.findViewById(R.id.ivTypeClose);
                final ListView lvListType = (ListView)dialog.findViewById(R.id.lvListType);
                lvListType.setChoiceMode(lvListType.CHOICE_MODE_SINGLE);
                ivTypeClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
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
                        final ArrayAdapter<String> CategoryAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_single_choice, CategoryNameArray);
                        lvListType.setAdapter(CategoryAdapter);
                        lvListType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String value = CategoryAdapter.getItem(position);
                                CategoryId = CategoryIdArray.get(position);
                                dialog.dismiss();
                                txtCategory.setText(value);
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<CategoryList> call, Throwable t) {
                        Toast.makeText(FilterActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();
            }
        });
        llFilterBrand = (LinearLayout)findViewById(R.id.llFilterBrand);
        llFilterBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrandArray.clear();
                BrandIdArray.clear();
                BrandNameArray.clear();
                final Dialog dialog = new Dialog(FilterActivity.this,android.R.style.Theme_Light_NoTitleBar);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.type_dialog);
                dialog.setCancelable(true);
                TextView txtTypeName = (TextView)dialog.findViewById(R.id.txtTypeName);
                txtTypeName.setText("Brand");
                ImageView ivTypeClose = (ImageView)dialog.findViewById(R.id.ivTypeClose);
                final ListView lvListType = (ListView)dialog.findViewById(R.id.lvListType);
                lvListType.setChoiceMode(lvListType.CHOICE_MODE_SINGLE);
                ivTypeClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
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
                        final ArrayAdapter<String> BrandAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_single_choice, BrandNameArray);
                        lvListType.setAdapter(BrandAdapter);
                        lvListType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String value = BrandAdapter.getItem(position);
                                BrandId = BrandIdArray.get(position);
                                dialog.dismiss();
                                txtBrand.setText(value);
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<BrandList> call, Throwable t) {
                        Toast.makeText(FilterActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();
            }
        });

        btnFilter = (Button)findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String minva = txtMinPrice.getText().toString();
                String maxva = txtMaxPrice.getText().toString();
                String CatId = CategoryId;
                String BrId = BrandId;

                Toast.makeText(FilterActivity.this, minva+maxva+CatId+BrId, Toast.LENGTH_SHORT).show();
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
