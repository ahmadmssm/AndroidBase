package ams.android_base.utils;

import android.arch.persistence.db.SimpleSQLiteQuery;

public class RoomUtils {

    @SuppressWarnings("WeakerAccess")
    public static SimpleSQLiteQuery getQuery(String selectStatement) { return new SimpleSQLiteQuery(selectStatement); }

    public static SimpleSQLiteQuery selectAllQuery(String tableName) { return getQuery("SELECT * FROM" + tableName); }

    public static SimpleSQLiteQuery selectPageItemsQuery(String tableName, int itemsPerPage, int offset) {
        return getQuery("SELECT * FROM" + tableName + " LIMIT " + itemsPerPage + " OFFSET " + offset);
    }

    public static SimpleSQLiteQuery totalNumberOfRowsQuery(String tableName) {
        return getQuery("SELECT COUNT(*) FROM " + tableName);
    }

    public static SimpleSQLiteQuery totalNumberOfRowsQuery(String tableName, String condition) {
        return getQuery("SELECT COUNT(*) FROM " + tableName + condition);
    }

    public static SimpleSQLiteQuery deleteAllRowsQuery(String tableName) {
        return getQuery("DELETE FROM " + tableName);
    }

}
