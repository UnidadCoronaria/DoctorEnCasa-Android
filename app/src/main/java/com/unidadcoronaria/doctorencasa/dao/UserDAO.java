package com.unidadcoronaria.doctorencasa.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.unidadcoronaria.doctorencasa.domain.User;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by AGUSTIN.BALA on 5/23/2017.
 */

@Dao
public interface UserDAO {

    @Insert(onConflict = REPLACE)
    void save(User user);
    @Query("SELECT * FROM user WHERE id = :userId")
    LiveData<User> load(int userId);
}
