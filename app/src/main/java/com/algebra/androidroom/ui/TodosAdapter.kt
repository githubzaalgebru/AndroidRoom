package com.algebra.androidroom.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.algebra.androidroom.R
import com.algebra.androidroom.model.TodoEntity

class TodosAdapter : RecyclerView.Adapter< TodoViewHolder >( ) {

    var todos : List< TodoEntity > = mutableListOf( )
        set( value ) {
            field = value
            notifyDataSetChanged( )
        }

    override fun onCreateViewHolder( parent : ViewGroup, viewType : Int ) : TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater
                .from( parent.context )
                .inflate( R.layout.todo_item, parent, false )
        )
    }

    override fun onBindViewHolder( holder: TodoViewHolder, position : Int ) {
        val todo = todos[ position ]
        holder.tvTitle.text       = todo.title
        holder.tvDescription.text = todo.description

        holder
            .itemView
            .setBackgroundColor(
                Color.parseColor( if( position%2==0 ) "#FFCCCC" else "#CCCCFF" )
            )
    }

    override fun getItemCount( ) : Int = todos.size
}



class TodoViewHolder( view : View) : RecyclerView.ViewHolder( view ) {
    val tvTitle       : TextView = view.findViewById( R.id.tvTitle )
    val tvDescription : TextView = view.findViewById( R.id.tvDescription )
}