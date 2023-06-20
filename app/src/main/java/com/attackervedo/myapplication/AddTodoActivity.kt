package com.attackervedo.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.attackervedo.myapplication.databinding.ActivityAddTodoBinding
import com.attackervedo.myapplication.db.AppDatabase
import com.attackervedo.myapplication.db.Dao.TodoDao
import com.attackervedo.myapplication.db.Entitiy.TodoEntity

class AddTodoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddTodoBinding
    lateinit var db :AppDatabase
    lateinit var todoDao:TodoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        binding.completeBtn.setOnClickListener {
            insertTodo()
        }
    }

    private fun insertTodo(){
        val todoTitle = binding.toDoEdt.text.toString()
        val todoImportance = binding.mainRadioGroup.checkedRadioButtonId
        var impData = ""
        when(todoImportance){
            R.id.importanceHighBtn ->{
                impData = "1Tier"
            }
            R.id.importanceMiddleBtn ->{
                impData = "2Tier"
            }
            R.id.importanceLowBtn ->{
                impData = "3Tier"
            }
        }

        if(impData == "" || todoTitle.isBlank()){
            Toast.makeText(this, "모든 항목을 채워 주세요.", Toast.LENGTH_SHORT).show()
        }else{
            Thread{
                todoDao.insertTodo(TodoEntity(null,todoTitle,impData))
                runOnUiThread{
                    Toast.makeText(this, "할 일이 추가 되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.start()
        }

    }
}