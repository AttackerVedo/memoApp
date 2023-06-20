package com.attackervedo.myapplication

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.attackervedo.myapplication.Adapter.TodoRecyclerViewAdapter
import com.attackervedo.myapplication.databinding.ActivityMainBinding
import com.attackervedo.myapplication.db.AppDatabase
import com.attackervedo.myapplication.db.Dao.TodoDao
import com.attackervedo.myapplication.db.Entitiy.TodoEntity
import kotlinx.coroutines.flow.combineTransform

class MainActivity : AppCompatActivity() ,OnItemLongClickListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var db : AppDatabase
    private lateinit var todoDao : TodoDao
    private lateinit var todoList: ArrayList<TodoEntity>
    private lateinit var adapter: TodoRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()
        getAllTodoList()

        binding.addBtn.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }


    }
    @SuppressLint("SuspiciousIndentation")
    private fun getAllTodoList(){
        Thread{
                todoList = ArrayList(todoDao.getAllTodo())
                setRecyclerView()
        }.start()
    }

    private fun setRecyclerView(){
        runOnUiThread{
            adapter = TodoRecyclerViewAdapter(todoList, this)
            binding.mainRecyclerView.adapter = adapter
            binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)

        }
    }

    override fun onRestart() {
        super.onRestart()
        getAllTodoList()
    }

    @SuppressLint("SuspiciousIndentation")
    override fun OnLongClick(position: Int) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alertTitle))
        builder.setMessage(getString(R.string.alertMessage))
        builder.setNegativeButton(getString(R.string.alertNo),null)
        builder.setPositiveButton(getString(R.string.alertYes),
            object :DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                 deleteTodo(position)
                }
            }
            )
        builder.show()

    }
    private fun deleteTodo(position: Int){
        Thread{
            todoDao.deleteTodo(todoList[position])
            todoList.removeAt(position)
        }.start()
        runOnUiThread {
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}