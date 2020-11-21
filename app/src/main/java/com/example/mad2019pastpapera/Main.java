package com.example.mad2019pastpapera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends AppCompatActivity {
    private EditText username, password;
    private Button login, register;
    private DBHandler dbHandler = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.etUserNameHome);
        password = findViewById(R.id.etPasswordHome);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.btnRegister);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = dbHandler.loginUser(username.getText().toString(), password.getText().toString());

                if(type==1){
                    Intent intent = new Intent(Main.this, AddMovie.class);
                    startActivity(intent);
                } else if(type==-1) {
                    Intent intent = new Intent(Main.this, MovieList.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Main.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long status = dbHandler.registerUser(username.getText().toString(), password.getText().toString());

                if(status > 0){
                    Toast.makeText(Main.this, "User Registered", Toast.LENGTH_SHORT).show();
                    username.setText(null);
                    password.setText(null);
                } else {
                    Toast.makeText(Main.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}