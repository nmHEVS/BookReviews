package com.example.bookreviews.database.async;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.bookreviews.database.AppDatabase;
import com.example.bookreviews.database.entity.ReviewEntity;
import com.example.bookreviews.util.OnAsyncEventListener;
import com.example.bookreviews.viewmodel.BaseApp;

public class DeleteReview extends AsyncTask<ReviewEntity, Void, Void> {


    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteReview(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ReviewEntity... params) {
        try {
            for (ReviewEntity review : params)
                database.reviewDao()
                        .delete(review);
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
