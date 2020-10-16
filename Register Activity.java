package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {


    EditText emailEt;
    EditText passowrdEt;
    Button RegisterBtn;
    TextView loginAc;
    ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        emailEt=(EditText)findViewById(R.id.emailEtt);
        passowrdEt=(EditText)findViewById(R.id.passwordEt);
        RegisterBtn=(Button)findViewById(R.id.Register_Btn);
        loginAc=(TextView)findViewById(R.id.go_to_login);

        mProgressDialog=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        mProgressDialog.setMessage("Registering User...");






        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailEt.getText().toString().trim();
                String password=passowrdEt.getText().toString().trim();


                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailEt.setError("Invalid email");
                    emailEt.setFocusable(true);
                }else if(password.length()<6){
                    passowrdEt.setError("Your password is too short");
                    passowrdEt.setFocusable(true);
                }else{
                    registerUser(email,password);
                }

            }
        });


        
    }

    private void registerUser(String email, String password) {
        mProgressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mProgressDialog.dismiss();
                            Intent intent=new Intent(RegisterActivity.this,ProfilActivity.class);
                            startActivity(intent);

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this,"Registered...\n "+user.getEmail(),Toast.LENGTH_SHORT);
                            Intent intent2=new Intent(RegisterActivity.this,ProfilActivity.class);
                            startActivity(intent2);
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressDialog.dismiss();

                Toast.makeText(RegisterActivity.this,""+e.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return super.onSupportNavigateUp();
    }
}
