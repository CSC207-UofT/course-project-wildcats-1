package Interfaces;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

public class LocalDatabase {

    SQLiteDatabase db;

    public LocalDatabase() {
        db = SQLiteDatabase.openOrCreateDatabase("login", null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Login(Username VARCHAR, Password VARCHAR);");
    }

    public boolean loginInfoSaved() {
        if (DatabaseUtils.queryNumEntries(db, "Login") == 0) return false;
        else return true;
    }

    public void saveLoginInfo(String name, String password) {
        db.execSQL("INSERT INTO TutorialsPoint VALUES('" + name + "','" + password + "');");
    }

    public String[] getLoginInfo() {
        Cursor cursor = db.rawQuery("Select * from Login", null);
        cursor.moveToFirst();
        String[] loginInfo = new String[2];
        loginInfo[0] = cursor.getString(0);
        loginInfo[1] = cursor.getString(1);
        return loginInfo;
    }

}