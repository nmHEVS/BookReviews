package com.example.bookreviews.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reviews")
public class ReviewEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "id_book")
    private Long id_book;

    @ColumnInfo(name = "grade")
    private double grade;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "review")
    private String review;

    public ReviewEntity(@NonNull long id_book, double grade, String author, String date, String review){
        this.id_book = id_book;
        this.grade = grade;
        this.author = author;
        this.date = date;
        this.review = review;
    }

    public Long getId_book() {
        return id_book;
    }

    public void setId_book(Long id_book) {
        this.id_book = id_book;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Long getId() {
        return id;
    }
}
