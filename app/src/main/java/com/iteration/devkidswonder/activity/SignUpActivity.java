package com.iteration.devkidswonder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.iteration.devkidswonder.R;

public class SignUpActivity extends AppCompatActivity {

    EditText txtname, txtlastname, txtemail, txtpassword, txtmobile_no;
    Button btnAlreadyLogin, btnReg;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        txtname = (EditText) findViewById(R.id.etfnameReg);
        txtlastname = (EditText) findViewById(R.id.etLnameReg);
        txtemail = (EditText) findViewById(R.id.etEmailReg);
        txtpassword = (EditText) findViewById(R.id.etPasswordReg);
        txtmobile_no = (EditText) findViewById(R.id.etPhonenoReg);
        btnReg = (Button) findViewById(R.id.btnRegister);
        btnAlreadyLogin = (Button) findViewById(R.id.btnAlreadyLogin);

        awesomeValidation.addValidation(this, R.id.etfnameReg, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.firstname);
        awesomeValidation.addValidation(this, R.id.etLnameReg, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.lname);
        awesomeValidation.addValidation(this, R.id.etEmailReg, Patterns.EMAIL_ADDRESS, R.string.Email);
        awesomeValidation.addValidation(this, R.id.etPasswordReg, "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})", R.string.Password);
        awesomeValidation.addValidation(this, R.id.etPhonenoReg, "^[2-9]{2}[0-9]{8}$", R.string.mobileNo);

        btnAlreadyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}
