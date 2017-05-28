package com.unidadcoronaria.doctorencasa.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.unidadcoronaria.doctorencasa.domain.Provider;
import com.unidadcoronaria.doctorencasa.domain.User;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by AGUSTIN.BALA on 5/23/2017.
 */

@Dao
public interface ProviderDAO {

    @Query("SELECT * FROM provider")
    LiveData<List<Provider>> loadAll();

    @Insert(onConflict = REPLACE)
    void save(List<Provider> provider);
}
