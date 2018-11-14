package com.example.sameedshah.foodorder;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.sameedshah.foodorderserver.Model.User;

public class SignUp extends AppCompatActivity {


    private EditText edtPhone,edtPassword,edtName;
    private Button btnSignUp;
    private FirebaseDatabase database;
    private DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = FirebaseDatabase.getInstance();
        mReference = database.getReference("User");

        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnRegister);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mProgress = new ProgressDialog(SignUp.this);
                mProgress.setMessage("Please wait....");
                mProgress.setCancelable(false);
                mProgress.show();

                mReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //check if aready exists

                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()){

                            mProgress.dismiss();
                            Toast.makeText(SignUp.this, " phone Number already exists!!", Toast.LENGTH_SHORT).show();
                        }else
                        {

                            User user = new User(edtName.getText().toString(),edtPassword.getText().toString());
                            mReference.child(edtPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Sign up successfully!!", Toast.LENGTH_SHORT).show();
                            finish();

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }
}
