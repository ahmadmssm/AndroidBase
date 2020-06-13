package com.ams.androiddevkit.utils

import androidx.sqlite.db.SimpleSQLiteQuery

open class RoomUtils {

    open fun getQuery(selectStatement: String?): SimpleSQLiteQuery {
        return SimpleSQLiteQuery(selectStatement)
    }

    open fun selectAllQuery(tableName: String): SimpleSQLiteQuery {
        return getQuery("SELECT * FROM$tableName")
    }

    open fun selectPageItemsQuery(tableName: String, itemsPerPage: Int, offset: Int): SimpleSQLiteQuery {
        return getQuery("SELECT * FROM$tableName LIMIT $itemsPerPage OFFSET $offset")
    }

    open fun totalNumberOfRowsQuery(tableName: String): SimpleSQLiteQuery {
        return getQuery("SELECT COUNT(*) FROM $tableName")
    }

    open fun totalNumberOfRowsQuery(tableName: String, condition: String): SimpleSQLiteQuery {
        return getQuery("SELECT COUNT(*) FROM $tableName$condition")
    }

    open fun deleteAllRowsQuery(tableName: String): SimpleSQLiteQuery {
        return getQuery("DELETE FROM $tableName")
    }
}