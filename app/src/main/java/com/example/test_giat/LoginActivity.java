package com.example.test_giat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    EditText email;
    String u_email;

    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //check if user is null
        if (firebaseUser != null){
            Intent intent = new Intent(LoginActivity.this, LoginActivity1.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=(EditText)findViewById(R.id.username_email);



    }
    public void con(View view) {
        Intent i = new Intent(LoginActivity.this, LoginActivity1.class);
        u_email=email.getText().toString();
        i.putExtra("email",u_email);
        startActivity(i);

    }

    public void register(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }
}