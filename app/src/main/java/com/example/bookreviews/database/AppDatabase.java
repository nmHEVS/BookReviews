package com.example.bookreviews.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.bookreviews.database.dao.BookDao;
import com.example.bookreviews.database.dao.ReviewDao;
import com.example.bookreviews.database.entity.BookEntity;
import com.example.bookreviews.database.entity.ReviewEntity;

import java.util.concurrent.Executors;

@Database(entities = {BookEntity.class, ReviewEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";

    private static AppDatabase instance;

    private static final String DATABASE_NAME = "bookReview-database";

    public abstract BookDao bookDao();
    public abstract ReviewDao reviewDao();

    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context){
        if(instance == null){
            synchronized (AppDatabase.class){
                if(instance == null){
                    instance = buildDatabase(context.getApplicationContext());
                    instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private static AppDatabase buildDatabase(final Context appContext){

        Log.i(TAG, "Database will be initialized.");

        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)

                .addCallback(new Callback() {

                    @Override

                    public void onCreate(@NonNull SupportSQLiteDatabase db) {

                        super.onCreate(db);

                        Executors.newSingleThreadExecutor().execute(() -> {

                            AppDatabase database = AppDatabase.getInstance(appContext);

                            DatabaseInitializer.populateDatabase(database);

                            // notify that the database was created and it's ready to be used

                            database.setDatabaseCreated();

                        });

                    }

                }).build();

    }

    private void updateDatabaseCreated(final Context context){
        if(context.getDatabasePath(DATABASE_NAME).exists()){
            Log.i(TAG, "Database initialized.");
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        isDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated(){
        return isDatabaseCreated;
    }
}
