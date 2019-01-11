package ams.android_base.baseClasses.roomDB;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface BaseDAO<T> {
    @Insert
    void insertAll(List<T> records);
    @Insert
    void insert(T record);
    @Insert(onConflict = REPLACE)
    void update(T record);
    @RawQuery
    List<T> getAll(SupportSQLiteQuery query);
    @RawQuery
    List<T> getPageItems(SupportSQLiteQuery query);
    @RawQuery
    int numberOfRows(SupportSQLiteQuery query);
    @RawQuery
    void deleteAllSections(SupportSQLiteQuery query);
}