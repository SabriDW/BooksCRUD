package io.sabri.bookcrud;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static io.sabri.bookcrud.Data.BookContract.BookEntry.AUTHOR;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.ID;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.TITLE;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.URI;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.YEAR;

public class EditBookActivity extends AppCompatActivity {

    EditText title;
    EditText author;
    EditText year;
    Button editButton;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        year = findViewById(R.id.year);
        editButton = findViewById(R.id.edit_button);
        deleteButton = findViewById(R.id.delete_button);

        final int id = getIntent().getIntExtra("ID", -1);

//        Uri uri = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME + "/" + id);
        Uri uri = Uri.withAppendedPath(URI, String.valueOf(id));
        // content://io.sabri.bookcrud/books/2


        Cursor cursor = getContentResolver()
                .query(uri, null, null, null, null);

        cursor.moveToNext();

        String bookTitle = cursor.getString(cursor.getColumnIndex(TITLE));
        String bookAuthor = cursor.getString(cursor.getColumnIndex(AUTHOR));
        int bookYear = cursor.getInt(cursor.getColumnIndex(YEAR));

        title.setText(bookTitle);
        author.setText(bookAuthor);
        year.setText(String.valueOf(bookYear));


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(TITLE, title.getText().toString());
                values.put(AUTHOR, author.getText().toString());
                values.put(YEAR, year.getText().toString());

                String[] args = {String.valueOf(id)};

                getContentResolver()
                        .update(URI, values, ID + " = ?", args);
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] args = {String.valueOf(id)};

                getContentResolver()
                        .delete(URI, ID + " = ?", args);
                finish();
            }
        });
    }
}
