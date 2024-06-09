package com.anshu.askit;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.anshu.askit.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class SignIn extends AppCompatActivity {
    Button signIn;
    TextView signUp;
    SharedPreferences sharedPref ;
    EditText editTextUsername, editTextPassword;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        signIn = findViewById(R.id.buttonRegister);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
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
    private void loginUser() {
        final String usernameOrEmail = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(usernameOrEmail)) {
            editTextUsername.setError("Username or Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password is required");
            return;
        }

        // Check if the input is an email or a username
        if (!usernameOrEmail.contains("@")) {
            // Assume it's an email and query Firestore by email
            db.collection("Users").document(usernameOrEmail).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful() && task.getResult().exists()) {
                                DocumentSnapshot document = task.getResult();
                                User user = document.toObject(User.class);
                                if (user != null && user.password.equals(password)) {
                                    Toast.makeText(SignIn.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    SharedPreferences sharedPref = SignIn.this.getSharedPreferences(
                                            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("username", user.username);
                                    editor.putString("email", user.email);
                                    editor.putBoolean("LoggedIn", true);
                                    editor.apply();
                                    finish();
                                    startActivity(new Intent(SignIn.this, DashBoard.class));
                                } else {
                                    Toast.makeText(SignIn.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SignIn.this, "User does not exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            // Assume it's a username and query Firestore by username
            db.collection("Users").whereEqualTo("email", usernameOrEmail).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                DocumentSnapshot document = task.getResult().getDocuments().get(0);
                                User user = document.toObject(User.class);
                                if (user != null && user.password.equals(password)) {
                                    Toast.makeText(SignIn.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    SharedPreferences sharedPref = SignIn.this.getSharedPreferences(
                                            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("username", user.username);
                                    editor.putString("email", user.email);
                                    editor.putBoolean("LoggedIn", true);
                                    editor.apply();
                                    finish();
                                    startActivity(new Intent(SignIn.this, DashBoard.class));
                                } else {
                                    Toast.makeText(SignIn.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(SignIn.this, "User does not exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        }
    }
