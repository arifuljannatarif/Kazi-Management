package com.example.kazimanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ebanx.swipebtn.OnActiveListener;
import com.ebanx.swipebtn.SwipeButton;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {
    Button addnew,view,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getSupportActionBar().setBackgroundDrawable(null);
        final SwipeButton enableButton = (SwipeButton) findViewById(R.id.swipe_btn1);
        enableButton.setOnActiveListener(new OnActiveListener() {
            @Override
            public void onActive() {
                startActivity(new Intent(HomePage.this,Add_new_details.class));
                overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
            }
        });
        final SwipeButton enableButton1 = (SwipeButton) findViewById(R.id.swipe_btn2);
        enableButton1.setOnActiveListener(new OnActiveListener() {
            @Override
            public void onActive() {
                startActivity(new Intent(HomePage.this,MainActivity.class));
                overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
            }
        });
        final SwipeButton enableButton2 = (SwipeButton) findViewById(R.id.swipe_btn3);
        enableButton2.setOnActiveListener(new OnActiveListener() {
            @Override
            public void onActive() {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomePage.this,LoginNew.class));
                overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
                finish();
            }
        });
        // TextView tv=findViewById(R.id.topbox);
        // tv.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

       /*
        addnew=findViewById(R.id.addnew);
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,Add_new_details.class));
            }
        });
        view=findViewById(R.id.viewall);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,MainActivity.class));
            }
        });
        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomePage.this,LoginNew.class));
                finish();
            }
        });*/
    }
}
