package com.iterationtechnology.devkidswonder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iterationtechnology.devkidswonder.R;
import com.iterationtechnology.devkidswonder.model.Message;
import com.iterationtechnology.devkidswonder.network.GetProductDataService;
import com.iterationtechnology.devkidswonder.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderShippedActivity extends AppCompatActivity {

    Button btnShippingCancelOrder,btnShippingConShopping;
    String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_shipped);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        order_id = getIntent().getExtras().getString("order_id");
        final GetProductDataService productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);
        btnShippingCancelOrder = (Button)findViewById(R.id.btnShippingCancelOrder);
        btnShippingCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderid = order_id;
                Call<Message> DeleteOrderCall = productDataService.getDeleteOrderData(orderid);
                DeleteOrderCall.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        Intent i = new Intent(OrderShippedActivity.this,HomeActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(OrderShippedActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnShippingConShopping = (Button)findViewById(R.id.btnShippingConShopping);
        btnShippingConShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderShippedActivity.this,HomeActivity.class);
                startActivity(i);
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
