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


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABSE_NAME, factory, DATABASE_VERSION);
        System.out.println("db handler constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("onCreate");
        String query = "CREATE TABLE " + TABLE_VIOFILTERTIME + "(" +
                COLUMN_TIME + " text " +
                ");";
        System.out.println(query);
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIOFILTERTIME);
        onCreate(db);
    }

    public void addVioFilterTime(List<String> time){
        System.out.println("addVioFilterTime");
        System.out.println(time.size());
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_VIOFILTERTIME);
        for(int i=0;i<time.size();i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TIME, time.get(i));
            db.insert(TABLE_VIOFILTERTIME, null, values);
        }

        db.close();
    }

    public void deleteVioFilterTime(String time){
        System.out.println("deleteVioFilterTime");
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_VIOFILTERTIME + " WHERE " + COLUMN_TIME + "=\"" + time + "\";");
    }

    public ArrayList<String> getPreTime(){
        ArrayList<String> list = new ArrayList<>();
//        String query = "SELECT * FROM "+TABLE_VIOFILTERTIME;
//        SQLiteDatabase db = getWritableDatabase();
//        Cursor c = db.rawQuery(query, null);
//
//        if(c.moveToFirst()){
//            do {
//                list.add(c.getString(0));
//            }while(c.moveToNext());
//        }
//        db.close();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_VIOFILTERTIME;

        Cursor c = db.rawQuery(query,null);
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

    public String databaseToString(){
        System.out.println("databaseToString");
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_VIOFILTERTIME;

        Cursor c = db.rawQuery(query,null);
        if(c.getString(c.getColumnIndex("time"))!= null){

        }
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("time"))!= null){
                dbString += c.getString(c.getColumnIndex("time"));
                dbString += "\n";
            }
        }
        db.close();
        return dbString;
    }
}
