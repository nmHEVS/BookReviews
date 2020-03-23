package com.example.bookreviews.database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookreviews.database.entity.BookEntity;

import java.util.List;

@Dao
public interface BookDao {

    //Select all the book's title
    @Query("SELECT title FROM books")
    LiveData<List<String>> getAllTitle();

    @Query("SELECT * FROM books WHERE id = :id")
    LiveData<BookEntity> getById(Long id);

    @Insert
    void insert(BookEntity book) throws SQLiteConstraintException;

    @Update
    void update(BookEntity book);

    @Delete
    void delete(BookEntity book);

    @Query("DELETE FROM books")
    void deleteAll();


}
