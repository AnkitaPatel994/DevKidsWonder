package com.iteration.devkidswonder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.iteration.devkidswonder.R;

public class SignInActivity extends AppCompatActivity {

    EditText uname,password;
    Button forgotpass,btnLogin,signUp;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        uname=(EditText)findViewById(R.id.etUserNameLogin);
        password=(EditText)findViewById(R.id.etPasswordLogin);
        forgotpass=(Button)findViewById(R.id.btnForgotPassLogin);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        signUp=(Button)findViewById(R.id.btnSignUp);

        awesomeValidation.addValidation(this, R.id.etUserNameLogin, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.uname);
        awesomeValidation.addValidation(this, R.id.etPasswordLogin, "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})", R.string.Psw);

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignInActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uname.getText().toString().equals("") && password.getText().toString().equals(""))
                {
                    Toast.makeText(SignInActivity.this,"Enter valid Username.",Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().toString().equals(""))
                {
                    Toast.makeText(SignInActivity.this,"Enter valid Password.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
