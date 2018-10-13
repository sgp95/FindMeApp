package com.guillen.santiago.findmeapp.data.cache;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.guillen.santiago.findmeapp.data.model.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("DELETE FROM current_user")
    void logout();

    @Query("SELECT * FROM current_user")
    User getCurrentUser();
}
