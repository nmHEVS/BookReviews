package com.example.bookreviews.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class BookEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "edition")
    private String edition;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "yearPublished")
    private int yearPublished;

    @ColumnInfo(name = "plotSummary")
    private String plotSummary;

    public BookEntity(@NonNull String title, String author, String edition, String category, int yearPublished, String plotSummary){
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.category = category;
        this.yearPublished = yearPublished;
        this.plotSummary = plotSummary;
    }

    public Long getId(){return id;}


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getPlotSummary() {
        return plotSummary;
    }

    public void setPlotSummary(String plotSummary) {
        this.plotSummary = plotSummary;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object obj){
        if (obj == null) return false;
        if (obj == this) return true;
        if(!(obj instanceof  BookEntity)) return false;
        BookEntity o = (BookEntity) obj;
        return o.getAuthor().equals(this.getAuthor());
    }
}
