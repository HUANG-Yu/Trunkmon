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

    /**
     * This function is called when the database file did not exist and was just created.
     * @param db
     */
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

    /**
     * This function is called when the database file exists but the stored version number is lower
     * than requested in constructor
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    /**
     * This function is called by LoginActivity.
     * Get username and password, check if they are valid in local database.
     * If
     * @param username
     * @param password
     * @return If username and password doesn't match, return -1;
     *          If user is valid, return role of this user, 0 for visitor and 1 for manager.
     */
    public int checkUser(String username, String password){
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
}
