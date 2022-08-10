package com.example.final_project;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigInteger;
import java.util.ArrayList;

public class previous_pics extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<NasaPicture> nasaPic = new ArrayList<>();
    ListView theList = findViewById(R.id.PreviousItems);
    SQLiteDatabase db;
    // MyOwnAdapter myAdapter;
    Context context;



    private static String input;
    private static nasa_pic.nasaImages req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exsisting_pics);
       // theList.setAdapter(myAdapter);

        Intent dataSent = getIntent();
        String typed = dataSent.getStringExtra("typed");

       // loadDataFromDatabase();

        Intent lastPage = getIntent();
        String sentText = lastPage.getStringExtra("encoded");

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


//        byte[] imageAsBytes = Base64.decode(sentText.getBytes(), Base64.DEFAULT);
//        ImageView image = (ImageView)this.findViewById(R.id.previousView);
//        image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));



/*

        theList.setOnItemLongClickListener((p, b, pos, id) -> {
            showNasaPics(pos);
            return true;
        });


*/

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
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(previous_pics.this);
                alertDialogBuilder.setTitle(helpTitle)
                        .setMessage("Heres how")
                        .setMessage(helpBody)
                        .setPositiveButton("yes", (click, arg) -> {
                        })
                        .setNegativeButton("no" , (click, arg) -> {
                            Snackbar.make(theList, notHelped, Snackbar.LENGTH_LONG).show();

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
                message = getString(R.string.mainPage);
                startActivity(new Intent(previous_pics.this, MainActivity.class));

                break;
            case R.id.item2:
                message = getString(R.string.newPic);
                startActivity(new Intent(previous_pics.this, nasa_pic.class));
                break;
            case R.id.item3:
                message = getString(R.string.exsist);
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }

    // Should be able to use DatabaseHelper instead?
/*
    private void loadDataFromDatabase() {
        //get a database connection:
        Database dbOpener = new Database(this);
         db = dbOpener.getWritableDatabase();


        // We want to get all of the columns. Look at Database.java for the definitions:
        String[] columns = {Database.COL_TITLE, Database.COL_ID, Database.COL_DATE, Database.COL_EXPLAIN, Database.COL_URL,
                Database.COL_HDURL, Database.COL_MEDIA, Database.COL_SERVICE};
        //query all the results from the database:
        Cursor results = db.query(false, Database.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int TitColInd = results.getColumnIndex(Database.COL_TITLE);
        int idColIndex = results.getColumnIndex(Database.COL_ID);
        int dateColInd = results.getColumnIndex(Database.COL_DATE);
        int ExpColIndex = results.getColumnIndex(Database.COL_EXPLAIN);
        int UrlColInd = results.getColumnIndex(Database.COL_URL);
        int hdUrlColIndex = results.getColumnIndex(Database.COL_HDURL);
        int mediaColInd = results.getColumnIndex(Database.COL_MEDIA);
        int servColIndex = results.getColumnIndex(Database.COL_SERVICE);

        //iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            String Title = results.getString(TitColInd);
            long id = results.getLong(idColIndex);
            String date = results.getString(dateColInd);
            String Explain = results.getString(ExpColIndex);
            String url = results.getString(UrlColInd);
            String hdurl = results.getString(hdUrlColIndex);
            String media = results.getString(mediaColInd);
            String service = results.getString(servColIndex);

            //add the new Contact to the array list:

            nasaPic.add(new NasaPicture(Title, id, date, Explain, url, hdurl, media, service));

        }


    }

    protected void showNasaPics(int position) {
        NasaPicture selectedItem = nasaPic.get(position);

        View ToDo_view = getLayoutInflater().inflate(R.layout.list_view, null);
        //get the TextViews
       // EditText rowItem = ToDo_view.findViewById(R.id.row_Item);
        //Switch urgn = ToDo_view.findViewById(R.id.EdiUrg);
        TextView rowId = ToDo_view.findViewById(R.id.row_id);

        //set the fields for the alert dialog
        // rowItem.setText(selectedItem.getToDo());
        // urgn.setChecked(selectedItem.getUrgent());
        // rowId.setText("id:" + selectedItem.getId());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("select" + position)
                .setMessage("likeTo")
                .setView(ToDo_view) //add the 3 edit texts showing the contact information
                .setPositiveButton("yes", (click, b) -> {
                    deleteToDo(selectedItem); //remove the contact from database
                    nasaPic.remove(position); //remove the contact from contact list
                    myAdapter.notifyDataSetChanged(); //there is one less item so update the listlist
                })
                .setNegativeButton("no", (click, b) -> {

                })
                .create().show();
    }

    protected void deleteToDo(NasaPicture c) {
        db.delete(Database.TABLE_NAME, Database.COL_ID + "= ?", new String[]{Long.toString(c.getId())});
    }


    private class MyOwnAdapter extends BaseAdapter {


        public int getCount() {
            return nasaPic.size();
        }

        public NasaPicture getItem(int position) {
            return nasaPic.get(position);
        }


        public View getView(int position, View old, ViewGroup parent) {

          //  Switch redSw = findViewById(R.id.switchUr);
          //  Boolean isUrgent = redSw.isChecked();

            LayoutInflater inflater = getLayoutInflater();
            NasaPicture thisRow = (NasaPicture) getItem(position);
            View newView = inflater.inflate(R.layout.list_view, parent, false);

            //make a new row:


            TextView rowItem = newView.findViewById(R.id.textView);
            TextView rowID = newView.findViewById(R.id.row_id);


            rowItem.setText(thisRow.getTitle() + thisRow.getDate());
            rowID.setText("id: " + thisRow.getId());
            //return it to be put in the table
            return newView;


        }

        public long getItemId(int position) {
            return getItem(position).getId();
        }
    }
*/

}
