package com.example.newapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class splashActivity extends AppCompatActivity {
    private TextView  appName;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appName = findViewById(R.id.app_name);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.myanim);
        appName.setAnimation(anim);
        mAuth=FirebaseAuth.getInstance();
       DbQuery.g_firestore = FirebaseFirestore.getInstance();

        new Thread(){
            @Override
           public void run()
           {
               try {
                   sleep(3000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               if (mAuth.getCurrentUser()!=null) {
                   DbQuery.loadData(new MyCompleteListener() {
                       @Override
                       public void onSuccess() {
                           Intent intent=new Intent(splashActivity.this,MainActivity.class);
                           startActivity(intent);
                           splashActivity.this.finish();
                       }
                       @Override
                       public void onFailure() {
                           Toast.makeText(splashActivity.this,"Something went wrong ! Please try again later!",Toast.LENGTH_SHORT).show();
                       }
                   });

               }else {
                   Intent intent=new Intent(splashActivity.this,LoginActivity.class);
                   startActivity(intent);
                   splashActivity.this.finish();
               }

           }
        }.start();
    }
}