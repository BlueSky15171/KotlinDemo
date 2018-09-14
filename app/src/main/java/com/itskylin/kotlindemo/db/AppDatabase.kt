package com.itskylin.kotlindemo.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * @author Sky Lin
 * @version V1.0
 * @Package demo/com.itskylin.kotlindemo.db
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/9/14 09:22
 */
@Database(version = 1, entities = [Student::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val studentDao: StudentDao
}