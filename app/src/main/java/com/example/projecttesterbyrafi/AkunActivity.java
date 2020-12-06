package com.example.projecttesterbyrafi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projecttesterbyrafi.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AkunActivity extends AppCompatActivity {


        Button btnLogout, BtnHome , BtnAkun;
        private FirebaseUser user ;
        private DatabaseReference reference;
        private String userID;
        FirebaseAuth mFirebaseAuth;
        private FirebaseAuth.AuthStateListener mAuthStateListener;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_akun);
            btnLogout = findViewById(R.id.logout);
            BtnHome = findViewById(R.id.BtnHome);
            BtnAkun = findViewById(R.id.BtnAkun);
            BtnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intToakun = new Intent(AkunActivity.this, HomeActivity.class);
                    startActivity(intToakun);
                }
            });
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intToMain = new Intent(AkunActivity.this, SignIn.class);
                    startActivity(intToMain);
                }
            });
            user = FirebaseAuth.getInstance().getCurrentUser();
            reference = FirebaseDatabase.getInstance().getReference("Users");
            userID = user.getUid();

            final TextView NamaTextView = (TextView)findViewById(R.id.tvNama);
            final TextView EmailTextView = (TextView)findViewById(R.id.tvEmail);

            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile = snapshot.getValue(User.class);

                    if (userProfile != null){
                        String Nama = userProfile.namauser;
                        String Email = userProfile.emailId;

                        NamaTextView.setText(Nama);
                        EmailTextView.setText(Email);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(AkunActivity.this, "Sesuatu yang salah Terjadi", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
