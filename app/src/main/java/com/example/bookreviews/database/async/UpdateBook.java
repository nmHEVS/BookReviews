package com.example.bookreviews.database.async;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.example.bookreviews.database.AppDatabase;
import com.example.bookreviews.database.entity.BookEntity;
import com.example.bookreviews.util.OnAsyncEventListener;
import com.example.bookreviews.viewmodel.BaseApp;

public class UpdateBook extends AsyncTask<BookEntity, Void, Void> {

    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateBook(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }


    @Override
    protected Void doInBackground(BookEntity... params) {
        try {
            for (BookEntity book : params)
                database.bookDao()
                        .update(book);
        } catch (Exception e) {
            this.exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}
