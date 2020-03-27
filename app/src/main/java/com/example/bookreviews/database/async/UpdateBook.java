package com.example.bookreviews.database.async;

import android.app.Application;
import android.os.AsyncTask;

import com.example.bookreviews.database.entity.BookEntity;
import com.example.bookreviews.util.OnAsyncEventListener;
import com.example.bookreviews.viewmodel.BaseApp;

public class UpdateBook extends AsyncTask<BookEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public UpdateBook(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }


    @Override
    protected Void doInBackground(BookEntity... params) {
        try {
            for (BookEntity book : params)
                ((BaseApp) application).getDatabase().bookDao()
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
