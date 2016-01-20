package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABSE_NAME = "user.db";

    public static final String TABLE_USER = "TableUser";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE = "role";

    public UserDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABSE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("user database onCreate");
        String query = "CREATE TABLE " + TABLE_USER + "(" +
                COLUMN_USERNAME + " text, " +
                COLUMN_PASSWORD + " text, " +
                COLUMN_ROLE + " text " +
                ");";
        db.execSQL(query);
        System.out.println("user table created");

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, "admin");
        values.put(COLUMN_PASSWORD, "admin");
        values.put(COLUMN_ROLE, "manager");
        db.insert(TABLE_USER, null, values);

        ContentValues values2 = new ContentValues();
        values2.put(COLUMN_USERNAME, "guest");
        values2.put(COLUMN_PASSWORD, "guest");
        values2.put(COLUMN_ROLE, "visitor");
        db.insert(TABLE_USER, null, values2);

        System.out.println("user data added");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public int checkUser(String username, String password){
        System.out.println("check user");
//        int role = -1;      //if wrong username or password, return -1
                            //manager return 1, visitor return 0
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM "+ TABLE_USER + " WHERE " + COLUMN_USERNAME + " = '" + username + "'";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        if(c.isAfterLast()){
            db.close();
            return -1;
        }else if(!(c.getString(c.getColumnIndex("password")).equals(password))){
            db.close();
            return -1;
        }else if(c.getString(c.getColumnIndex(COLUMN_ROLE)).equals("manager")){
            db.close();
            return 1;
        }else {
            db.close();
            return 0;
        }
    }

    private void displayTable(){
        System.out.println("display table");
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_USERNAME))!= null){
                System.out.println(c.getString(c.getColumnIndex(COLUMN_USERNAME)));
            }
            c.moveToNext();
        }
        db.close();
    }
}
