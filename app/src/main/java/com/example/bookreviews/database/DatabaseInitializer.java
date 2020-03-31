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

        //poopulate books
        addBook(db, "Fahrenheit 451", "Ray Bradbury", "Ballantine Books",
                "Dystopian", 1953,
                "Fahrenheit 451 is set in an unspecified city \n (likely in the American" +
                        " Midwest) in the year 1999 (according to Ray Bradbury's Coda), though it "+
                        "is written as if set in a distant future. The earliest editions " +
                        "make clear that it takes place no earlier than the year 1960.\n" +
                        "The novel is divided into three parts: \"The Hearth and the Salamander\", " +
                        "\"The Sieve and the Sand\", and \"Burning Bright\". ");

        addBook(db, "The Old Man and the Sea", "Ernest Hemingway", "Charles Scribner's Sons",
                "Adventure", 1952,
                "The Old Man and the Sea tells the story of a battle between an aging, experienced fisherman, Santiago, and a large marlin. The story opens with Santiago having gone 84 days without catching a fish, and now being seen as salao, the worst form of unluckiness. He is so unlucky that his young apprentice, Manolin, has been forbidden by his parents to sail with him and has been told instead to fish with successful fishermen. The boy visits Santiago's shack each night, hauling his fishing gear, preparing food, talking about American baseball and his favorite player, Joe DiMaggio. Santiago tells Manolin that on the next day, he will venture far out into the Gulf Stream, north of Cuba in the Straits of Florida to fish, confident that his unlucky streak is near its end.");

        addBook(db, "Moby Dick", "Hermann Melville", "Henry Bentley",
                "Adventure", 1851,
                "Ishmael travels in December from Manhattan Island to New Bedford, Massachusetts with plans to sign up for a whaling voyage. The inn where he arrives is overcrowded, so he must share a bed with the tattooed cannibal Polynesian Queequeg, a harpooneer whose father was king of the fictional island of Rokovoko. The next morning, Ishmael and Queequeg attend Father Mapple's sermon on Jonah, then head for Nantucket. Ishmael signs up with the Quaker ship-owners Bildad and Peleg for a voyage on their whaler Pequod. Peleg describes Captain Ahab: He's a grand, ungodly, god-like man who nevertheless has his humanities. They hire Queequeg the following morning. A man named Elijah prophesies a dire fate should Ishmael and Queequeg join Ahab. While provisions are loaded, shadowy figures board the ship. On a cold Christmas Day, the Pequod leaves the harbor.");


        //populate reviews
        addReview(db, 1, 4, "Toto", "23.03.2020", "C'est OK");
        addReview(db, 1, 5, "chris666", "22.03.2020", "C'est bien");
        addReview(db, 2, 4, "Toto", "24.03.2020", "C'est pas mal");
        addReview(db, 2, 4, "chris666", "23.03.2020", "C'est nul");
        addReview(db, 3, 4, "Toto", "21.03.2020", "C'est top");
        addReview(db, 3, 4, "chris666", "20.03.2020", "C'est OK");

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
