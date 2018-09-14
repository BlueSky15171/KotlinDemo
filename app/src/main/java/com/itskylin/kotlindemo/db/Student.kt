package com.itskylin.kotlindemo.db

import android.arch.persistence.room.*

/**
 * @author Sky Lin
 * @version V1.0
 * @Package demo/com.itskylin.kotlindemo.db
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/9/14 10:05
 */
@Entity(tableName = "student",
        indices = [Index("id"), Index("t_name")]
)
data class Student(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        @ColumnInfo(name = "t_name")
        var name: String? = null
) {
    @Ignore
    constructor() : this(0)

    override fun toString(): String {
        return "Student(id=$id, name=$name)"
    }
}