package com.itskylin.kotlindemo.db

import android.arch.persistence.room.*

/**
 * @author Sky Lin
 * @version V1.0
 * @Package demo/com.itskylin.kotlindemo.db
 * @Description:
 * @email admin@itksylin.com
 * @date 2018/9/14 10:07
 */
@Dao
interface StudentDao {
    @Query("select * from student order by id desc")
    fun findAll(): MutableList<Student>

    @Insert
    fun insert(vararg stus: Student)

    @Update
    fun update(stu: Student)


    @Delete
    fun delete(stu: Student)

    @Query("select * from student where id in (:ids)")
    fun findByIds(vararg ids: Int): MutableList<Student>

    @Query("select * from student where id=:id")
    fun findById(id: Int): Student


    @Query("select * from student order by student.id desc limit 1")
    fun findLast(): Student?
}

