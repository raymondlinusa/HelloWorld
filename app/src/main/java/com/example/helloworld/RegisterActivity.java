package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private TextView email, password;
    private Button btnRegister;
    List<DBModel> accountList = new ArrayList<DBModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: menyimpan input ke dbmodel");
                DBModel dbModel = new DBModel(email.getText().toString() ,password.getText().toString());
                Log.i(TAG, "onClick: membuat dbhandler");
                DBHandler dbHandler = new DBHandler(getBaseContext());
                Log.i(TAG, "onClick: menyimpan isi db");
                int count = dbHandler.getDBModelCount();
                dbHandler.close();
                Log.i(TAG, "onClick: menyimpan isi db ke temp");
                accountList = dbHandler.getAllRecord();
                Log.i(TAG, "onClick: mengecek");
                if (count > 0){
                    for (int i=0; i<count; i++){
                        DBModel temp = accountList.get(i);
                        Log.i(TAG, "onClick: "+temp.getPass());
                        if(dbModel.getEmail().equals(temp.getEmail())){
                            Toast.makeText(getApplication(), "Email sudah terpakai, coba email lain", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    dbHandler.addRecord(dbModel);
                    Toast.makeText(getApplication(), "Akun sukses dibuat, mohon login", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    dbHandler.addRecord(dbModel);
                    Toast.makeText(getApplication(), "Akun sukses dibuat, mohon login", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }
}