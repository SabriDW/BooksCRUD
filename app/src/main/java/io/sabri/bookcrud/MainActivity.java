package io.sabri.bookcrud;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static io.sabri.bookcrud.Data.BookContract.BookEntry.AUTHOR;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.ID;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.TITLE;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.URI;
import static io.sabri.bookcrud.Data.BookContract.BookEntry.YEAR;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);

        // setting an on item click listener for the listView so we can open the EditActivity when an item is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // get the object from the adapter, and cast it into Book
                Book book = (Book) parent.getItemAtPosition(position);
                // create a new intent which targets that EditBookActivity
                Intent intent = new Intent(MainActivity.this, EditBookActivity.class);
                // pass the ID with the intent so the new activity can load the data using that ID
                intent.putExtra("ID", book.getId());
                // start the activity
                startActivity(intent);
            }
        });
    }

    // we use onStart to update the data when we go back from the
    // edit activity to show the new updated content
    @Override
    protected void onStart() {
        super.onStart();

        // load the cursor using the contentResolver and the ContentProvider
        Cursor cursor = getContentResolver().query(URI, null, null, null, null);

        // create an empty ArrayList to hold that data that will be passed to the ArrayAdapter
        ArrayList<Book> books = new ArrayList<>();

        // while loop to loop through all the rows in the cursor
        while (cursor.moveToNext()) {

            // get the data from the row
            int id = cursor.getInt(cursor.getColumnIndex(ID));
            String title = cursor.getString(cursor.getColumnIndex(TITLE));
            String author = cursor.getString(cursor.getColumnIndex(AUTHOR));
            int year = cursor.getInt(cursor.getColumnIndex(YEAR));

            // create a java object of these data
            Book book = new Book(id, title, author, year);

            // add the object to the ArrayList
            books.add(book);
        }

        // close the cursor after we're done with it
        cursor.close();

        // create the adapter
        BookAdapter adapter = new BookAdapter(this, R.layout.item_book, books);

        // set adapter to ListView
        listView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(this, AddBookActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
