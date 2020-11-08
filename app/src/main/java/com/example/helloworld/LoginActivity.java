package com.example.helloworld;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private TextView username, password, register;
    private Button btnLogin;
    SessionManager sesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //simpan value dari kolom txtUsername dan txtPassword
        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);
        register = findViewById(R.id.txtRegister);
        btnLogin = findViewById(R.id.btnLogin);
        sesi = new SessionManager(LoginActivity.this.getApplicationContext());

        DBHandler dbHandler = new DBHandler(getBaseContext());
        Toast.makeText(getApplication(), dbHandler.getDatabaseName(), Toast.LENGTH_SHORT).show();

        if(sesi.isLogin()){
            successLogin();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user, pass;
                user = username.getText().toString();
                pass = password.getText().toString();
                Log.i(TAG, "onClick: menyimpan input ke dbmodel");
                DBModel dbModel = new DBModel(user ,pass);
                Log.i(TAG, "onClick: membuat dbhandler");
                DBHandler dbHandler = new DBHandler(getBaseContext());
                Log.i(TAG, "onClick: menghitung isi db");
                int count = dbHandler.getDBModelCount();
                dbHandler.close();
                Log.i(TAG, "onClick: membuat tampungan isi db");
                List<DBModel> accountList = new ArrayList<DBModel>();
                Log.i(TAG, "onClick: menyimpan isi db ke tampungan");
                accountList = dbHandler.getAllRecord();
                Log.i(TAG, "onClick: mengecek input dengan db");

                if (count > 0){
                    for (int i=0; i<count; i++){
                        DBModel temp = accountList.get(i);
                        if(dbModel.getEmail().equals(temp.getEmail()) && dbModel.getPass().equals(temp.getPass())){
                            sesi.createLoginSession(user);
                            sesi.setIduser(user);

                            successLogin();

                        }
                        else{
                            failLogin();
                        }
                    }
                }
                else {
                    failLogin();
                }

            }
        });

    }
    private void successLogin(){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void failLogin(){
        Toast.makeText(getApplicationContext(), "username atau password salah", Toast.LENGTH_LONG).show();
    }
}