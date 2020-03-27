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

import java.util.List;

public class ReviewListViewModel extends AndroidViewModel {

    private Application application;
    private ReviewRepository repository;


    private Context applicationContext;

    private final MediatorLiveData<List<ReviewEntity>> observableReviews;

    public ReviewListViewModel(@NonNull Application application,long id, ReviewRepository reviewRepository){
        super(application);

        repository = reviewRepository;

        applicationContext = application.getApplicationContext();


        observableReviews = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableReviews.setValue(null);

        LiveData<List<ReviewEntity>> reviews = repository.getAllReviewsForABook(id, applicationContext);

        //observe the changes of the entities from the database and forward them
        observableReviews.addSource(reviews, observableReviews::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        @NonNull
        private final Application application;

        private final ReviewRepository reviewRepository;

        public Factory(@NonNull Application application){
            this.application = application;
            reviewRepository = ReviewRepository.getInstance();
        }

        public <T extends ViewModel> T create(Class<T> modelClass, long id){
            return (T) new ReviewListViewModel(application, id, reviewRepository);
        }
    }

    //Expose the LiveData ClientEntities query so the UI can observe it.
    public LiveData<List<ReviewEntity>> getBooks(){
        return observableReviews;
    }
}
