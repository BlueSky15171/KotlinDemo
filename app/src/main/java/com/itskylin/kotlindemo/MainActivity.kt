@file:Suppress("SENSELESS_COMPARISON")

package com.itskylin.kotlindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.itskylin.kotlindemo.adapter.TestAdapter
import com.itskylin.kotlindemo.db.AppDatabase
import com.itskylin.kotlindemo.db.DBHelper
import com.itskylin.kotlindemo.db.Student
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), TestAdapter.OnItemClickListener {
    override fun onItemClick(view: View, item: Student, position: Int, id: Long) {
        mAdapter.remove(item)
    }

    lateinit var appDatabase: AppDatabase

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        recyclerView = findViewById(R.id.rvList)

        appDatabase = DBHelper.init(this, AppDatabase::class.java)
        initViews()
    }

    private lateinit var mAdapter: TestAdapter

    private fun initViews() {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        mAdapter = TestAdapter(this, appDatabase.studentDao.findAll())
        mAdapter.setOnItemClickListener(this)
        recyclerView.adapter = mAdapter

        val defaultAnim = DefaultItemAnimator()
        defaultAnim.moveDuration = (500 + Random().nextInt(500)).toLong()
        defaultAnim.changeDuration = (500 + Random().nextInt(500)).toLong()
        recyclerView.itemAnimator = defaultAnim

    }

    private fun insertDB() {
        val findLast = appDatabase.studentDao.findLast()
        val fid = findLast?.id ?: 0

        for (i in 1..10) {
            val id = fid + i
            appDatabase.studentDao.insert(Student(id, "sky.$i--$id"))
        }
        val findAll = appDatabase.studentDao.findAll()

        val json = JSON.toJSONString(findAll)
        Toast.makeText(this, json, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //kotlin
        return when (item.itemId) {
            R.id.action_settings -> {
                insertDB()
                true
            }
            R.id.action_add -> {
                mAdapter.add(Student(Random().nextInt(100000), "Sky${Random().nextInt(100000)}"))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}