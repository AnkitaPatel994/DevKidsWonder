package com.iteration.devkidswonder.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iteration.devkidswonder.R;
import com.iteration.devkidswonder.model.Message;
import com.iteration.devkidswonder.network.GetProductDataService;
import com.iteration.devkidswonder.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddShippingAddressActivity extends AppCompatActivity {

    String user_id,firstname,lastname,email,contact,Address,City,Pincode,fullAddress,TotalCartPrice,ShippingPrice;
    EditText txtShippingName,txtShippingAddress,txtShippingCity,txtShippingPincode;
    Button btnShippingSave;
    GetProductDataService productDataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shipping_address);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        user_id = getIntent().getExtras().getString("user_id");
        firstname = getIntent().getExtras().getString("firstname");
        lastname = getIntent().getExtras().getString("lastname");
        email = getIntent().getExtras().getString("email");
        contact = getIntent().getExtras().getString("contact");
        Address = getIntent().getExtras().getString("address");
        City = getIntent().getExtras().getString("city");
        Pincode = getIntent().getExtras().getString("pincode");
        TotalCartPrice = getIntent().getExtras().getString("TotalCartPrice");
        ShippingPrice = getIntent().getExtras().getString("ShippingPrice");

        txtShippingName = (EditText)findViewById(R.id.txtShippingName);
        txtShippingName.setText(firstname +" "+lastname);
        txtShippingAddress = (EditText)findViewById(R.id.txtShippingAddress);
        txtShippingCity = (EditText)findViewById(R.id.txtShippingCity);
        txtShippingPincode = (EditText)findViewById(R.id.txtShippingPincode);
        btnShippingSave = (Button)findViewById(R.id.btnShippingSave);

        fullAddress = txtShippingAddress.getText().toString()+", "+txtShippingCity.getText().toString()+", "+txtShippingPincode.getText().toString();
        productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);
        btnShippingSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog dialog = new ProgressDialog(AddShippingAddressActivity.this);
                dialog.setMessage("Loading...");
                dialog.setCancelable(true);
                dialog.show();

                Call<Message> InsertShippingCall = productDataService.getInsertShippingData(user_id,fullAddress);
                InsertShippingCall.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        dialog.dismiss();
                        String message = response.body().getMessage();
                        Toast.makeText(AddShippingAddressActivity.this, message, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AddShippingAddressActivity.this,DeliveryActivity.class);
                        i.putExtra("user_id",user_id);
                        i.putExtra("firstname",firstname);
                        i.putExtra("lastname",lastname);
                        i.putExtra("email",email);
                        i.putExtra("contact",contact);
                        i.putExtra("address",txtShippingAddress.getText().toString());
                        i.putExtra("city",txtShippingCity.getText().toString());
                        i.putExtra("pincode",txtShippingPincode.getText().toString());
                        i.putExtra("TotalCartPrice",TotalCartPrice);
                        i.putExtra("ShippingPrice",ShippingPrice);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(AddShippingAddressActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });

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
