package com.example.bookreviews.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.bookreviews.database.BookWithReviews;
import com.example.bookreviews.database.entity.BookEntity;
import com.example.bookreviews.database.entity.ReviewEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//TODO set value return, l. 48

public class BookReviewsListLiveData extends LiveData<List<ReviewEntity>> {
    private static final String TAG = "ReviewsLiveData";

    private final DatabaseReference reference;
    private final String title;
    private final BookReviewsListLiveData.MyValueEventListener listener =
            new BookReviewsListLiveData.MyValueEventListener();

    public BookReviewsListLiveData(DatabaseReference ref, String title) {
        reference = ref;
        this.title = title;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //setValue(toBookWithReviewsList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<BookWithReviews> toBookWithReviewsList(DataSnapshot snapshot) {
        List<BookWithReviews> bookWithReviewsList = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            if (!childSnapshot.getKey().equals(title)) {
                BookWithReviews bookWithReviews = new BookWithReviews();
                bookWithReviews.book = childSnapshot.getValue(BookEntity.class);
                bookWithReviews.book.setId(childSnapshot.getKey());
                bookWithReviews.reviews = toReviews(childSnapshot.child("reviews"),
                        childSnapshot.getKey());
                bookWithReviewsList.add(bookWithReviews);
            }
        }
        return bookWithReviewsList;
    }

    private List<ReviewEntity> toReviews(DataSnapshot snapshot, String bookId) {
        List<ReviewEntity> reviews = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            ReviewEntity entity = childSnapshot.getValue(ReviewEntity.class);
            entity.setId(childSnapshot.getKey());
            entity.setId_book(bookId);
            reviews.add(entity);
        }
        return reviews;
    }


}
