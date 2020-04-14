package com.example.bookreviews.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.bookreviews.database.entity.BookEntity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookListLiveData extends LiveData<List<BookEntity>> {

    private static final String TAG = "AccountListLiveData";

    private final DatabaseReference reference;
    private final String title;
    private final MyValueEventListener listener = new MyValueEventListener();

    public BookListLiveData(DatabaseReference ref, String title) {
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
            setValue(toBooks(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<BookEntity> toBooks(DataSnapshot snapshot) {
        List<BookEntity> accounts = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            BookEntity entity = childSnapshot.getValue(BookEntity.class);
            entity.setId(childSnapshot.getKey());
            entity.setTitle(title);
            accounts.add(entity);
        }
        return accounts;
    }

}
