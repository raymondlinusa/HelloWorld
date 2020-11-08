package com.example.helloworld;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SessionManager {
    private static final String KEY_TOKEN = "tokenLogin";
    private static final String KEY_LOGIN = "isLogin";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    String user;

    public SessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences("LoginSessionHelloWorld", MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String token){
        editor.putString(KEY_TOKEN, token);
        editor.putBoolean(KEY_LOGIN, true);
        editor.commit();
    }

    public boolean isLogin(){
        return pref.getBoolean(KEY_LOGIN, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }

    public void setIduser(String id_user){
        editor.putString("user_id", id_user);
        editor.commit();
    }
    public String getIdUser(){
        return pref.getString("user_id", "");
    }

//    public void setPass(String pass){
//        editor.putString("user_pass", pass);
//        editor.commit();
//    }
//    public String getPass(){
//        return pref.getString("user_pass", "");
//    }

}
