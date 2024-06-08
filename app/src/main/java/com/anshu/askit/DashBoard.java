package com.anshu.askit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

import java.util.ArrayList;

public class DashBoard extends AppCompatActivity {
ArrayList<Question> questionArrayList;
FirebaseFirestore db = FirebaseFirestore.getInstance();
CollectionReference qn = db.collection("Question");
DocumentReference UIDRef = db.document("UniqueId/UID");
    QuestionsAdapter questionsAdapter;
    Object obj;
FloatingActionButton fab;
    RecyclerView recyclerView;
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
        obj = new Object();
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

    @Override
    protected void onResume() {
//        if(questionsAdapter!=null && obj!=null)  {
//            UIDRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                @Override
//                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                    long uid = documentSnapshot.getLong("ID");
//                    db.collection("Question").document(""+uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            Question que = documentSnapshot.toObject(Question.class);
//                            questionArrayList.add(que);
//                            questionsAdapter.notifyItemInserted(questionArrayList.size()-1);
//
//                        }
//                    });
//                }
//            });
//        }
        super.onResume();

    }
}