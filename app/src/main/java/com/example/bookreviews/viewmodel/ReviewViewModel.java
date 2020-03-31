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
import com.example.bookreviews.database.entity.ReviewEntity;
import com.example.bookreviews.database.repository.BookRepository;
import com.example.bookreviews.database.repository.ReviewRepository;
import com.example.bookreviews.util.OnAsyncEventListener;

public class ReviewViewModel extends AndroidViewModel {

    private ReviewRepository repository;

    private Application application;

    private final MediatorLiveData<ReviewEntity> observableReview;


    public ReviewViewModel(@NonNull Application application, final long id, final long id_book, final String author,
                         final String date, final double grade, String review_text, ReviewRepository reviewRepository){

        super(application);



        repository = reviewRepository;

        this.application = application;

        observableReview = new MediatorLiveData<>();
        observableReview.setValue(null);

        LiveData<ReviewEntity> review = repository.getReview(id_book,author, application);

        observableReview.addSource(review, observableReview::setValue);
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        @NonNull
        private final Application application;

        private final long id;
        private final long id_book;
        private final String author;
        private final String date;
        private final double grade;
        private final String review_text;

        private final ReviewRepository repository;

        public Factory(@NonNull Application application, long reviewId, final long reviewId_book,
                       final String reviewAuthor, final String reviewDate, final double reviewGrade,
                       final String reviewReview_text){

            this.application = application;
            this.id = reviewId;
            this.id_book = reviewId_book;
            this.author = reviewAuthor;
            this.date = reviewDate;
            this.grade = reviewGrade;
            this.review_text = reviewReview_text;
            repository = ReviewRepository.getInstance();
        }

        @Override public <T extends ViewModel> T create(Class<T> modelClass){
            return (T) new ReviewViewModel(application,id, id_book, author, date, grade, review_text, repository);
        }
    }

    public LiveData<ReviewEntity> getReview(){
        return observableReview;
    }

    public void createReview(ReviewEntity review, OnAsyncEventListener callBack){
        repository.insert(review, callBack, application);
    }

    public void updateReview(ReviewEntity review, OnAsyncEventListener callBack){
        repository.update(review, callBack, application);
    }

    public void deleteReview(ReviewEntity review, OnAsyncEventListener callBack){
        repository.delete(review, callBack, application);
    }

}
