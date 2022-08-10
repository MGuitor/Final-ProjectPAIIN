package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText typeField;
    String PREF_FILE_NAME = "shared_prefs";
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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


        typeField = findViewById(R.id.Name);
        Button nextButton = findViewById(R.id.Button);

        prefs = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        if (prefs.contains("typed")){
            typeField.setText(prefs.getString("typed", ""));
        }

        nextButton.setOnClickListener(click -> {

            Intent nextPage = new Intent (MainActivity.this, nasa_pic.class);
            nextPage.putExtra("typed", typeField.getText().toString() );
            startActivityForResult(nextPage, 1);
            SharedPreferences.Editor prefsEditor = prefs.edit();
            prefsEditor.putString("typed", typeField.getText().toString());
            prefsEditor.commit();

        });






    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String message = null;
        switch (item.getItemId()) {
            case R.id.item1:
                message = getString(R.string.clickedMain);

                // SAVE IMAGE WITH THIS

                break;
            case R.id.item2:
                message = getString(R.string.clickedNew);

                // LOAD RANDOM IMAGE WITH THIS

                break;
            case R.id.item3:
                message = getString(R.string.clickedExist);

                // NEEDS FUNCTIONS

                break;
            case R.id.item4:
                message = getString(R.string.helpSelected);
                String helpTitle = getString(R.string.helpTitle);
                String helpBody = getString(R.string.helpBodyMain);
                String helped = getString(R.string.helped);
                String notHelped = getString(R.string.notHelped);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle(helpTitle)
                        .setMessage("Heres how")
                        .setMessage(helpBody)
                        .setPositiveButton("yes", (click, arg) -> {
                        })
                        .setNegativeButton("no" , (click, arg) -> {
                            Snackbar.make(typeField, notHelped, Snackbar.LENGTH_LONG).show();

                        })
                        .setView(getLayoutInflater().inflate(R.layout.list_view, null))
                        .create().show();

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
                message = getString(R.string.homeAlready);
                break;
            case R.id.item2:
                message = getString(R.string.newPic);
                startActivity(new Intent(MainActivity.this, nasa_pic.class));
                break;
            case R.id.item3:
                message = getString(R.string.exsist);
                startActivity(new Intent(MainActivity.this, previous_pics.class));
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }

}