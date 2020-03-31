package com.example.bookreviews.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookreviews.database.entity.BookEntity;
import com.example.bookreviews.database.repository.BookRepository;


import java.util.List;

public class BookListViewModel extends AndroidViewModel {

    private BookRepository repository;

    private Context applicationContext;

    private final MediatorLiveData<List<BookEntity>> observableBooks;

    public BookListViewModel(@NonNull Application application, BookRepository bookRepository){
        super(application);

        repository = bookRepository;

        applicationContext = application.getApplicationContext();

        observableBooks = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableBooks.setValue(null);

        LiveData<List<BookEntity>> books = repository.getAllBooks(applicationContext);

        //observe the changes of the entities from the database and forward them
        observableBooks.addSource(books, observableBooks::setValue);
    }

    public BookListViewModel(@NonNull Application application, BookRepository bookRepository, String author){
        super(application);

        repository = bookRepository;

        applicationContext = application.getApplicationContext();

        observableBooks = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableBooks.setValue(null);

        LiveData<List<BookEntity>> books = repository.getAllBooksByAuthor(author, applicationContext);

        //observe the changes of the entities from the database and forward them
        observableBooks.addSource(books, observableBooks::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        @NonNull
        private final Application application;

        private final BookRepository bookRepository;

        public Factory(@NonNull Application application){
            this.application = application;
            bookRepository = BookRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass){
            return (T) new BookListViewModel(application, bookRepository);
        }
    }

     //Expose the LiveData ClientEntities query so the UI can observe it.
    public LiveData<List<BookEntity>> getBooks(){
        return observableBooks;
    }
}
