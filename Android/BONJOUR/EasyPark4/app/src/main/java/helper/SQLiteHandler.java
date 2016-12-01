package helper;

/**
 * Created by nixos on 29/11/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.StringTokenizer;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "easy_park";

    // Login table name
    private static final String TABLE_USER = "user";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NOM = "nom";
    private static final String KEY_PRENOM = "prenom";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_DATE_CREATE = "date_creation";
    private static final String KEY_DATE_NAISSANCE = "date_naissance";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOM + " TEXT,"  + KEY_PRENOM + " TEXT,"
                + KEY_LOGIN + " TEXT," + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
                + KEY_DATE_CREATE + " TEXT" + KEY_DATE_NAISSANCE + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String nom, String prenom, String login, String email, String uid, String date_creation, String date_naissance) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOM, nom); // Name
        values.put(KEY_PRENOM, prenom); // Name
        values.put(KEY_LOGIN, login); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_DATE_CREATE, date_creation); // Created At
        values.put(KEY_DATE_NAISSANCE, date_naissance); // Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("nom", cursor.getString(1));
            user.put("prenom", cursor.getString(1));
            user.put("login", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("date_creation", cursor.getString(4));
            user.put("date_naissance", cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}