package database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABSE_NAME = "trunkmon.db";

    public static final String TABLE_VIOFILTERTIME = "vioFilterTime";
    public static final String COLUMN_TIME = "time";

    public static final String TABLE_VIOFILTERSTARTCOUNTRY = "vioFilterStartCountry";
    public static final String COLUMN_START_COUNTRY = "startcountry";

    public static final String TABLE_VIOFILTERDIVISION = "vioFilterDivision";
    public static final String COLUMN_DIVISION = "division";

    public static final String TABLE_VIOFILTERADD = "vioFilterAdd";
    public static final String COLUMN_ADD = "additional";

    public static final String TABLE_VIOFILTERSHOW = "vioFilterShow";
    public static final String COLUMN_SHOW = "showfields";


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABSE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_VIOFILTERTIME + "(" +
                COLUMN_TIME + " text " +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_VIOFILTERSTARTCOUNTRY + "(" +
                COLUMN_START_COUNTRY + " text " +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_VIOFILTERDIVISION + "(" +
                COLUMN_DIVISION + " text " +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_VIOFILTERADD + "(" +
                COLUMN_ADD + " text " +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_VIOFILTERSHOW + "(" +
                COLUMN_SHOW + " text " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIOFILTERTIME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIOFILTERSTARTCOUNTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIOFILTERDIVISION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIOFILTERADD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIOFILTERSHOW);
        onCreate(db);
    }

    public void addVioFilterTime(List<String> time){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_VIOFILTERTIME);
        for(int i=0;i<time.size();i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TIME, time.get(i));
            db.insert(TABLE_VIOFILTERTIME, null, values);
        }

        db.close();
    }

    public ArrayList<String> getPreTime(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_VIOFILTERTIME;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("time"))!= null){
                list.add(c.getString(c.getColumnIndex("time")));
            }
            c.moveToNext();
        }
        db.close();
        return list;
    }

    public void addVioFilterStartCountry(List<String> startCountries){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_VIOFILTERSTARTCOUNTRY);
        for(int i=0;i<startCountries.size();i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_START_COUNTRY, startCountries.get(i));
            db.insert(TABLE_VIOFILTERSTARTCOUNTRY, null, values);
        }
        db.close();
    }

    public ArrayList<String> getPreStartCountry(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_VIOFILTERSTARTCOUNTRY;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("startcountry"))!= null){
                list.add(c.getString(c.getColumnIndex("startcountry")));
            }
            c.moveToNext();
        }
        db.close();
        return list;
    }

    public void addVioFilterDivision(List<String> divisions){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_VIOFILTERDIVISION);
        for(int i=0;i<divisions.size();i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_DIVISION, divisions.get(i));
            db.insert(TABLE_VIOFILTERDIVISION, null, values);
        }
        db.close();
    }

    public ArrayList<String> getPreDivision(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_VIOFILTERDIVISION;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("division"))!= null){
                list.add(c.getString(c.getColumnIndex("division")));
            }
            c.moveToNext();
        }
        db.close();
        return list;
    }

    public void addVioFilterAdd(List<String> addItems){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_VIOFILTERADD);
        for(int i=0;i<addItems.size();i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ADD, addItems.get(i));
            db.insert(TABLE_VIOFILTERADD, null, values);
        }
        db.close();
    }

    public ArrayList<String> getPreAdditional(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_VIOFILTERADD;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("additional"))!= null){
                list.add(c.getString(c.getColumnIndex("additional")));
            }
            c.moveToNext();
        }
        db.close();
        return list;
    }

    public void addVioFilterShowFileds(List<String> showFields){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_VIOFILTERSHOW);
        for(int i=0;i<showFields.size();i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_SHOW, showFields.get(i));
            db.insert(TABLE_VIOFILTERSHOW, null, values);
        }
        db.close();
    }

    public ArrayList<String> getPreShowFields(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_VIOFILTERSHOW;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("showfields"))!= null){
                list.add(c.getString(c.getColumnIndex("showfields")));
            }
            c.moveToNext();
        }
        db.close();
        return list;
    }
}
