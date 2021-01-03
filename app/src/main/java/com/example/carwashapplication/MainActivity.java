package com.example.carwashapplication;

import androidx.annotation.NonNull;
import androidx.annotation.experimental.UseExperimental;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    EditText email;
    EditText password;
    Button reg_btn;
    Button ref_log_btn;
    private FirebaseAuth auth;
    DatabaseReference datRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.R_userName);
        password = findViewById(R.id.R_pass);
        reg_btn = findViewById(R.id.R_reg);
        ref_log_btn = findViewById(R.id.R_login);
        auth = FirebaseAuth.getInstance();


        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

            }
        });
    }


    public void register(){
       String userN = email.getText().toString().trim();
       String pas = password.getText().toString().trim();
        if(email.getText().toString().trim().isEmpty()){
            email.setError("Invalid UserName");
            email.requestFocus();
            return;
        }
        if(password.getText().toString().trim().isEmpty()){
            password.setError("Invalid password");
            password.requestFocus();
            return;
        }
        else{
             auth.createUserWithEmailAndPassword(userN,pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()){
                             Customer customer = new Customer(userN,pas);
                             datRef = FirebaseDatabase.getInstance().getReference().child("Customers");
                             datRef.push().setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                             if(task.isSuccessful()){
                                                 Toast.makeText(MainActivity.this,"Successfull register",Toast.LENGTH_SHORT).show();
                                             }
                                             else{
                                                 Toast.makeText(MainActivity.this,"Invalid database register",Toast.LENGTH_SHORT).show();
                                             }
                                 }
                             });


                         }
                         else{
                             Toast.makeText(MainActivity.this,"Invalid register",Toast.LENGTH_SHORT).show();
                         }
                 }
             });
            }

        }

    }

