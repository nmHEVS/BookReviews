package com.example.bookreviews.database.repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.bookreviews.database.AppDatabase;
import com.example.bookreviews.database.async.CreateBook;
import com.example.bookreviews.database.async.DeleteBook;
import com.example.bookreviews.database.async.UpdateBook;
import com.example.bookreviews.database.entity.BookEntity;
import com.example.bookreviews.util.OnAsyncEventListener;

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

    public LiveData<List<BookEntity>> getAllBooks(Context context){
        return AppDatabase.getInstance(context).bookDao().getAllBooks();
    }

    public LiveData<List<BookEntity>> getAllBooksByAuthor(final String author, Context context){
        return AppDatabase.getInstance(context).bookDao().getBooksByAuthor(author);
    }

    public LiveData<List<BookEntity>> getAllBooksByTitle(final String title, Context context){
        return AppDatabase.getInstance(context).bookDao().getBooksByTitle(title);
    }


    public void insert(final BookEntity book, OnAsyncEventListener callback, Application application){
        new CreateBook(application, callback).execute(book);
    }

    public void update(final BookEntity book, OnAsyncEventListener callback, Application application){
        new UpdateBook(application, callback).execute(book);
    }

    public void delete(final BookEntity book, OnAsyncEventListener callback, Application application){
        new DeleteBook(application, callback).execute(book);
    }


}
