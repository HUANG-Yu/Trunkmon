package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ThresDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABSE_NAME = "threshold.db";

    public static final String TABLE_COUNTRY = "TableCountry";
    public static final String COLUMN_COUNTRY = "country";

    public static final String TABLE_START_COUNTRY = "TableStartCountry";
    public static final String COLUMN_START_COUNTRY = "startcountry";

    public static final String TABLE_DIVISION = "TableDivision";
    public static final String COLUMN_DIVISION = "division";

    public ThresDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABSE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * This function is called when the database file did not exist and was just created.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_COUNTRY + "(" +
                COLUMN_COUNTRY + " text " +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_START_COUNTRY + "(" +
                COLUMN_START_COUNTRY + " text " +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_DIVISION + "(" +
                COLUMN_DIVISION + " text " +
                ");";
        db.execSQL(query);
    }

    /**
     * This function is called when the database file exists but the stored version number is lower
     * than requested in constructor
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_START_COUNTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIVISION);
        onCreate(db);
    }

    /**
     * This function adds a list of country strings into database
     * @param countries
     */
    public void addCountry(List<String> countries){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_COUNTRY);
        for(int i=0;i<countries.size();i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_COUNTRY, countries.get(i));
            db.insert(TABLE_COUNTRY, null, values);
        }
        db.close();
    }

    /**
     * This function gets all countries from database
     * @return An arraylist of country strings
     */
    public ArrayList<String> getPreCountry(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_COUNTRY;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_COUNTRY))!= null){
                list.add(c.getString(c.getColumnIndex(COLUMN_COUNTRY)));
            }
            c.moveToNext();
        }

        db.close();
        return list;
    }

    /**
     * This function adds a list of start country strings into database.
     * @param startCountries
     */
    public void addStartCountry(List<String> startCountries){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_START_COUNTRY);
        for(int i=0;i<startCountries.size();i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_START_COUNTRY, startCountries.get(i));
            db.insert(TABLE_START_COUNTRY, null, values);
        }
        db.close();
    }

    /**
     * This function gets all start countries from database
     * @return An arraylist of start country strings
     */
    public ArrayList<String> getPreStartCountry(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_START_COUNTRY;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_START_COUNTRY))!= null){
                list.add(c.getString(c.getColumnIndex(COLUMN_START_COUNTRY)));
            }
            c.moveToNext();
        }
        db.close();
        return list;
    }

    /**
     * This function adds a list of division strings into database.
     * @param divisions
     */
    public void addDivision(List<String> divisions){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_DIVISION);
        for(int i=0;i<divisions.size();i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_DIVISION, divisions.get(i));
            db.insert(TABLE_DIVISION, null, values);
        }
        db.close();
    }

    /**
     * This function gets all divisions from database
     * @return An arraylist of division strings
     */
    public ArrayList<String> getPreDivision(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_DIVISION;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_DIVISION))!= null){
                list.add(c.getString(c.getColumnIndex(COLUMN_DIVISION)));
            }
            c.moveToNext();
        }
        db.close();
        return list;
    }
}
