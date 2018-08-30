package com.hyk.app.ipclearndemo.provider;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hyk.app.ipclearndemo.R;
import com.hyk.app.ipclearndemo.User;
import com.hyk.app.ipclearndemo.use_aidl.Book;

public class ProviderActivity extends Activity {
    private static final String TAG = "ProviderActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        Uri bookUri = Uri.parse("content://com.hyk.app.ipclearndemo.provider/book");

        ContentValues values = new ContentValues();
        values.put("_id", 6);
        values.put("name", "程序设计的艺术");
        getContentResolver().insert(bookUri, values);

        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"},
                null, null, null);
        while (bookCursor.moveToNext()) {
            Book book = new Book();
            book.bookId = bookCursor.getInt(0);
            book.bookName = bookCursor.getString(1);
            Log.d(TAG, "query book: " + book);
        }
        bookCursor.close();


        Uri userUri = Uri.parse("content://com.hyk.app.ipclearndemo.provider/user");


        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"},
                null, null, null);
        while (userCursor.moveToNext()) {
            User user = new User();
            user.age = userCursor.getInt(0);
            user.name = userCursor.getString(1);
            user.male = userCursor.getInt(2) == 1;
            Log.d(TAG, "query user: " + user);
        }
        userCursor.close();
    }


}
