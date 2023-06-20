package com.attackervedo.myapplication.Adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.attackervedo.myapplication.OnItemLongClickListener
import com.attackervedo.myapplication.R
import com.attackervedo.myapplication.databinding.ItemTodoBinding
import com.attackervedo.myapplication.db.Entitiy.TodoEntity

class TodoRecyclerViewAdapter(private val todoList: ArrayList<TodoEntity>,private val listener: OnItemLongClickListener): RecyclerView.Adapter<TodoRecyclerViewAdapter.ViewHolder>(){

    inner class ViewHolder(binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root){
        val itemImportanceTxt = binding.itemImportanceTxt
        val itemTitle = binding.itemTitleTxt
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    //화면 작업
        val binding: ItemTodoBinding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
    //아이템 개수
        return todoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    //데이터 설정
        val todoData = todoList[position]
        when(todoData.importance){
            "1Tier" -> holder.itemImportanceTxt.setBackgroundResource(R.color.red)
            "2Tier" -> holder.itemImportanceTxt.setBackgroundResource(R.color.yellow)
            "3Tier" -> holder.itemImportanceTxt.setBackgroundResource(R.color.green)

        }
        holder.itemImportanceTxt.text = todoData.importance
        holder.itemTitle.text = todoData.title
        holder.root.setOnLongClickListener {
            listener.OnLongClick(position)
            false
            // false 다른 클릭들도 실행이됨, true라고하면 오직 onLongClick 만 실행이됨
        }
    }
}