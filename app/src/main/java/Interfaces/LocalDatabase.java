package Interfaces;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class LocalDatabase {

    public LocalDatabase() {

        SQLiteDatabase mydatabase = SQLiteDatabase.openOrCreateDatabase("login", null);

    }

    public boolean loginInfoSaved() {

        return false;
    }

    public void saveLoginInfo(String name, String password) {

    }

    public String[] getLoginInfo() {
        String[] loginInfo = new String[2];
        loginInfo[0] = "";
        loginInfo[1] = "";
        return loginInfo;
    }

}