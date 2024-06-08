package com.anshu.askit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.anshu.askit.models.Question;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class QuestionActivity extends AppCompatActivity {
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    CollectionReference qn = firestore.collection("Question");
    DocumentReference UIDRef = firestore.document("UniqueId/UID");
EditText question, tags;
Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        question = findViewById(R.id.question);
        tags = findViewById(R.id.tags);
        submit = findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitQuestion();
                QuestionActivity.super.onBackPressed();
            }
        });

    }
    public void submitQuestion()
    {
        UIDRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                long uid =  documentSnapshot.getLong("ID");
                uid++;
                Question question1 = new Question(question.getText().toString(),
                        "",tags.getText().toString(),0,0,uid);
                qn.document(""+uid).set(question1);
                HashMap<String,Long> UIDHash = new HashMap<String,Long>();
                UIDHash.put("ID",uid);
                UIDRef.set(UIDHash);
                Toast.makeText(QuestionActivity.this,"Question submitted successfully",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(QuestionActivity.this,"Cannot connect to server! Try again later",Toast.LENGTH_LONG).show();
            }
        });
    }
}