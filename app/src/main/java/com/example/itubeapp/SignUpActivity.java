package com.example.itubeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.itubeapp.sqlitehelper.DatabaseHelper;
import com.example.itubeapp.sqlitehelper.User;

public class SignUpActivity extends AppCompatActivity {
    EditText name, username, password, confirmPassword;
    Button signUp;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // findViewById
        name = findViewById(R.id.editTextText2);
        username = findViewById(R.id.editTextText3);
        password = findViewById(R.id.editTextTextPassword2);
        confirmPassword = findViewById(R.id.editTextTextPassword3);
        signUp = findViewById(R.id.button3);

        // DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Submit to database
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Makes sure EditText is not empty
                if (name.getText().toString().trim().length() != 0 ||
                        username.getText().toString().trim().length() != 0 ||
                        password.getText().toString().trim().length() != 0 ||
                        confirmPassword.getText().toString().trim().length() != 0)
                {
                    // Password has to be equal to ConfirmPassword
                    if (password.getText().toString().equals(confirmPassword.getText().toString()))
                    {
                        long result = databaseHelper.insertUser(new User(
                                name.getText().toString(),
                                username.getText().toString(),
                                password.getText().toString()
                        ));
                        if(result > 0)
                        {
                            // SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
                            SharedPreferences.Editor myEditor = sharedPreferences.edit();
                            myEditor.putString("username", username.getText().toString());
                            myEditor.apply();
                            Toast.makeText(SignUpActivity.this, "User registered successfully!", Toast.LENGTH_LONG).show();
                            Intent intent3 = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent3);
                        }
                        else Toast.makeText(SignUpActivity.this, "Unsuccessful!", Toast.LENGTH_LONG).show();
                    }
                    else Toast.makeText(SignUpActivity.this, "Password and Confirm Password is different!", Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(SignUpActivity.this, "Please enter all fields!", Toast.LENGTH_LONG).show();
            }
        });
    }
}