package com.example.bookreviews.database.repository;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;



import com.example.bookreviews.database.entity.BookEntity;
import com.example.bookreviews.database.firebase.BookListLiveData;
import com.example.bookreviews.database.firebase.BookLiveData;
import com.example.bookreviews.util.OnAsyncEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.List;

public class BookRepository {

    private static BookRepository instance;

    private BookRepository() {}

    public static BookRepository getInstance(){
        if(instance == null){
            synchronized (BookRepository.class){
                if(instance == null){
                    instance = new BookRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<BookEntity> getBook(final String id){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("books")
                .child(id);
        return new BookLiveData(reference);    }

    public LiveData<List<BookEntity>> getAllBooks(){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("books");
        return new BookListLiveData(reference);
    }




    public void insert(final BookEntity book, final OnAsyncEventListener callback) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("books")
                .child(book.getTitle())
                .child("reviews");
        String key = reference.push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("books")
                .child(book.getTitle())
                .child("reviews")
                .child(key)
                .setValue(book, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final BookEntity book, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("books")
                .child(book.getTitle())
                .child("reviews")
                .child(book.getId())
                .updateChildren(book.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final BookEntity book, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("books")
                .child(book.getTitle())
                .child("reviews")
                .child(book.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }




}
