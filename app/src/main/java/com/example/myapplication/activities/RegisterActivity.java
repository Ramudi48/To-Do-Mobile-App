package com.example.myapplication.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.utils.SharedPrefManager;

public class RegisterActivity extends AppCompatActivity {
    EditText usernameET, passwordET;
    Button registerBtn;
    SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameET = findViewById(R.id.editTextUsername);
        passwordET = findViewById(R.id.editTextPassword);
        registerBtn = findViewById(R.id.buttonRegister);
        prefManager = new SharedPrefManager(this);

        registerBtn.setOnClickListener(v -> {
            String username = usernameET.getText().toString().trim();
            String password = passwordET.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (prefManager.isUsernameTaken(username)) {
                Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
                return;
            }

            prefManager.saveUser(username, password);
            Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show();
            finish(); // back to Login
        });
    }
}
