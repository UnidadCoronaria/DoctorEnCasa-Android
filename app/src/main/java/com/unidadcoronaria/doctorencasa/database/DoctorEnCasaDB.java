package com.unidadcoronaria.doctorencasa.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.unidadcoronaria.doctorencasa.dao.ProviderDAO;
import com.unidadcoronaria.doctorencasa.dao.UserDAO;
import com.unidadcoronaria.doctorencasa.database.converter.DateConverter;
import com.unidadcoronaria.doctorencasa.domain.Provider;
import com.unidadcoronaria.doctorencasa.domain.User;

/**
 * Created by AGUSTIN.BALA on 5/23/2017.
 */

@Database(entities = {User.class, Provider.class}, version = 6, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class DoctorEnCasaDB extends RoomDatabase {

    public static final String DATABASE_NAME = "database-name";

    public abstract UserDAO userDAO();
    public abstract ProviderDAO providerDAO();
}
