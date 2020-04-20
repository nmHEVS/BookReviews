package com.example.bookreviews.database.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.bookreviews.database.entity.ReviewEntity;
import com.example.bookreviews.database.firebase.ReviewListLiveData;
import com.example.bookreviews.database.firebase.ReviewLiveData;
import com.example.bookreviews.util.OnAsyncEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.List;

public class ReviewRepository {

    private static ReviewRepository instance;

    private ReviewRepository() {}

    public static ReviewRepository getInstance(){
        if(instance == null){
            synchronized (BookRepository.class){
                if(instance == null){
                    instance = new ReviewRepository();
                }
            }
        }
        return instance;
    }



    public LiveData<List<ReviewEntity>> getAllReviewsForABook(final String book){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("books")
                .child(book)
                .child("reviews");
        return new ReviewListLiveData(reference, book);    }

    public LiveData<ReviewEntity> getReview(final String id){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("books")
                .child("reviews")
                .child(id);
        return new ReviewLiveData(reference);
    }



    public void insert(final ReviewEntity review, final OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("books")
                .child(review.getId_book())
                .child("reviews");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("books")
                .child(review.getId_book())
                .child("reviews")
                .child(key)
                .setValue(review, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final ReviewEntity review, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("reviews")
                .child(review.getId_book())
                .child("reviews")
                .child(review.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final ReviewEntity review, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("books")
                .child(review.getId_book())
                .child("reviews")
                .child(review.getId())
                .updateChildren(review.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void transaction(final ReviewEntity sender, final ReviewEntity recipient,
                            OnAsyncEventListener callback) {
        final DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference();
        rootReference.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                rootReference
                        .child("books")
                        .child(sender.getId_book())
                        .child("reviews")
                        .child(sender.getId_book())
                        .updateChildren(sender.toMap());

                rootReference
                        .child("books")
                        .child(recipient.getId_book())
                        .child("reviews")
                        .child(recipient.getId())
                        .updateChildren(recipient.toMap());

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                if (databaseError != null) {
                    callback.onFailure(databaseError.toException());
                } else {
                    callback.onSuccess();
                }
            }
        });
    }


}
