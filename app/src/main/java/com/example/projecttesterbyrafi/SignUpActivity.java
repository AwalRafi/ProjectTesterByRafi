package com.example.projecttesterbyrafi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projecttesterbyrafi.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText emailId, password, namauser;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText);
        namauser = findViewById(R.id.editText3);
        password = findViewById(R.id.editText2);
        btnSignUp = findViewById(R.id.button2);
        btnSignUp.setOnClickListener(this);
        tvSignIn = findViewById(R.id.textView);
        tvSignIn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                btnSignUp();
                break;

            case  R.id.textView :
                startActivity(new Intent(SignUpActivity.this,SignIn.class));
                break;
        }

    }


    private void btnSignUp() {
     final String email = emailId.getText().toString().trim();
     final String pwd = password.getText().toString().trim();
      final String nama = namauser.getText().toString().trim();

        if (email.isEmpty()) {
            emailId.setError("Please enter email id");
            emailId.requestFocus();
            return;
        }

        if (pwd.isEmpty()) {
            password.setError("Please enter Password");
            password.requestFocus();
            return;
        }

        if (nama.isEmpty()) {
            namauser.setError("Please enter Nama");
            namauser.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailId.setError("Email anda tidak ditemukan");
            return;
        }
        mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                User user = new User(nama, email, pwd);
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>(){

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this,"SignUp successful",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(SignUpActivity.this,"SignUp Unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            }
        });
    }

}






