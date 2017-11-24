package com.unidadcoronaria.doctorencasa.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.unidadcoronaria.doctorencasa.dao.UserDAO;
import com.unidadcoronaria.doctorencasa.database.converter.DateConverter;
import com.unidadcoronaria.doctorencasa.domain.Affiliate;

/**
 * Created by AGUSTIN.BALA on 5/23/2017.
 */

//@Database(entities = {Affiliate.class}, version = 1, exportSchema = false)
//@TypeConverters(DateConverter.class)
public abstract class DoctorEnCasaDB extends RoomDatabase {

    public static final String DATABASE_NAME = "database-name";

    public abstract UserDAO userDAO();
}
