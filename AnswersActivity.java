package com.example.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newapp.Adapters.AnwersAdapter;

public class AnswersActivity extends AppCompatActivity {
    private Toolbar toolbar2;
    private RecyclerView answerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        toolbar2 =findViewById(R.id.aa_toolbar);

        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("ANSWERS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        answerView = findViewById(R.id.aa_recycler_view);

        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        answerView.setLayoutManager(layoutManager);
        AnwersAdapter adapter = new AnwersAdapter(DbQuery.g_quesList);
        answerView.setAdapter(adapter);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            AnswersActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}