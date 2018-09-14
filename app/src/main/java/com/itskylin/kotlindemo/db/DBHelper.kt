package com.itskylin.kotlindemo.db

import android.annotation.SuppressLint
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log

/**
 * @author Sky Lin
 * @version V1.0
 * @Package demo/com.itskylin.kotlindemo.db
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/9/14 13:37
 */

@SuppressLint("StaticFieldLeak")
class DBHelper private constructor() {
    companion object {

        private val DATABASE_NAME: String = "test.db"

         fun <T : RoomDatabase> init(context: Context, clazz: Class<T>): T {
            return Room.databaseBuilder(context, clazz, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.i("db-log", "first create database")
                        }
                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            Log.i("db-log", "database status is ${db.isOpen}")
                        }
                    })
                    .build()
        }
    }


}