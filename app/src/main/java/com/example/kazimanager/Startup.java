package com.example.kazimanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.FirebaseApp;

public class Startup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        FirebaseApp.initializeApp(this);
        try{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(Startup.this,LoginNew.class));
                            overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
                            finish();
                        }
                    });
                }
            }, 2500);
        }catch (Exception e){
            startActivity(new Intent(Startup.this,LoginNew.class));
            finish();
        }
    }
}
