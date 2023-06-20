package com.attackervedo.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.attackervedo.myapplication.db.Dao.TodoDao
import com.attackervedo.myapplication.db.Entitiy.TodoEntity

@Database(entities = arrayOf(TodoEntity::class), version = 1) // 조건 1
abstract class AppDatabase : RoomDatabase(){ //조건 2

    abstract fun getTodoDao(): TodoDao //조건3

    companion object{
        val databaseName = "memo_database" //데이터베이스 이름
        var appDatabase : AppDatabase? = null

        fun getInstance(context:Context) : AppDatabase?{
            if(appDatabase == null){
                appDatabase = Room.databaseBuilder(context,
                AppDatabase::class.java,
                databaseName).fallbackToDestructiveMigrationOnDowngrade().build()
            }
            return appDatabase
        }
    }
}