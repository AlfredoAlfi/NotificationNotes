package com.example.edu.notificationnotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Edu on 28/04/2017.
 */

public class BaseDeDatos extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Notas.db";

    public BaseDeDatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+Contrato.Notas.TABLA+" ("+
                Contrato.Notas._ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                Contrato.Notas.TITULO+" TEXT,"+
                Contrato.Notas.INFO+" TEXT,"+
                Contrato.Notas.ENCENDIDO+" INTEGER NOT NULL DEFAULT (0),"+
                Contrato.Notas.FECHA+" INTEGER NOT NULL DEFAULT (strftime('%s','now')))"
        );
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS "+Contrato.Notas.TABLA);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
