package com.anshu.askit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anshu.askit.adapters.QuestionsAdapter;
import com.anshu.askit.models.Question;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DashBoard extends AppCompatActivity {
ArrayList<Question> questionArrayList;
FirebaseFirestore db = FirebaseFirestore.getInstance();
CollectionReference qn = db.collection("Question");
DocumentReference UIDRef = db.document("UniqueId/UID");
QuestionsAdapter questionsAdapter;
FloatingActionButton fab;
ImageButton search;
RecyclerView recyclerView;
EditText searchField;
    private final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
            "a", "an", "and", "are", "as", "at", "be", "but", "by", "for", "if",
            "in", "into", "is", "it", "no", "not", "of", "on", "or", "such",
            "that", "the", "their", "then", "there", "these", "they", "this",
            "to", "was", "will", "with","what","how","when","which","whom"
    ));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dash_board);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        questionArrayList = new ArrayList<>();

        questionArrayList.add(new Question("What is Java?","A programming language","Coding,Java",10,5,100));
        questionArrayList.add(new Question("What is DBMS?","A Database Management System","Coding,Database",5,2,101));
         recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        questionsAdapter = new QuestionsAdapter(this,questionArrayList);
        recyclerView.setAdapter(questionsAdapter);
        searchField = findViewById(R.id.editTextText);
        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,QuestionActivity.class));
            }
        });
        UIDRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                long uid = documentSnapshot.getLong("ID");
                Fetch(uid);
            }
        });
        search = findViewById(R.id.imageButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Question> matchedQns =
                        findMatches(searchField.getText().toString(),questionArrayList);
                        Intent intent = new Intent(DashBoard.this,SearchQuestionActivity.class);
                        Bundle args = new Bundle();
                        args.putSerializable("ARRAYLIST",(Serializable)matchedQns);
                        intent.putExtra("BUNDLE",args);
                        startActivity(intent);
            }
        });
    }
    public void Fetch(long uid)
    {
        if(uid==100)
        {
            return;
        }
        db.collection("Question").document(""+uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Question que = documentSnapshot.toObject(Question.class);
                questionArrayList.add(que);
                questionsAdapter.notifyItemInserted(questionArrayList.size()-1);
                Fetch(uid-1);
            }
        });
    }
    public Set<String> extractKeywords(String question) {
        Set<String> keywords = new HashSet<>();
        if(question!=null) {
            String[] words = question.toLowerCase().split("\\W+"); // Split by non-word characters

            for (String word : words) {
                if (!STOP_WORDS.contains(word) && !word.isEmpty()) {
                    keywords.add(word);
                }
            }
        }
        return keywords;
    }
    public Set<String> extractTags(String tags) {
        Set<String> tagSet = new HashSet<>();
        String[] tagArray = tags.toLowerCase().split(","); // Split by comma

        for (String tag : tagArray) {
            tag = tag.trim(); // Remove leading and trailing spaces
            if (!STOP_WORDS.contains(tag) && !tag.isEmpty()) {
                tagSet.add(tag);
            }
        }

        return tagSet;
    }
    public ArrayList<Question> findMatches(String question, ArrayList<Question> questions) {
        Set<String> keywords = extractKeywords(question);
        ArrayList<Question> matches = new ArrayList<>();

        for (Question q : questions) {
            Set<String> qKeywords = extractKeywords(q.question);
            Set<String> tagKeywords = extractTags(q.tags);
            // Check for intersection of keyword sets
            boolean foundMatch = false;

            for (String keyword : keywords) {
                if (qKeywords.contains(keyword) || tagKeywords.contains(keyword)) {
                    foundMatch = true;
                    break;
                }
            }

            if (foundMatch) {
                matches.add(q);
            }
        }
        return matches;
    }
}