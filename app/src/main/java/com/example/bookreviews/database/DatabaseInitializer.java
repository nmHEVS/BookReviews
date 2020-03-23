package com.example.bookreviews.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bookreviews.database.entity.BookEntity;
import com.example.bookreviews.database.entity.ReviewEntity;

public class DatabaseInitializer {
    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Insterting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addBook(final AppDatabase db, final String title, final String author,
                                final String edition, final String category,
                                final int yearPublished, final String plotSummary) {
        BookEntity book = new BookEntity(title, author, edition, category, yearPublished, plotSummary);
        db.bookDao().insert(book);
    }

    private static void addReview(final AppDatabase db, final long id_book, final double grade,
                                  final String author, final String date, final String review){
        ReviewEntity reviewEntity = new ReviewEntity(id_book, grade, author, date, review);
        db.reviewDao().insert(reviewEntity);

    }

    private static void populateWithTestData(AppDatabase db){
        db.bookDao().deleteAll();
        db.reviewDao().deleteAll();

        addBook(db, "Fahrenheit 451", "Ray Bradbury", "Ballantine Books",
                "Dystopian", 1953,
                "Fahrenheit 451 is set in an unspecified city (likely in the American" +
                        " Midwest) in the year 1999 (according to Ray Bradbury's Coda), though it "+
                        "is written as if set in a distant future. The earliest editions " +
                        "make clear that it takes place no earlier than the year 1960.\n" +
                        "The novel is divided into three parts: \"The Hearth and the Salamander\", " +
                        "\"The Sieve and the Sand\", and \"Burning Bright\". ");

        addReview(db, 1, 4.5, "Toto", "23.03.2020", "C'est OK");
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db){
            database = db;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            populateWithTestData(database);
            return null;
        }
    }
}
