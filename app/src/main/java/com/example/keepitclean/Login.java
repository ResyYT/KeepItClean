package com.example.keepitclean;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // Utilisateur déjà connecté, redirige vers MainActivity
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        } else {
            // Utilisateur non connecté, affiche l'écran de login
            setContentView(R.layout.login);

            EditText emailEditText = findViewById(R.id.etEmailConnec);
            EditText passwordEditText = findViewById(R.id.etPasswordConnec);
            Button loginButton = findViewById(R.id.btnConnecter);

            loginButton.setOnClickListener(v -> {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                findViewById(R.id.txtErreur).setVisibility(View.VISIBLE);
                            }
                        });
            });
        }
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }
    }
}
