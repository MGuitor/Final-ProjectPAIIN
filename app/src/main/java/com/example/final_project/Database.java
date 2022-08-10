package com.example.final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.net.URL;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "NasaPicsDB";
    protected final static int VERSION_NUM = 3;
    public final static String TABLE_NAME = "NasaPictures";
    public final static String COL_DATE = "DATE";
    public final static String COL_EXPLAIN = "NEXPLAIN";
    public final static String COL_MEDIA = "MEDIA";
    public final static String COL_SERVICE = "SERVICE";
    public final static String COL_TITLE = "TITLE";
    public final static String COL_URL = "NURL";
    public final static String COL_HDURL = "HDURL";
    public final static String COL_ID = "_id";
    private Object NasaPicture;



    /*
    long id;
    String date;
    String explain;
    String media_type;
    String service_version;
    String title;
    String url;
    String hdurl;
*/

    public Database(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " text, " + COL_DATE +  " text, " + COL_EXPLAIN +
                " text, " + COL_URL +  " text, "   + COL_HDURL +  " text, "  +
                COL_MEDIA +  " text, " + COL_SERVICE  +" text);");

        // add or remove columns
    }

    // https://www.geeksforgeeks.org/how-to-read-data-from-sqlite-database-in-android/

    public void addNewItem(String TITLE, String DATE, String NEXPLAIN, String NURL,
                           String HDURL, String MEDIA, String SERVICE , Long _id) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(COL_TITLE, TITLE);
        values.put(COL_DATE, DATE);
        values.put(COL_EXPLAIN, NEXPLAIN);
        values.put(COL_URL, NURL);
        values.put(COL_HDURL, HDURL);
        values.put(COL_MEDIA, MEDIA);
        values.put(COL_SERVICE, SERVICE);

        values.put(COL_ID, _id);


        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    // we have created a new method for reading all the courses.

    public ArrayList<NasaPicture> readToDo() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();
        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        // on below line we are creating a new array list.
        ArrayList<NasaPicture> NasaPic = new ArrayList<>();
        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                NasaPic.add(new NasaPicture(cursorCourses.getString(1),
                                cursorCourses.getLong(2),
                                cursorCourses.getString(3),
                                cursorCourses.getString(4),
                                cursorCourses.getString(5),
                                cursorCourses.getString(6),
                                cursorCourses.getString(7),
                                cursorCourses.getString(8)));
            } while (cursorCourses.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return (ArrayList<NasaPicture>) NasaPicture;
    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);

    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }
}
