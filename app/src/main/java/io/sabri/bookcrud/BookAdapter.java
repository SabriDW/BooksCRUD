package io.sabri.bookcrud;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import static io.sabri.bookcrud.Data.BookContract.BookEntry.AUTHOR;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.ID;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.TITLE;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.YEAR;

/**
 * Created by Sabri on 11/11/17.
 */

public class BookAdapter extends CursorAdapter {

    public BookAdapter(Context context) {
        super(context, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.item_book, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView id = view.findViewById(R.id.id);
        TextView title = view.findViewById(R.id.title);
        TextView author = view.findViewById(R.id.author);
        TextView year = view.findViewById(R.id.year);

        int bookId = cursor.getInt(cursor.getColumnIndex(ID));
        String bookTitle = cursor.getString(cursor.getColumnIndex(TITLE));
        String bookAuthor = cursor.getString(cursor.getColumnIndex(AUTHOR));
        int bookYear = cursor.getInt(cursor.getColumnIndex(YEAR));

        id.setText(String.valueOf(bookId));
        title.setText(bookTitle);
        author.setText(bookAuthor);
        year.setText(String.valueOf(bookYear));

    }
}
