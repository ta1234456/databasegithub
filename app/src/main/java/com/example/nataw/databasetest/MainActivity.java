package com.example.nataw.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FeedReaderDbHelper feedReaderDbHelper = new FeedReaderDbHelper(getBaseContext());

        textView = (TextView) findViewById(R.id.tv_id);

        updateDataInDatabase(feedReaderDbHelper);
        textView.setText(queryFromDatabase(feedReaderDbHelper));

        feedReaderDbHelper.close();


    }

    private void updateDataInDatabase(FeedReaderDbHelper feedReaderDbHelper) {
        SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TILE, "PAU");
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_TILE + " Like ?";
        String[] selectionArgs = {"test01"};
        db.update(FeedReaderContract.FeedEntry.TABLE_NAME, values, selection, selectionArgs);

    }


    private void deleteDataInDatabase(FeedReaderDbHelper feedReaderDbHelper) {
        String selection = FeedReaderContract.FeedEntry._ID + " = ?";
        String[] selectionArgs = {"2"};
        SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase();
        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }


    private String queryFromDatabase(FeedReaderDbHelper feedReaderDbHelper) {
        SQLiteDatabase db = feedReaderDbHelper.getReadableDatabase();
        String[] projection = {FeedReaderContract.FeedEntry._ID
                , FeedReaderContract.FeedEntry.COLUMN_NAME_TILE
                , FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE};
//        String selection = FeedReaderContract.FeedEntry.TABLE_NAME+" = ?";
//        String[]  selectionArgs={"My Title"};
        String sortOrder = FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";
        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder);

        if (!cursor.moveToFirst()) {
            cursor.close();
            return " ";
        }

        String _1stData = cursor.getInt(cursor.getColumnIndex(FeedReaderContract.FeedEntry._ID)) +
                ":" +
                cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TILE)) +
                ":" +
                cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE));

        cursor.close();
        return _1stData;

    }

    private long insertToDatabase(FeedReaderDbHelper feedReaderDbHelper) {
        SQLiteDatabase db = feedReaderDbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TILE, "test01");
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, "test01sub");

        return db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, contentValues);
    }


}
