package com.example.prototype_1_group_12;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Each entity corresponds to a table that will be created in the database
// exportSchema for db migrations | True --> export the schema into a folder | False --> When don't want to keep history of versions (like an in-memory only database).

@Database(entities = {Routes.class, Points.class}, version = 1, exportSchema = false)
public abstract class RPDatabase extends RoomDatabase {

    public abstract RoutesDAO routesDAO();
    public abstract PointsDAO pointsDAO();

    // A singleton To prevent having multiple instances of the db
    private static volatile RPDatabase INSTANCE;
    private static final int THREADS = 4;

    // To run db operations tasks asynchronously
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(THREADS);

    // Creates database the first time it's accessed
    // Returns the singleton
    // Define the db name to rp_database
    static RPDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (RPDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RPDatabase.class, "rp_database").build();
                }
            }
        }
        return INSTANCE;
    }

}
