package com.example.bookreviews.database;

import com.example.bookreviews.database.entity.BookEntity;
import com.example.bookreviews.database.entity.ReviewEntity;

import java.util.List;

public class BookWithReviews {
    public BookEntity book;
    public List<ReviewEntity> reviews;
}
