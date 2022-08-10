package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class nasa_pic extends MainActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nasa_pic);

        Log.i("MainActivity", "The result is: ");

        CatImages req = new CatImages();
        req.execute("https://api.nasa.gov/planetary/apod?api_key=DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d&date=2020-02-01");  //Type 1

    }

    class CatImages extends AsyncTask<String, Integer, Bitmap> {

        Bitmap image;
        ProgressBar bar = findViewById(R.id.proBar);

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


                    String urlEND = display.get("hdurl").toString();
                    String newURL = (String) display.get("url");


                    URL catURL = new URL(newURL);
                    HttpURLConnection connection = (HttpURLConnection) catURL.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    image = BitmapFactory.decodeStream(input);


                    Log.i("MainActivity", "The result is: " + result);
                    Log.i("MainActivity", "URL END: " + urlEND);
                    Log.i("MainActivity", "FULL URL: " + newURL);
                    Log.i("MainActivity", "Decoded Response: " + response);


                    String fileName = "NASA File";
                    Context context = getApplicationContext();

                    File file = new File(context.getFilesDir(), fileName);

                    FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);

                    if (!file.exists()) {


                        for (int i = 0; i < 100; i++) {
                            try {
                                bar.setProgress(0);
                                publishProgress(i);
                                Thread.sleep(30);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {

                        image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                        outputStream.flush();
                        outputStream.close();
                        Log.i("FileName: ", fileName + " was downloaded");

                        for (int i = 0; i < 100; i++) {
                            try {
                                bar.setProgress(0);
                                publishProgress(i);
                                Thread.sleep(30);


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
}