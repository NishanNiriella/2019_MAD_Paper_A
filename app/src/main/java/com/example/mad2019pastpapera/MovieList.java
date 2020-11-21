package com.example.mad2019pastpapera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MovieList extends AppCompatActivity {
    private ListView movieList;
    private ArrayList dataList;
    private ArrayAdapter movieAdapter;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        movieList = findViewById(R.id.listViewMovies);

        dbHandler = new DBHandler(this);
        dataList = dbHandler.viewMovies();

        movieAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dataList);
        movieList.setAdapter(movieAdapter);

        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = movieList.getItemAtPosition(position).toString();
//                System.out.println(selected);
                Intent intent = new Intent(MovieList.this, MovieOverview.class);
                intent.putExtra("MOVIE_NAME", selected);
                startActivity(intent);
            }
        });

    }
}