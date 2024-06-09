package com.anshu.askit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.anshu.askit.models.Question;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AnswerActivity extends AppCompatActivity {
Button submit;
EditText answer;
TextView question;
Question currQuest;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    CollectionReference qn = firestore.collection("Question");
    DocumentReference UIDRef = firestore.document("UniqueId/UID");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_answer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String uid = getIntent().getStringExtra("UID");
        question = findViewById(R.id.textView10);

        qn.document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                question.setText(documentSnapshot.getString("question"));
                currQuest = documentSnapshot.toObject(Question.class);
            }
        });

        answer = findViewById(R.id.answer);
        submit = findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currQuest.answer = answer.getText().toString();
                qn.document(uid).set(currQuest);
                Toast.makeText(AnswerActivity.this,"Answer submitted successfully",Toast.LENGTH_LONG).show();
                AnswerActivity.super.onBackPressed();
            }
        });
    }
}