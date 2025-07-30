package com.example.newapp.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newapp.DbQuery;
import com.example.newapp.Models.TestModel;
import com.example.newapp.R;
import com.example.newapp.StartTestActivity;

import java.util.List;

public class TestAdapter  extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    private List<TestModel> testList;

    public TestAdapter(List<TestModel> testList){
        this.testList=testList;
    }

    @NonNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int progress =testList.get(position).getTopScore();
        holder.setData(position,progress);

    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView testNO;
        private TextView topscore;
        private ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            testNO = itemView.findViewById(R.id.testNo);
            topscore = itemView.findViewById(R.id.scoretest);
            progressBar = itemView.findViewById(R.id.testProgressbar);

        }
        private void setData(final int pos,int progress){

            testNO.setText("Test NO:"+String.valueOf(pos+1));
            topscore.setText(String.valueOf(progress)+"%");
            progressBar.setProgress(progress);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DbQuery.g_selected_test_index = pos;
                    Intent intent = new Intent(itemView.getContext(), StartTestActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }


    }
}
