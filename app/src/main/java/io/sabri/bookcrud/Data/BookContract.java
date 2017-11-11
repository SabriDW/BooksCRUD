package io.sabri.bookcrud.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Sabri on 11/8/17.
 */

public final class BookContract {

    public static final String AUTHORITY = "io.sabri.bookcrud";
    public static final String DATABASE = "books.db";

    public static final class BookEntry implements BaseColumns {

        public static final String TABLE_NAME = "books";

        public static final String ID = BaseColumns._ID;
        public static final String TITLE = "title";
        public static final String AUTHOR = "author";
        public static final String YEAR = "year";

        public static final Uri URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
    }

}
