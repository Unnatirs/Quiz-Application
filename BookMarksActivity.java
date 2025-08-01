package com.example.newapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.newapp.Adapters.AnwersAdapter;
import com.example.newapp.Adapters.BookmarksAdapter;

public class BookMarksActivity extends AppCompatActivity {

    private RecyclerView questionsView;
    private Dialog progressDialog;
    private TextView dialogText;
    private Toolbar toolbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_marks);
        toolbar2 =findViewById(R.id.ba_toolbar);

        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("SAVED_QUESTIONS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new Dialog(BookMarksActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout2);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText =progressDialog.findViewById(R.id.dialog_text);
        //dialogText.setText("Loading....");
        progressDialog.show();

        questionsView = findViewById(R.id.ba_recycler_view);

        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        questionsView.setLayoutManager(layoutManager);


        DbQuery.loadBookmarks(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                BookmarksAdapter adapter = new BookmarksAdapter(DbQuery.g_bookmarksList);
                questionsView.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onFailure() {
               progressDialog.dismiss();
            }
        });

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            BookMarksActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

}