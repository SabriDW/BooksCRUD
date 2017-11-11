package io.sabri.bookcrud;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static io.sabri.bookcrud.Data.BookContract.BookEntry.AUTHOR;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.TITLE;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.URI;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.YEAR;

public class AddBookActivity extends AppCompatActivity {

    EditText title;
    EditText author;
    EditText year;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        year = findViewById(R.id.year);

        addButton = findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues values = new ContentValues();
                values.put(TITLE, title.getText().toString());
                values.put(AUTHOR, author.getText().toString());
                values.put(YEAR, year.getText().toString());

                getContentResolver()
                        .insert(URI, values);

                finish();
            }
        });
    }
}
