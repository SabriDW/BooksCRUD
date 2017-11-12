package io.sabri.bookcrud;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static io.sabri.bookcrud.Data.BookContract.BookEntry.AUTHOR;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.ID;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.TITLE;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.URI;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.YEAR;

public class EditBookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    EditText title;
    EditText author;
    EditText year;
    Button editButton;
    Button deleteButton;

    long bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        year = findViewById(R.id.year);
        editButton = findViewById(R.id.edit_button);
        deleteButton = findViewById(R.id.delete_button);

        bookId = getIntent().getLongExtra("ID", -1);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(TITLE, title.getText().toString());
                values.put(AUTHOR, author.getText().toString());
                values.put(YEAR, year.getText().toString());

                String[] args = {String.valueOf(bookId)};

                getContentResolver()
                        .update(URI, values, ID + " = ?", args);
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] args = {String.valueOf(bookId)};

                getContentResolver()
                        .delete(URI, ID + " = ?", args);

                finish();
            }
        });

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.withAppendedPath(URI, String.valueOf(bookId));
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data.moveToNext()) {

            String bookTitle = data.getString(data.getColumnIndex(TITLE));
            String bookAuthor = data.getString(data.getColumnIndex(AUTHOR));
            int bookYear = data.getInt(data.getColumnIndex(YEAR));

            title.setText(bookTitle);
            author.setText(bookAuthor);
            year.setText(String.valueOf(bookYear));
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
