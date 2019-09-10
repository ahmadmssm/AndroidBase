package com.ams.androiddevkit.baseClasses.roomDB;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import static androidx.room.OnConflictStrategy.REPLACE;

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