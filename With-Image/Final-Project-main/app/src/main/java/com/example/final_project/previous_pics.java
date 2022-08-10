package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class previous_pics extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<String> elements = new ArrayList<>();
//    private MyListAdapter myAdapter;
    private View newView;
    Button buttonAdd;
    EditText edittxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exsisting_pics);

        //For toolbar:
        Toolbar tBar = findViewById(R.id.toolbar);
        setSupportActionBar(tBar);


        //For NavigationDrawer:
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
/*
        buttonAdd.setOnClickListener( (new View.OnClickListener() {
            @Override
            public void onClick(View old){



                String text;
                text = edittxt.getText().toString();

                // finLab4.setAdapter(myAdapter);


                elements.add(text);

                myAdapter.notifyDataSetChanged();
                edittxt.getText().clear();

                return;
            }
        }));
*/

    }
/*
    private class MyListAdapter extends BaseAdapter {


        public int getCount() {
            return elements.size();
        }

        public Object getItem(int position) {
            return elements.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View old, ViewGroup parent) {



            View newView = old;
            LayoutInflater inflater = getLayoutInflater();


            //make a new row:
            if (newView == null) {

                newView = inflater.inflate(R.layout.exsisting_pics, parent, false);


            }
        return newView;

        }


    }
*/
@Override
public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    String message = null;
    switch (item.getItemId()) {
        case R.id.item1:
            message = getString(R.string.clickedMain);

            break;
        case R.id.item2:
            message = getString(R.string.clickedNew);

            break;
        case R.id.item3:
            message = getString(R.string.clickedExist);
            break;
    }
    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    return true;
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String message = null;
        switch (item.getItemId()) {
            case R.id.item1:
                message = getString(R.string.mainPage);
                startActivity(new Intent(previous_pics.this, MainActivity.class));

                break;
            case R.id.item2:
                message = getString(R.string.newPic);
                startActivity(new Intent(previous_pics.this, nasa_pic.class));
                break;
            case R.id.item3:
                message = "THE DARKNESS COMES!";
                finishAffinity();
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }
}
