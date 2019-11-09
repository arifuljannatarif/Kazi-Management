package com.example.kazimanager;

import com.google.firebase.auth.FirebaseAuth;

public class UserUtils {
    static String email="mjarif97@gmail.com";
    public static String getuseremail(){
        return FirebaseAuth.getInstance().getCurrentUser().getEmail()==null?email:FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }
}
