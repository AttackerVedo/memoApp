package com.attackervedo.myapplication.db.Dao

import androidx.core.view.WindowInsetsCompat.Type.InsetsType
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.attackervedo.myapplication.db.Entitiy.TodoEntity

@Dao
interface TodoDao {
    // get All
    @Query("SELECT * FROM TodoEntity ORDER BY importance ASC")
    fun getAllTodo():List<TodoEntity>

    // insert
    @Insert
    fun insertTodo(todo: TodoEntity)
    // delete
    @Delete
    fun deleteTodo(todo: TodoEntity)
}