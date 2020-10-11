package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView username, password;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //simpan value dari kolom txtUsername dan txtPassword
        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), username.getText()+" dan "+password.getText(), Toast.LENGTH_LONG).show();
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                    successLogin(view);
                }
                else{
                    failLogin();
                }

            }
        });

    }
    private void successLogin(View view){
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    private void failLogin(){
        Toast.makeText(getApplicationContext(), "username atau password salah", Toast.LENGTH_LONG).show();
    }
}