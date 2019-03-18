package com.example.kevin.comp3074_project_resturant_guide.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.kevin.comp3074_project_resturant_guide.model.Restaurant;

@Database(entities = {Restaurant.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract RestaurantDao restaurantDao();




    //Singleton AppDatabase class, if instance is null,initialize it
    public static AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    //Use this to dereference the database object within the Singleton, to clean up references and avoid leaks
    public static void destroyInstance(){
        instance = null;
    }

}
