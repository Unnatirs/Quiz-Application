package com.example.newapp.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.newapp.DbQuery;
import com.example.newapp.QuestionsActivity;
import com.example.newapp.R;

public class QuestionGridAdapter extends BaseAdapter {
    public QuestionGridAdapter(Context context,int numOfQues) {
        this.numOfQues = numOfQues;
        this.context = context;
    }

    private int numOfQues;
    private Context context;
    @Override
    public int getCount() {
        return numOfQues;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View myview;
        if (view == null){
            myview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ques_grid_item,viewGroup,false);
        }else {
            myview = view;
        }
        myview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof QuestionsActivity)
                    ((QuestionsActivity)context).goToQuestion(i);
            }
        });
        TextView quesTV =myview.findViewById(R.id.ques_num);
        quesTV.setText(String.valueOf(i+1));
        if (DbQuery.g_quesList.get(i).getStatus() == DbQuery.NOT_VISITED)
        {
            quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(),R.color.gray)));
        }
        if (DbQuery.g_quesList.get(i).getStatus() == DbQuery.UNANSWERD)
        {
            quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(),R.color.red)));
        }
        if (DbQuery.g_quesList.get(i).getStatus() == DbQuery.ANSWERD)
        {
            quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(),R.color.green)));
        }
        if (DbQuery.g_quesList.get(i).getStatus() == DbQuery.REVIEW)
        {
            quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(),R.color.pink)));
        }

        return myview;
    }
}
