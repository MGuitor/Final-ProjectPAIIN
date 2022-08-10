package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class nasa_pic extends MainActivity {

    ArrayList<NasaPicture> nasaPic = new ArrayList<>();
    private static String input;
    private static nasaImages req;
    SQLiteDatabase db;
//    Intent nextPage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nasa_pic);


        //For toolbar:
        Toolbar tBar = findViewById(R.id.toolbar);
        setSupportActionBar(tBar);


        Intent dataSent = getIntent();
        String typed = dataSent.getStringExtra("typed");

        TextView welcome = findViewById(R.id.Date);
        welcome.setText(getString(R.string.newpictures, typed));

        //For NavigationDrawer:
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        req = new nasaImages();

//        if (input != null) {
//
//            // Initial intent with putExtra function
//
//            nextPage = new Intent(nasa_pic.this, previous_pics.class); {
//                nextPage.putExtra("sentText", input);
//            }
//        }

        loadDataFromDatabase();
    }


    class nasaImages extends AsyncTask<String, Integer, Bitmap> {

        Bitmap image;
        ProgressBar bar = findViewById(R.id.proBar);
        TextView urlDisplay = findViewById(R.id.urlDisplay);
        TextView dateDisplay = findViewById(R.id.dateDisplay);




        @Override
        protected Bitmap doInBackground(String... args) {

            while (true) {
                try {

                    //create a URL object of what server to contact:
                    URL url = new URL(args[0]);

                    //open the connection
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    //wait for data:
                    InputStream response = urlConnection.getInputStream();

                    //JSON reading:
                    //Build the entire string response:
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response, StandardCharsets.UTF_8), 8);
                    StringBuilder sb = new StringBuilder();

                    String line;
                    while ((line = reader.readLine()) != null) {

                        sb.append(line).append("\n");
                    }

                    String result = sb.toString(); //result is the whole string


                    // convert string to JSON:
                    JSONObject display = new JSONObject(result);



                    String hdURL = display.get("hdurl").toString();
                    String newURL = (String) display.get("url");
                    String date = (String) display.get("date");
                    String title = (String) display.get("title");
                    String explain = (String) display.get("explanation");
                    String media_type = (String) display.get("media_type");
                    String service_version = (String) display.get("service_version");

                    ContentValues newRowValues = new ContentValues();

                    newRowValues.put(Database.COL_TITLE, title);
                    newRowValues.put(Database.COL_DATE, date);
                    newRowValues.put(Database.COL_EXPLAIN, explain);
                    newRowValues.put(Database.COL_MEDIA, media_type);
                    newRowValues.put(Database.COL_SERVICE, service_version);
                    newRowValues.put(Database.COL_URL, newURL);
                    newRowValues.put(Database.COL_HDURL, hdURL);
                    long newID = db.insert(Database.TABLE_NAME, null,newRowValues);

                    NasaPicture newItem = new NasaPicture(title, newID, date, explain, newURL, hdURL, media_type, service_version);

                    // TITLE, _id, DATE, NEXPLAIN, NURL, HDURL, MEDIA, SERVICE

                    // date, explain, media_type, service_version, title, newURL, hdURL, newID

                    nasaPic.add(newItem);



                    nasaImages req = new nasaImages();
                    URL catURL = new URL(newURL);
                    HttpURLConnection connection = (HttpURLConnection) catURL.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    image = BitmapFactory.decodeStream(input);

//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    image.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
//                    byte[] b = baos.toByteArray();
//
//                    String encoded = Base64.encodeToString(b, Base64.DEFAULT);
//
//                    SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor edit = pref.edit();
//                    edit.putString("encoded", encoded);
//                    edit.apply();

                    Log.i("MainActivity", "The result is: " + result);
                    Log.i("MainActivity", "HD URL: " + hdURL);
                    Log.i("MainActivity", "URL: " + newURL);
                    Log.i("MainActivity", "DATE: " + date);
                    Log.i("MainActivity", "Decoded Response: " + response);


                    String fileName = "NASA File";
                    Context context = getApplicationContext();

                    File file = new File(context.getFilesDir(), fileName);

                    FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);

                    if (!file.exists()) {

                        //
                        //
                        //  THREAD IS SLEEPIGN FOR 83 HOURS
                        //
                        //



                        for (int i = 0; i < 10000000; i++) {
                            try {
                                bar.setProgress(0);
                                publishProgress(i);
                                Thread.sleep(300000000);



                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {

                        image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                        outputStream.flush();
                        outputStream.close();
                        Log.i("FileName: ", fileName + " was downloaded");


                        for (int i = 0; i < 1000000000; i++) {
                            try {
                                bar.setProgress(0);
                                publishProgress(i);
                                urlDisplay.setText(hdURL);
                                dateDisplay.setText(getResources().getString(R.string.selectedDate) + " " + date);
                                Thread.sleep(300000000);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        public void onProgressUpdate(Integer... args){

            ImageView imageView = findViewById(R.id.nasaView);
            imageView.setImageBitmap(image);
            ((ProgressBar)findViewById(R.id.proBar)).setProgress(args[0]);

        }
    }

        // DATE PICKER AND FUNCTIONS
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(requireContext(), this, year, month, day);

        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            String fullURL;
            String dayAppend;
            String monthAppend;

            String url = "https://api.nasa.gov/planetary/apod?api_key=4HySHCGctcfOdDoBCvATkObI5e0SsZBYid3yzEwm&date=";

            StringBuilder dateB = new StringBuilder();
            StringBuilder urlB = new StringBuilder();

            if(day < 10){
                dayAppend = "-0";
            } else {
                dayAppend = "-";
            }
            if(month < 10){
                monthAppend = "-0";
            } else {
                monthAppend = "-";
            }

            Log.i("MainActivity", "YEAR: " + year);
            Log.i("MainActivity", "MONTH: " + month);
            Log.i("MainActivity", "DAY: " + day);

            dateB.append(year).append(monthAppend).append(month).append(dayAppend).append(day);

            String date = dateB.toString();

            urlB.append(url).append(date);

            fullURL = urlB.toString();

            nasa_pic.input = fullURL;
            Log.i("MainActivity", "URL: " + nasa_pic.input);


            req.execute(fullURL);

        }

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

//    private void startActivityForResult(Intent nextPage) {
//
//        // Function to retrieve RESULTS which either finishAffinity();
//        // the application OR begin the process over again
//
//        startActivity(nextPage);
//        Intent lastPage = getIntent();
//
//        int result = lastPage.getIntExtra("resultCode", 0);
//
//        if (result == 1)
//            finishAffinity();
//    }
//
//    @Override
//    protected void onPause() {
//
//        super.onPause();
//
//
//        // SharedPreferences is set with EditText input and saved to update the STRING
//
//        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = pref.edit();
//        edit.putString("saved", input);
//        edit.apply();
//    }

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
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(nasa_pic.this);
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
                message = getString(R.string.mainPage);
                startActivity(new Intent(nasa_pic.this, MainActivity.class));
                break;
            case R.id.item2:
                message = getString(R.string.newPic);
                break;
            case R.id.item3:
                message = getString(R.string.exsist);
                startActivity(new Intent(nasa_pic.this, previous_pics.class));
//                startActivity(nextPage);
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return true;
    }
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
        printCursor(results,3);



    }




        //At this point, the contactsList array has loaded every row from the cursor.


        void printCursor(Cursor c, int DBV) {
        int DataVersion = DBV;
        int numOfColumns = c.getColumnCount();
        int ammountOfC = c.getCount();
        String[] nameOColumns = c.getColumnNames();
        ArrayList<NasaPicture> ValuesList = new ArrayList<>();
        String Values;

        c.moveToFirst();

        if (c.moveToFirst()) {
            int TitColInd = c.getColumnIndex(Database.COL_TITLE);
            int idColIndex = c.getColumnIndex(Database.COL_ID);
            int dateColInd = c.getColumnIndex(Database.COL_DATE);
            int ExpColIndex = c.getColumnIndex(Database.COL_EXPLAIN);
            int UrlColInd = c.getColumnIndex(Database.COL_URL);
            int hdUrlColIndex = c.getColumnIndex(Database.COL_HDURL);
            int mediaColInd = c.getColumnIndex(Database.COL_MEDIA);
            int servColIndex = c.getColumnIndex(Database.COL_SERVICE);

            String Title = c.getString(TitColInd);
            long id = c.getLong(idColIndex);
            String date = c.getString(dateColInd);
            String Explain = c.getString(ExpColIndex);
            String url = c.getString(UrlColInd);
            String hdurl = c.getString(hdUrlColIndex);
            String media = c.getString(mediaColInd);
            String service = c.getString(servColIndex);


            ValuesList.add(new NasaPicture(Title, id, date, Explain, url, hdurl, media, service));
        }
        Values = TextUtils.join(" , ", ValuesList);

        Log.i("DATABASE VERSION", Integer.toString(DBV));
        Log.i("NUMBER OF COLUMNS", Integer.toString(numOfColumns));
        Log.i("COLUMN NAMES", Arrays.toString(nameOColumns));
        Log.i("NUMBER OF RESULTS", Integer.toString(ammountOfC));
        Log.i("ROW VALUES", Values);

    }




}