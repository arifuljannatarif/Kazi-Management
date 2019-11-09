package com.example.kazimanager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.HalfFloat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nullable;

public class LoginNew extends AppCompatActivity {
    String mail,pass;int x=1;
    ImageView top_curve;
    TextInputEditText email,password;
    TextView email_text, password_text, login_title,signupbtn,msgbox;
    ProgressBar progressbar;
    ImageView logo;
    LinearLayout new_user_layout;
    CardView login_card;
    Button loginbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        email = findViewById(R.id.login_email);
        progressbar=findViewById(R.id.progressBar);
        msgbox=findViewById(R.id.msgbox);
        email.clearFocus();
        password = findViewById(R.id.login_password);
        loginbtn=findViewById(R.id.login_btnlogin);
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(LoginNew.this,HomePage.class));
            overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
            finish();
        }
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgbox.setVisibility(View.GONE);
                mail=email.getText().toString().trim();
                pass=password.getText().toString().trim();
                if(mail.isEmpty() || !mail.contains(".com") || !mail.contains("@") ){
                    email.setError("Invalid Email");
                    return;
                }
                if(pass.trim().isEmpty())
                {
                    password.setError("Invalid");
                    return;
                }
                loginbtn.setEnabled(false);
                progressbar.setVisibility(View.VISIBLE);
                final FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(mail,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
                                       startActivity(new Intent(LoginNew.this,HomePage.class));
                                        overridePendingTransition(R.anim.zoom_enter,R.anim.zoom_exit);
                                        finish();
                                    }
                                }else{
                                    loginbtn.setEnabled(true);
                                    progressbar.setVisibility(View.GONE);
                                    msgbox.setVisibility(View.VISIBLE);
                                    msgbox.setText(task.getException().getMessage());
                                }
                            }
                        });

            }

        });

    }



    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    public void finish(){
        super.finish();
        // overridePendingTransition(R.anim.no_anim,R.anim.fast_zoom_out);
    }

    private void hideNavigationBar() {

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE   | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_FULLSCREEN | View
                .SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().getDecorView().setSystemUiVisibility(flags);
            final View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View
                    .OnSystemUiVisibilityChangeListener() {

                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(flags);
                    }
                }
            });
        }
    }



}
