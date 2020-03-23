package com.example.bookreviews.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookreviews.database.entity.ReviewEntity;

import java.util.List;

@Dao
public interface ReviewDao {

    //Get all author of reviews for one single book
    @Query("SELECT author FROM reviews WHERE id_book = :id_book")
    LiveData<List<ReviewEntity>> getByIdBook(long id_book);

    @Query("SELECT * FROM reviews WHERE id_book = :id_book AND author = :author")
    LiveData<ReviewEntity> getReview(long id_book, String author);

    @Query("SELECT avg(grade) FROM reviews WHERE id_book = :id_book")
    LiveData<ReviewEntity> getAvgGrade(long id_book);

    @Insert
    void insert(ReviewEntity review) throws SQLiteConstraintException;

    @Update
    void update(ReviewEntity review);

    @Delete
    void delete(ReviewEntity review);

    @Query("DELETE FROM reviews")
    void deleteAll();

}
