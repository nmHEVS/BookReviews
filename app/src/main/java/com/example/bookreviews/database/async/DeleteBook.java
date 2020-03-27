package com.example.bookreviews.database.async;

import android.app.Application;
import android.os.AsyncTask;

import com.example.bookreviews.database.entity.BookEntity;
import com.example.bookreviews.util.OnAsyncEventListener;
import com.example.bookreviews.viewmodel.BaseApp;

import java.nio.channels.AsynchronousChannelGroup;

public class DeleteBook extends AsyncTask<BookEntity, Void, Void> {
    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteBook(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(BookEntity... params) {
        try {
            for (BookEntity book : params)
                ((BaseApp) application).getDatabase().bookDao()
                        .delete(book);
        } catch (Exception e) {
            exception = e;
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
