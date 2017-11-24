package com.unidadcoronaria.doctorencasa.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.unidadcoronaria.doctorencasa.domain.Affiliate;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by AGUSTIN.BALA on 5/23/2017.
 */

@Dao
public interface UserDAO {

    @Insert(onConflict = REPLACE)
    void save(Affiliate affiliate);

    @Query("SELECT * FROM user limit 1")
    Affiliate load();

    @Delete
    void delete(Affiliate affiliate);
}
