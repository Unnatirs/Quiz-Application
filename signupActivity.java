package com.example.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signupActivity extends AppCompatActivity {
    private EditText name,email,pass,confirmPass;
    private Button signupB;
    private ImageView backB;
    private FirebaseAuth mAuth;
    private String emailStr,passStr,confirmPassStr,nameStr;
    private Dialog progressDialog;
    private TextView dialogText;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name=findViewById(R.id.username);
        email=findViewById(R.id.emailID);
        pass = findViewById(R.id.password);
        confirmPass=findViewById(R.id.confirm_pass);
        signupB=findViewById(R.id.signupB);
        backB =findViewById(R.id.backB);


        progressDialog =new Dialog(signupActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText =findViewById(R.id.dialog_text);
        //dialogText.setText("Registering User...");

        mAuth =FirebaseAuth.getInstance();
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        signupB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    signupNewUser();
                }
            }
        });

    }
    private boolean validate(){
        nameStr= name.getText().toString().trim();
        passStr =pass.getText().toString().trim();
        emailStr =email.getText().toString().trim();
        confirmPassStr =confirmPass.getText().toString().trim();
        if(nameStr.isEmpty())
        {
            name.setError("Enter your Name");
            return false;
        }
        if (emailStr.isEmpty())
        {
            email.setError("Enter the Email");
            return false;
        }
        if (passStr.isEmpty())
        {
            pass.setError("Enter Password");
            return false;
        }
        if (confirmPassStr.isEmpty())
        {
            confirmPass.setError("Enter Password");
            return false;
        }
        if(passStr.compareTo(confirmPassStr) != 0)
        {
            Toast.makeText(signupActivity.this,"Password and ConfirmPassword should be same!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void signupNewUser(){
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(emailStr,passStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(signupActivity.this,"Sign up Sucessfull",Toast.LENGTH_SHORT).show();
                            DbQuery.createUserData(emailStr,nameStr,new MyCompleteListener(){

                                @Override
                                public void onSuccess() {
                                    DbQuery.loadData(new MyCompleteListener() {
                                        @Override
                                        public void onSuccess() {
                                            progressDialog.dismiss();

                                            Intent intent= new Intent(signupActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            signupActivity.this.finish();
                                        }

                                        @Override
                                        public void onFailure() {
                                            Toast.makeText(signupActivity.this,"Something went wrong ! Please try again later!",Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    });
                                }
                                @Override
                                public void onFailure() {
                                    Toast.makeText(signupActivity.this,"Something went wrong ! Please try again later!",Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });

                        }else {
                            Toast.makeText(signupActivity.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}