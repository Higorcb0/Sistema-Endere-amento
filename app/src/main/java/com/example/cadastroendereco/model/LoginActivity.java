package com.example.cadastroendereco.model;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cadastroendereco.MainActivity;
import com.example.cadastroendereco.R;
import com.example.cadastroendereco.DBHelper.DBHelper;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button btnLogin;
    TextView registerLink;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DBHelper(this);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btn_login);
        registerLink = findViewById(R.id.register_link);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkUserPass = dbHelper.checkUsernamePassword(user, pass);
                    if (checkUserPass) {
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
