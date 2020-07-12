package com.ams.androiddevkit.baseClasses.roomDB

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.lang.reflect.ParameterizedType

@Dao
@Suppress("unused")
interface BaseDAO<Entity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg item: Entity): Completable

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(vararg item: Entity): Completable

    @Delete
    fun delete(item: Entity)

    fun deleteAll(): Completable {
        val query = SimpleSQLiteQuery("delete from " + getTableName())
        return executeQueryWithResult(query).ignoreElement()
    }

    fun findItemWith(id: Long): Single<Entity> {
        val query = SimpleSQLiteQuery("select * from " + getTableName() + " where deleteFlag = 0 and id = ?", arrayOf<Any>(id))
        return executeQueryWithResult(query)
    }

    fun findAll(): Single<List<Entity>> {
        val query = SimpleSQLiteQuery("select * from " + getTableName() + " where deleteFlag = 0 order by sortKey")
        return executeQueryWithListResult(query)
    }

    @RawQuery
    fun executeQueryWithListResult(query: String): Single<List<Entity>> = executeQueryWithListResult(SimpleSQLiteQuery(query))

    @RawQuery
    fun executeQueryWithResult(query: String): Single<Entity> = executeQueryWithResult(SimpleSQLiteQuery(query))

    @RawQuery
    fun executeQueryWithListResult(query: SupportSQLiteQuery): Single<List<Entity>>

    @RawQuery
    fun executeQueryWithResult(query: SupportSQLiteQuery): Single<Entity>

    fun getTableName(): String {
        val clazz = (javaClass.superclass!!.genericSuperclass as ParameterizedType?)?.actualTypeArguments?.get(0) as Class<*>
        return clazz.simpleName
    }
}