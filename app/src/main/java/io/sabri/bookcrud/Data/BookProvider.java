package io.sabri.bookcrud.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static io.sabri.bookcrud.Data.BookContract.AUTHORITY;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.AUTHOR;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.ID;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.TABLE_NAME;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.TITLE;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.YEAR;

/**
 * Created by Sabri on 11/8/17.
 */

public class BookProvider extends ContentProvider {

    // declare the Helper globally so it's accessed from any method inside this class
    BookHelper helper;

    // create a UriMatcher to know which URI was passed
    public static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    // add the possible Uris to the matcher
    static {
        matcher.addURI(AUTHORITY, TABLE_NAME, 1);
        matcher.addURI(AUTHORITY, TABLE_NAME + "/#", 2);
    }

    // we initialize the Helper
    @Override
    public boolean onCreate() {
        helper = new BookHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        // get the code of the Uri it matched so we can know whether it's a read all or read a specific field operation
        int code = matcher.match(uri);

        Cursor cursor = null;

        // the columns we want to select
        String[] columns = {ID, TITLE, AUTHOR, YEAR};

        // now we check whether it's read all (1) or read one (2)
        if (code == 1) {
            cursor = helper.getReadableDatabase().query(TABLE_NAME, columns, null, null, null, null, null);
            //  SELECT id, title, author, year FROM books
        } else if (code == 2) {

            // get the ID from the URI
            long id = ContentUris.parseId(uri);

            // create the WHERE arguments array
            String[] args = {String.valueOf(id)};

            cursor = helper.getReadableDatabase().query(TABLE_NAME, columns, ID + " = ?", args, null, null, null);
            // SELECT id, title, author, year FROM books WHERE id = #

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        helper.getWritableDatabase()
                .insert(TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        helper.getWritableDatabase()
                .delete(TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        helper.getWritableDatabase()
                .update(TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }
}
