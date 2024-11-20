package com.example.test3;

import com.Database.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.example.test3.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static Database warrior;
    public static Database mage;
    public static Database cleric;
    public static Database ranger;
    public static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Find views by ID
        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        //Database Creation
        File path = getApplicationContext().getFilesDir();


        try {
            warrior = new Database("warrior.txt", path);
            mage = new Database("mage.txt", path);
            cleric = new Database("cleric.txt", path);
            ranger = new Database("ranger.txt", path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Handle login button click
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Basic validation
                if (username.isEmpty()) {
                    editTextUsername.setError("Please enter username");
                } else if (password.isEmpty()) {
                    editTextPassword.setError("Please enter password");
                } else {

                    Snackbar.make(view, "Logging in...", Snackbar.LENGTH_LONG).show();

                    // Login system, Checks if user exists, if they do not it creates a new one
                    // Compares existing user to password and log's them in if it's valid
                    if (warrior.GetIndex(username) > -1) {
                        if (warrior.Query(username, 1).equals(password)){

                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            // Navigate to next screen or handle successful login
                            Intent intent = new Intent(MainActivity.this, MainMenu.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                            // If the user exists and the password is wrong
                        }
                    } else {
                        try {
                            warrior.NewRecord(username, password);
                            mage.NewRecord(username, password);
                            cleric.NewRecord(username, password);
                            ranger.NewRecord(username, password);
                            Toast.makeText(MainActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            // Navigate to next screen or handle successful login
                            Intent intent = new Intent(MainActivity.this, MainMenu.class);
                            startActivity(intent);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }
            }
        });
    }
}
