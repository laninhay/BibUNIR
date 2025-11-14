package com.unir.bib_unir.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bibunir";
    // 1. Incrementamos a versão para forçar a atualização (onUpgrade)
    private static final int DATABASE_VERSION = 2;

    // 2. Adicionamos o campo 'editora'
    private final String CREATE_TABLE_LIVRO = "CREATE TABLE livro (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "titulo TEXT," +
            "autor TEXT," +
            "editora TEXT," + // Novo campo
            "ano INTEGER);";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_LIVRO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Estratégia simples: apaga e cria de novo (CUIDADO: apaga dados existentes)
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS livro");
        onCreate(sqLiteDatabase);
    }
}