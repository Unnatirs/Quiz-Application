package com.example.newapp;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newapp.Adapters.RankAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaderBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderBoardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView totalUsersTV,myImgTextTV,myScoreTV,myRankTV;
    private RecyclerView usersView;
    private RankAdapter adapter;
    private Dialog progressDialog;


    public LeaderBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeaderBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaderBoardFragment newInstance(String param1, String param2) {
        LeaderBoardFragment fragment = new LeaderBoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_leader_board, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("LeaderBoard");

        initViews(view);
        progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.dialog_layout2);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //dialogText =findViewById(R.id.dialog_text);
        //dialogText.setText();
        progressDialog.show();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        usersView.setLayoutManager(layoutManager);

        adapter = new RankAdapter(DbQuery.g_usersList);
        usersView.setAdapter(adapter);
        DbQuery.getTopusers(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                adapter.notifyDataSetChanged();
                if (DbQuery.myPerformance.getScore() != 0)
                {
                    if ( ! DbQuery.isMeOnTop)
                    {
                        calculateRank();
                    }
                    myScoreTV.setText("Score : "+DbQuery.myPerformance.getScore());
                    myRankTV.setText("Rank - "+DbQuery.myPerformance.getRank());


                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure() {
                Toast.makeText(getContext(), "Something went wrong Please try again.", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        totalUsersTV.setText("Total Users : "+DbQuery.g_usersCount);
        myImgTextTV.setText(DbQuery.myPerformance.getName().toUpperCase().substring(0,1));
        return view;
    }

    private void initViews(View view) {
        totalUsersTV = view.findViewById(R.id.total_users);
        myImgTextTV = view.findViewById(R.id.img_text);
        myScoreTV = view.findViewById(R.id.total_score);
        myRankTV = view.findViewById(R.id.rank);
        usersView = view.findViewById(R.id.users_view);
    }
    private  void calculateRank()
    {
        int lowTopScore = DbQuery.g_usersList.get(DbQuery.g_usersList.size() - 1).getScore();
        int remaining_slots = DbQuery.g_usersCount -20;
        int myslot = (DbQuery.myPerformance.getScore()*remaining_slots)/lowTopScore;
        int rank;
        if (lowTopScore != DbQuery.myPerformance.getScore())
        {
            rank = DbQuery.g_usersCount - myslot;
        }
        else {
            rank = 21;
        }
        DbQuery.myPerformance.setRank(rank);
    }
}