package com.example.cadastroendereco.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cadastroendereco.R;
import com.example.cadastroendereco.DBHelper.DBHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password;
    Button btnRegister;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DBHelper(this);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean userExists = dbHelper.checkUsernameExists(user);
                    if (userExists) {
                        Toast.makeText(RegisterActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                    } else {
                        dbHelper.insertUser(user, pass);
                        Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }
}
