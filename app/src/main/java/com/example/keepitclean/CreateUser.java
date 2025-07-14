package com.example.keepitclean;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateUser extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        EditText emailEditText = findViewById(R.id.etEmailCreate);
        EditText passwordEditText = findViewById(R.id.etPasswordCreate);
        TextView txtErreur = findViewById(R.id.txtErreurCreate);
        Button btnInscrire = findViewById(R.id.btnCreate);

        btnInscrire.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            if (!email.isEmpty() && !password.isEmpty()) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                txtErreur.setVisibility(View.VISIBLE);
                            }
                        });
            } else {
                txtErreur.setText("Email et mot de passe requis");
                txtErreur.setVisibility(View.VISIBLE);
            }
        });
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            startActivity(new Intent(CreateUser.this, MainActivity.class));
            finish();
        }
    }
}
