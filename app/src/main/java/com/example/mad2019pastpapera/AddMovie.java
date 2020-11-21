package com.example.mad2019pastpapera;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMovie extends AppCompatActivity {
    private EditText movieName, year;
    private Button Add;
    private DBHandler dbHandler = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        movieName = findViewById(R.id.etMovieName);
        year = findViewById(R.id.etDate);
        Add = findViewById(R.id.btnAdd);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long status;
                status = dbHandler.addMovies(movieName.getText().toString(), Integer.parseInt(year.getText().toString()));

                if(status>0){
                    Toast.makeText(AddMovie.this, "Movie Added!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddMovie.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}