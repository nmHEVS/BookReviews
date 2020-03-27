package com.example.bookreviews.database.async;

import android.app.Application;
import android.os.AsyncTask;

import com.example.bookreviews.database.entity.ReviewEntity;
import com.example.bookreviews.util.OnAsyncEventListener;
import com.example.bookreviews.viewmodel.BaseApp;

public class CreateReview extends AsyncTask<ReviewEntity, Void, Void> {

    private Application application;
    private OnAsyncEventListener callback;
    private Exception exception;

    public CreateReview(Application application, OnAsyncEventListener callback) {
        this.application = application;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(ReviewEntity... params) {
        try {
            for (ReviewEntity review : params)
                ((BaseApp) application).getDatabase().reviewDao()
                        .insert(review);
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
