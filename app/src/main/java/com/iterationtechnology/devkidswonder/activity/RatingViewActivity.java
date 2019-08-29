package com.iterationtechnology.devkidswonder.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.iterationtechnology.devkidswonder.R;
import com.iterationtechnology.devkidswonder.adapter.CategoryAllListAdapter;
import com.iterationtechnology.devkidswonder.adapter.RatingListAdapter;
import com.iterationtechnology.devkidswonder.model.CategoryList;
import com.iterationtechnology.devkidswonder.model.Rating;
import com.iterationtechnology.devkidswonder.model.RatingList;
import com.iterationtechnology.devkidswonder.network.GetProductDataService;
import com.iterationtechnology.devkidswonder.network.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingViewActivity extends AppCompatActivity {

    RecyclerView rvRatingViewList;
    ArrayList<Rating> RatingListArray = new ArrayList<>();
    GetProductDataService productDataService;
    String rating_pro_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);

        rating_pro_id = getIntent().getExtras().getString("pro_id");

        rvRatingViewList = (RecyclerView)findViewById(R.id.rvRatingViewList);
        rvRatingViewList.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),1);
        rvRatingViewList.setLayoutManager(manager);

        Call<RatingList> RatingListCall = productDataService.getViewRatingData(rating_pro_id);

        RatingListCall.enqueue(new Callback<RatingList>() {
            @Override
            public void onResponse(Call<RatingList> call, Response<RatingList> response) {
                String Status = response.body().getStatus();
                String message = response.body().getMessage();
                if (Status.equals("1"))
                {
                    Log.d("message",""+message);
                    RatingListArray = response.body().getRatingList();
                    RatingListAdapter ratingListAdapter = new RatingListAdapter(RatingViewActivity.this,RatingListArray);
                    rvRatingViewList.setAdapter(ratingListAdapter);
                }
                else
                {
                    Log.d("message",""+message);
                }
            }

            @Override
            public void onFailure(Call<RatingList> call, Throwable t) {
                Toast.makeText(RatingViewActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
