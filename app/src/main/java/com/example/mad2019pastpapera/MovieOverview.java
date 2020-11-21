package com.example.mad2019pastpapera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MovieOverview extends AppCompatActivity {
    private ArrayList commentsArrList;
    private DBHandler dbHandler;
    private TextView movLabel, currentRate;
    private SeekBar rateBar;
    private EditText comment;
    private Button btnSubmit;
    private ListView commentList;
    private ArrayAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_overview);

        String movName = getIntent().getStringExtra("MOVIE_NAME");
        System.out.println("Intent caught " + movName);

        rateBar = findViewById(R.id.ratingBar);
        comment = findViewById(R.id.etComment);
        btnSubmit = findViewById(R.id.btnSubmitComment);
        currentRate = findViewById(R.id.tvCurrentRating);
        commentList = findViewById(R.id.commentListView);
        movLabel = findViewById(R.id.textView2);

        movLabel.setText(movName);

        dbHandler = new DBHandler(this);
        commentsArrList = dbHandler.viewComments(movName);

        int totRate = dbHandler.getTotalRating(movName);
        System.out.println("Total Fetched " + totRate);
        currentRate.setText(String.valueOf(totRate));


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int rateGiven = rateBar.getProgress();
                System.out.println("Given Rate " + rateGiven);

                long status = dbHandler.insertComments(movLabel.getText().toString(), comment.getText().toString(), rateGiven);

                if(status > 0){
                    Toast.makeText(MovieOverview.this, "Comment Added!", Toast.LENGTH_SHORT).show();
                    comment.setText(null);
                    Intent intent = new Intent(MovieOverview.this, MovieList.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MovieOverview.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        commentAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, commentsArrList);
        commentList.setAdapter(commentAdapter);
    }
}
