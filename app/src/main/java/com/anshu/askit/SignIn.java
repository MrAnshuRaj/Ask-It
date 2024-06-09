package com.anshu.askit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignIn extends AppCompatActivity {
    Button signIn;
    TextView signUp;
    SharedPreferences sharedPref ;

    @Override
    protected void onStart() {
        super.onStart();
        sharedPref= getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if(sharedPref.getBoolean("LoggedIn",false)) {
            startActivity(new Intent(SignIn.this, DashBoard.class));
            finish();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        signIn = findViewById(R.id.buttonRegister);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this,DashBoard.class));
            }
        });
        signUp = findViewById(R.id.textView3);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });
    }

}