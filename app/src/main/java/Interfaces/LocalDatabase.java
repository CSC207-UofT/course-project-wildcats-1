package Interfaces;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

/**
 * Stores information locally on the user's device using a SQLite database.
 * Currently, it only stores user's login info but it could store other type of information.
 */
public class LocalDatabase {

    SQLiteDatabase db;

    public LocalDatabase(Activity activity) {
        db = activity.openOrCreateDatabase("login", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Login(Username VARCHAR, Password VARCHAR);");
        db.close();
    }

    /**
     * Check if the user login if has been previously saved locally.
     */
    public boolean loginInfoSaved() {
        if (DatabaseUtils.queryNumEntries(db, "login") == 0) {
            db.close();
            return false;
        }
        else return true;
    }

    /**
     * Save user's login info locally.
     * @param name user's name
     * @param password user's password
     */
    public void saveLoginInfo(String name, String password) {
        db.execSQL("INSERT INTO login VALUES('" + name + "','" + password + "');");
        db.close();
    }

    /**
     * Get user's login info a String array.
     * Index 0: name, index 1: password.
     */
    public String[] getLoginInfo() {
        Cursor cursor = db.rawQuery("SELECT * FROM login", null);
        cursor.moveToFirst();
        String[] loginInfo = new String[2];
        loginInfo[0] = cursor.getString(0);
        loginInfo[1] = cursor.getString(1);
        db.close();
        return loginInfo;
    }

}