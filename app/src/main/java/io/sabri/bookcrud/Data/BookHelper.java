package io.sabri.bookcrud.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static io.sabri.bookcrud.Data.BookContract.BookEntry.AUTHOR;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.ID;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.YEAR;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.TABLE_NAME;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.TITLE;
import static io.sabri.bookcrud.Data.BookContract.DATABASE;

/**
 * Created by Sabri on 11/8/17.
 */

public class BookHelper extends SQLiteOpenHelper{

    public BookHelper(Context context){
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY, " + TITLE + " TEXT, " + AUTHOR + " TEXT, " + YEAR + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
