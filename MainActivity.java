package com.example.newapp;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.NavigationUI;

import com.example.newapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout main_frame;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Toolbar toolbar2;

    private TextView drawerProfileName,drawerProfileText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar2 = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Categories");


        bottomNavigationView =findViewById(R.id.bottom_nav_bar);
        main_frame = findViewById(R.id.main_frame);
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_frame,new CategoryFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId=item.getItemId();
                if (itemId== R.id.nav_home){
                    setFragment(new CategoryFragment());
                    return true;
                } else if (itemId== R.id.nav_leaderboard) {
                   setFragment(new LeaderBoardFragment());

                    return true;
                } else if (itemId== R.id.nav_account) {
                    setFragment(new AccountFragment());
                    return true;
                }
                return false;
            }
        });
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_account ,R.id.nav_leaderboard)
                .setOpenableLayout(drawer)
                .build();
        NavController navController =Navigation.findNavController(this,R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this,navController,mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView,navController);
        drawerProfileName =navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_name);
        drawerProfileText = navigationView.getHeaderView(0).findViewById(R.id.nav_drawer_text_img);
        String name= DbQuery.myProfile.getName();
        drawerProfileName.setText(name);
        String name1 =name.substring(0,1).toUpperCase();
        drawerProfileText.setText(name1);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_account1){
                    setFragment(new AccountFragment());
                    return true;
                } else if (item.getItemId() == R.id.nav_home1){
                    setFragment(new CategoryFragment());
                    return true;
                }else if (item.getItemId() == R.id.nav_leaderboard){
                    setFragment(new LeaderBoardFragment());
                    return true;
                } else if (item.getItemId() == R.id.nav_bookmark) {
                    Intent intent=new Intent(MainActivity.this,BookMarksActivity.class);
                    startActivity(intent);
                }

                return false;
            }

        });

        setFragment(new CategoryFragment());
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setFragment(CategoryFragment fragment)
    {
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(main_frame.getId(), fragment);
        transaction.commit();
    }
    private void setFragment(LeaderBoardFragment fragment)
    {
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(main_frame.getId(), fragment);
        transaction.commit();
    }
    private void setFragment(AccountFragment fragment)
    {
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(main_frame.getId(), fragment);
        transaction.commit();
    }


}