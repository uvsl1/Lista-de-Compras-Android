package com.example.listinha.ui.app.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class listinhaDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """CREATE TABLE IF NOT EXISTS listaCompras (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL
            );"""
        )

        db.execSQL(
            """CREATE TABLE IF NOT EXISTS produto (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                quantidade REAL NOT NULL,
                status INTEGER NOT NULL,
                precoUnitario REAL NOT NULL,
                precoTotal REAL NOT NULL,
                idListaCompras INTEGER NOT NULL,
                FOREIGN KEY(idListaCompras) REFERENCES listaCompras(id) ON DELETE CASCADE
            );"""
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS produto")
        db.execSQL("DROP TABLE IF EXISTS listaCompras")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "listinha.db"
        const val DATABASE_VERSION = 1
    }
}