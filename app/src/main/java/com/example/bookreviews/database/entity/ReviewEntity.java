package com.example.bookreviews.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;


//Todo put exclude if needed

@Entity(tableName = "reviews",
        foreignKeys =
        @ForeignKey(
                entity = BookEntity.class,
                parentColumns = "id",
                childColumns = "id_book",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(
                        value = {"id_book"}
                )}
)
public class ReviewEntity {

    private String id;
    private String id_book;
    private double grade;
    private String author;
    private String date;
    private String review;



    public ReviewEntity(@NonNull String id_book, double grade, String author, String date, String review){
        this.id_book = id_book;
        this.grade = grade;
        this.author = author;
        this.date = date;
        this.review = review;
    }

    public ReviewEntity(){}

    public String getId_book() {
        return id_book;
    }

    public void setId_book(String id_book) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) return false;
        if (obj == this) return true;
        if(!(obj instanceof  ReviewEntity)) return false;
        ReviewEntity o = (ReviewEntity) obj;
        return o.getAuthor().equals(this.getAuthor());
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id_book", id_book);
        result.put("grade", grade);
        result.put("author", author);
        result.put("date", date);
        result.put("review", review);

        return result;
    }
}
