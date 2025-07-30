package com.example.newapp.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.example.newapp.DbQuery;
import com.example.newapp.Models.CategoryModel;
import com.example.newapp.R;
import com.example.newapp.TestActivity;

public class CategoryAdapter extends BaseAdapter {
    private List<CategoryModel> cat_list;

    public CategoryAdapter(List<CategoryModel> catList) {
        cat_list = catList;
    }


    @Override
    public int getCount() {
        return cat_list.size();
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
        View myview = null;
        if (view==null)
        {
            myview= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cat_item_layput,viewGroup,false);
        }
        else
        {
            myview=view;
        }

        myview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbQuery.g_selected_cat_index =i;
                Intent intent = new Intent(view.getContext(), TestActivity.class);

                view.getContext().startActivity(intent);

            }
        });

        TextView catName=myview.findViewById(R.id.catName);
        TextView noofTests=myview.findViewById(R.id.no_of_tests);
        catName.setText(cat_list.get(i).getName());
        noofTests.setText(String.valueOf(cat_list.get(i).getNoOfTests())+" Set");
        return myview;
    }
}
