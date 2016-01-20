package database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

public class VioDBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABSE_NAME = "trunkmon.db";

    public static final String TABLE_TIME = "TableTime";
    public static final String COLUMN_TIME = "time";

    public static final String TABLE_START_COUNTRY = "TableStartCountry";
    public static final String COLUMN_START_COUNTRY = "startcountry";

    public static final String TABLE_DIVISION = "TableDivision";
    public static final String COLUMN_DIVISION = "division";

    public static final String TABLE_ADDITIONAL_ITEMS = "TableAdd";
    public static final String COLUMN_ADD = "additional";

    public static final String TABLE_SHOW_FIELDS = "TableShow";
    public static final String COLUMN_SHOW = "showfields";


    public VioDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABSE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * This function is called when the database file did not exist and was just created.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_TIME + "(" +
                COLUMN_TIME + " text " +
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

        query = "CREATE TABLE " + TABLE_ADDITIONAL_ITEMS + "(" +
                COLUMN_ADD + " text " +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_SHOW_FIELDS + "(" +
                COLUMN_SHOW + " text " +
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_START_COUNTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIVISION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDITIONAL_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOW_FIELDS);
        onCreate(db);
    }

    /**
     * This function adds a list of time strings into database
     * @param time
     */
    public void addTime(List<String> time){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TIME);
        for(int i=0;i<time.size();i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TIME, time.get(i));
            db.insert(TABLE_TIME, null, values);
        }

        db.close();
    }

    /**
     * This function gets all time strings from database
     * @return An arraylist of time strings
     */
    public ArrayList<String> getPreTime(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_TIME;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_TIME))!= null){
                list.add(c.getString(c.getColumnIndex(COLUMN_TIME)));
            }
            c.moveToNext();
        }
        db.close();
        return list;
    }

    /**
     * This function adds a list of start country strings into database
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
     * This function adds a list of division strings into database
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

    /**
     * This function adds a list of additional items strings into database
     * @param addItems
     */
    public void addAdditional(List<String> addItems){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ADDITIONAL_ITEMS);
        for(int i=0;i<addItems.size();i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ADD, addItems.get(i));
            db.insert(TABLE_ADDITIONAL_ITEMS, null, values);
        }
        db.close();
    }

    /**
     * This function gets all additional items from database
     * @return An arraylist of additional item strings
     */
    public ArrayList<String> getPreAdditional(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_ADDITIONAL_ITEMS;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_ADD))!= null){
                list.add(c.getString(c.getColumnIndex(COLUMN_ADD)));
            }
            c.moveToNext();
        }
        db.close();
        return list;
    }

    /**
     * This function adds a list of show fields strings into database
     * @param showFields
     */
    public void addShowFileds(List<String> showFields){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SHOW_FIELDS);
        for(int i=0;i<showFields.size();i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_SHOW, showFields.get(i));
            db.insert(TABLE_SHOW_FIELDS, null, values);
        }
        db.close();
    }

    /**
     * This function gets all show fields from database
     * @return An arraylist of show field strings
     */
    public ArrayList<String> getPreShowFields(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_SHOW_FIELDS;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_SHOW))!= null){
                list.add(c.getString(c.getColumnIndex(COLUMN_SHOW)));
            }
            c.moveToNext();
        }
        db.close();
        return list;
    }
}
