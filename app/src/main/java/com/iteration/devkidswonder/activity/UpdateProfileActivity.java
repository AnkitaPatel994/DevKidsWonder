package com.iteration.devkidswonder.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iteration.devkidswonder.R;

public class UpdateProfileActivity extends AppCompatActivity {

    Button btnEditProfile;
    EditText update_fname,update_lastname,update_number,update_email,update_address,update_city,update_pincode;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        update_fname = (EditText)findViewById(R.id.update_name);
        update_lastname = (EditText)findViewById(R.id.update_lastname);
        update_number = (EditText)findViewById(R.id.update_number);
        update_email = (EditText)findViewById(R.id.update_email);
        update_address = (EditText)findViewById(R.id.update_address);
        update_city = (EditText)findViewById(R.id.update_city);
        update_pincode = (EditText)findViewById(R.id.update_pincode);
        btnEditProfile = (Button) findViewById(R.id.btnEditProfile);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
