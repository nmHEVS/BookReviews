package com.example.bookreviews.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

public class BookEntity {

    private String id;
    private String title;
    private String author;
    private String edition;
    private String category;
    private int yearPublished;
    private String plotSummary;



    public BookEntity(@NonNull String title, String author, String  edition, String category, int yearPublished, String plotSummary) {
        this.title = title;
        this.author = author;
        this.edition = edition;
        this.category = category;
        this.yearPublished = yearPublished;
        this.plotSummary = plotSummary;
    }

    public BookEntity(){}


    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

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


    public boolean equals(Object obj){
        if (obj == null) return false;
        if (obj == this) return true;
        if(!(obj instanceof  BookEntity)) return false;
        BookEntity o = (BookEntity) obj;
        return o.getAuthor().equals(this.getAuthor());
    }

    @Override
    public String toString() {
        return title;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("author", author);
        result.put("edition", edition);
        result.put("category", category);
        result.put("yearPublished", yearPublished);
        result.put("plotSummary", plotSummary);

        return result;
    }
}
