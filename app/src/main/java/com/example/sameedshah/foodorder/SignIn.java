package com.example.sameedshah.foodorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sameedshah.foodorder.Common.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.sameedshah.foodorderserver.Model.User;

public class SignIn extends AppCompatActivity {

    EditText edtPhone,edtPasswrod;
    Button btnSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPhone = findViewById(R.id.edtPhone);
        edtPasswrod = findViewById(R.id.edtPassword);
        btnSignin = findViewById(R.id.btnLogin);


        //firebase

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference mReference = database.getReference("User");

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mProgress = new ProgressDialog(SignIn.this);
                mProgress.setMessage("Please wait....");
                mProgress.setCancelable(false);
                mProgress.show();

                mReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        mProgress.dismiss();

                        //to check if user exists
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()){
                            //User info

                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            user.setPhone(edtPhone.getText().toString());

                            if (user.getPassword().equals(edtPasswrod.getText().toString())) {
                                Intent i = new Intent(SignIn.this,Home.class);
                                Common.currentUser = user;
                                startActivity(i);
                                finish();

                            } else {
                                Toast.makeText(SignIn.this, "Sign in failed !!", Toast.LENGTH_SHORT).show();
                            }
                        }else
                        {
                            Toast.makeText(SignIn.this, "User doesn't exists ", Toast.LENGTH_SHORT).show();
                        }
                    }

                      @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
