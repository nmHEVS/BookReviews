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
import com.example.bookreviews.util.OnAsyncEventListener;

public class BookViewModel extends AndroidViewModel {

    private BookRepository repository;

    private Application application;

    //
    private final MediatorLiveData<BookEntity> observableBook;

    public BookViewModel(@NonNull Application application, final long id,final String title,
                         final String author, final String edition, final String category,
                         final int yearPublished, final String plotSummary ,BookRepository bookRepository){           //,

        super(application);

        repository = bookRepository;

        this.application = application;

        observableBook = new MediatorLiveData<>();
        observableBook.setValue(null);

        LiveData<BookEntity> book = repository.getBook(id, application);

        observableBook.addSource(book, observableBook::setValue);
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        @NonNull
        private final Application application;

        private final long id;
        private final String title;
        private final String author;
        private final String edition;
        private final String category;
        private final int yearPublished;
        private final String plotSummary;

        private final BookRepository repository;

        public Factory(@NonNull Application application, long bookId, final String bookTitle, final String bookAuthor,
                       final String bookEdition, final String bookCategory, final int bookYearPublished,
                       final String bookPlotSummary, BookRepository repository){

            this.application = application;
            this.id = bookId;
            this.title = bookTitle;
            this.author = bookAuthor;
            this.edition = bookEdition;
            this.category = bookCategory;
            this.yearPublished = bookYearPublished;
            this.plotSummary = bookPlotSummary;
            this.repository = repository;
        }

        @Override public <T extends ViewModel> T create(Class<T> modelClass){
            return (T) new BookViewModel(application,id, title, author, edition, category, yearPublished, plotSummary, repository);
        }
    }
    public LiveData<BookEntity> getBook(){
        return observableBook;
    }

    public void createBook(BookEntity book, OnAsyncEventListener callBack){
        repository.insert(book, callBack, application);
    }

    public void updateBook(BookEntity book, OnAsyncEventListener callBack){
        repository.update(book, callBack, application);
    }

    public void deleteBook(BookEntity book, OnAsyncEventListener callBack){
        repository.delete(book, callBack, application);
    }

}
