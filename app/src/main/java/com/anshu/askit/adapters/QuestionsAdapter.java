package com.anshu.askit.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anshu.askit.R;
import com.anshu.askit.models.Question;

import java.util.ArrayList;
import java.util.Arrays;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.MyViewHolder> {
    Context context;
    ArrayList<Question> arrayList;
    String tags;
    public QuestionsAdapter(Context context, ArrayList<Question> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public QuestionsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.question_card,parent,false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull QuestionsAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.question.setText((position+1)+". "+arrayList.get(position).question);
        holder.answer.setText(arrayList.get(position).answer);
        holder.tags.setText(arrayList.get(position).tags);
        holder.downVotes.setText(""+arrayList.get(position).downVotes);
        holder.upVotes.setText(""+arrayList.get(position).upVotes);
        holder.upVoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!arrayList.get(position).upClick)
                {
                    arrayList.get(position).upVotes++;
                    holder.upVotes.setText(""+arrayList.get(position).upVotes);
                    arrayList.get(position).upClick=true;
                }
            }
        });
        holder.downVoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!arrayList.get(position).downClick) {
                    arrayList.get(position).downVotes++;
                    holder.downVotes.setText(""+arrayList.get(position).downVotes);
                    arrayList.get(position).downClick = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView question,answer,tags,upVotes,downVotes;
        ImageView upVoteBtn,downVoteBtn;
        public MyViewHolder(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.textView4);
            answer = itemView.findViewById(R.id.textView5);
            tags = itemView.findViewById(R.id.textView6);
            upVotes = itemView.findViewById(R.id.textView7);
            downVotes = itemView.findViewById(R.id.textView8);
            upVoteBtn = itemView.findViewById(R.id.imageView2);
            downVoteBtn = itemView.findViewById(R.id.imageView3);
        }
    }
}
