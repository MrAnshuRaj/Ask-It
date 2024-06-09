package com.anshu.askit;

import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anshu.askit.adapters.QuestionsAdapter;
import com.anshu.askit.models.Question;

import java.util.ArrayList;

public class SearchQuestionActivity extends AppCompatActivity {
RecyclerView recyclerView;
QuestionsAdapter questionsAdapter;
ArrayList<Question> questionArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_question);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle args = getIntent().getBundleExtra("BUNDLE");
        questionArrayList = (ArrayList<Question>) args.getSerializable("ARRAYLIST");
        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        questionsAdapter = new QuestionsAdapter(this,questionArrayList);
        recyclerView.setAdapter(questionsAdapter);
    }
}