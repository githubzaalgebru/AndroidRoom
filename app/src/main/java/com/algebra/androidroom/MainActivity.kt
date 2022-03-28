package com.algebra.androidroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.androidroom.database.AppDatabase
import com.algebra.androidroom.model.TodoEntity
import com.algebra.androidroom.ui.TodosAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity( ) {

    private lateinit var handler : Handler
    private val adapter = TodosAdapter( )
    private lateinit var db : AppDatabase

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_main )

        handler = Handler( )
        db = AppDatabase( this )

        setupListeners( )
        rView.layoutManager = LinearLayoutManager( this )
        rView.adapter       = adapter
        Thread {
            adapter.todos = db.todoDao( ).getAll( )
        }.start( )


    }

    private fun setupListeners( ) {
        bSave.setOnClickListener {
            val todo = readNewTodo( )
            if( todo!=null ) {
                Thread {
                    db.todoDao( ).insert( todo )
                    val listaTodo = db.todoDao( ).getAll( )
                    handler.post( Runnable {
                        makeTodosTable( listaTodo )
                    } )
                }.start( )
            }
        }

        bQuery.setOnClickListener {
            val title = etTitle.text.toString( ).trim( )
            val description = etDescription.text.toString( ).trim( )
            if( title=="" && description=="" )
                makeTodosTable( db.todoDao( ).getAll( ) )
            else if( title!="" && description!="" )
                Toast.makeText( this, "Only one field may be filled", Toast.LENGTH_SHORT ).show( )
            else {
                if( title=="" ) {
                    Thread {
                        val poDesc = db.todoDao( ).getByDesc( description )
                        handler.post( Runnable {
                            makeTodosTable( poDesc )
                        } )
                    }.start( )
                } else {
                    Thread {
                        val poTitle = db.todoDao( ).getByTitle( title )
                        handler.post( Runnable {
                            makeTodosTable( poTitle )
                        } )
                    }.start( )
                }
            }
        }
    }

    override fun onCreateOptionsMenu( menu: Menu? ): Boolean {
        menuInflater.inflate( R.menu.menu, menu )
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected( item : MenuItem ) : Boolean {
        if( item.itemId==R.id.deleteAll ) {
            Thread {
                db.todoDao( ).deleteAll( )
                val sve = db.todoDao( ).getAll( )
                handler.post( Runnable {
                    makeTodosTable( sve )
                } )
            }.start( )
        }
        return true
    }

    private fun readNewTodo( ) : TodoEntity? {
        val title       = etTitle.text.toString( ).trim( )
        val description = etDescription.text.toString( ).trim( )
        if( title=="" )
            etTitle.error = "Title is missing"
        if( description=="" )
            etDescription.error = "Description is missing"
        if( title=="" || description=="" ) {
            Toast.makeText(this, "Both fields must be filled", Toast.LENGTH_SHORT).show()
            return null
        }
        return TodoEntity( 0, title, description )
    }

    private fun makeTodosTable( todos : List< TodoEntity > ) {
        adapter.todos = todos
        etTitle.setText( "" )
        etDescription.setText( "" )
        etTitle.requestFocus( )
    }
}