package com.joni.myapplication;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tashila.pleasewait.PleaseWaitDialog;


public class LoginActivity extends AppCompatActivity {
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    Button mButtonLogin;

    Toolbar mToolbar;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    AlertDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mToolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputPassword = findViewById(R.id.textInputPassword);
        mButtonLogin = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();
            }
        });


    }

    private void login(){
        String email = mTextInputEmail.getText().toString();
        String password = mTextInputPassword.getText().toString();
        PleaseWaitDialog progressDialog = new PleaseWaitDialog(this);
        progressDialog.setMessage("Espere un momento ");

        if (!email.isEmpty() && !password.isEmpty()){
            if (password.length() >= 6){
                progressDialog.show();

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           Toast.makeText(LoginActivity.this,"El login se realizo exitosamente",Toast.LENGTH_SHORT).show();

                       }
                       else {
                           Toast.makeText(LoginActivity.this,"La contraseña o el passwar son incorrectos",Toast.LENGTH_SHORT).show();
                       }
                        progressDialog.dismiss();
                    }
                });
            }
            else {
                Toast.makeText( this, "La contraseña debe tener mas de 6 caracteres",Toast.LENGTH_SHORT).show();

            }

        }
        else{
            Toast.makeText( this, "La contraseña y el email son obligatorios",Toast.LENGTH_SHORT).show();
        }

    }
}