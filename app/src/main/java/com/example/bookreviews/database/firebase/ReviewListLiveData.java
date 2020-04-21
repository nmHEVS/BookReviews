package com.example.bookreviews.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.bookreviews.database.entity.ReviewEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewListLiveData extends LiveData<List<ReviewEntity>> {

    private static final String TAG = "ReviewListLiveData";

    private final DatabaseReference reference;
    private final String book;
    private final MyValueEventListener listener = new MyValueEventListener();

    public ReviewListLiveData(DatabaseReference ref, String book) {
        reference = ref;
        this.book = book;
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
            setValue(toReviews(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<ReviewEntity> toReviews(DataSnapshot snapshot) {
        List<ReviewEntity> reviews = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            //select only the reviews we want (corresponding to the book iD choosen)
            if(childSnapshot.getValue(ReviewEntity.class).getId_book().equals(book)){
                ReviewEntity entity = childSnapshot.getValue(ReviewEntity.class);
                entity.setId(childSnapshot.getKey());
                entity.setId_book(book);
                reviews.add(entity);
            }

        }
        return reviews;
    }
}
