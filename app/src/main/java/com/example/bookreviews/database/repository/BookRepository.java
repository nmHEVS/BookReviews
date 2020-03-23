package com.example.bookreviews.database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.bookreviews.database.AppDatabase;
import com.example.bookreviews.database.entity.BookEntity;

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

    public LiveData<BookEntity> getBook(final long id, Context context){
        return AppDatabase.getInstance(context).bookDao().getById(id);
    }

    public LiveData<List<String>> getAllBooks(Context context){
        return AppDatabase.getInstance(context).bookDao().getAllTitle();
    }


}
