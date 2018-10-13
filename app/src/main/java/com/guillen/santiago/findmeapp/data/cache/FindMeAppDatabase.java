package com.guillen.santiago.findmeapp.data.cache;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.guillen.santiago.findmeapp.data.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class FindMeAppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    private static volatile FindMeAppDatabase INSTANCE;

    public static FindMeAppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FindMeAppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FindMeAppDatabase.class, "find_me_app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
