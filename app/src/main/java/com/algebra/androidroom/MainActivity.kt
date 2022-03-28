package com.algebra.androidroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.androidroom.database.AppDatabase
import com.algebra.androidroom.database.AppExecutors
import com.algebra.androidroom.model.TodoEntity
import com.algebra.androidroom.ui.TodosAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity( ) {

//  private lateinit var handler : Handler
    private val adapter = TodosAdapter( )
    private lateinit var db : AppDatabase

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_main )

//      handler = Handler( )
        db = AppDatabase( this )

        setupListeners( )
        rView.layoutManager = LinearLayoutManager( this )
        rView.adapter       = adapter

        db.todoDao( ).getAll( ).observe( this, Observer < List< TodoEntity > > {
            adapter.todos = it
        } )

/*
        Thread {
            adapter.todos = db.todoDao( ).getAll( )
        }.start( )
*/

    }

    private fun setupListeners( ) {
        bSave.setOnClickListener {
            val todo = readNewTodo( )
            if( todo!=null ) {
                AppExecutors.instance?.diskIO( )?.execute( Runnable {
                    db.todoDao( ).insert( todo )
/*
                    val listaTodo = db.todoDao( ).getAll( )
                    AppExecutors.instance?.mainThread( )?.execute( Runnable {
                        makeTodosTable( listaTodo )
                    } )

*/
                } )
                /*
                Thread {
                    db.todoDao( ).insert( todo )
                    val listaTodo = db.todoDao( ).getAll( )
                    handler.post( Runnable {
                        makeTodosTable( listaTodo )
                    } )
                }.start( )
               */
            }
        }

        bQuery.setOnClickListener {
            val title = etTitle.text.toString( ).trim( )
            val description = etDescription.text.toString( ).trim( )
            if( title=="" && description=="" )
                AppExecutors.instance?.diskIO( )?.execute( Runnable {
                    val poDesc = db.todoDao( ).getAllAsList( )
                    AppExecutors.instance?.mainThread( )?.execute( Runnable {
                        makeTodosTable( poDesc )
                    } )
                } )
            else if( title!="" && description!="" )
                Toast.makeText( this, "Only one field may be filled", Toast.LENGTH_SHORT ).show( )
            else {
                if( title=="" ) {
                    AppExecutors.instance?.diskIO( )?.execute( Runnable {
                        val poDesc = db.todoDao( ).getByDesc( description )
                        AppExecutors.instance?.mainThread( )?.execute( Runnable {
                            makeTodosTable( poDesc )
                        } )
                    } )
                } else {
                    AppExecutors.instance?.diskIO( )?.execute( Runnable  {
                        val poTitle = db.todoDao( ).getByTitle( title )
                        AppExecutors.instance?.mainThread( )?.execute( Runnable {
                            makeTodosTable( poTitle )
                        } )
                    } )
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
            AppExecutors.instance?.diskIO( )?.execute( Runnable {
                db.todoDao( ).deleteAll( )
/*
                val sve = db.todoDao( ).getAll( )
                AppExecutors.instance?.mainThread( )?.execute( Runnable {
                    makeTodosTable( sve )
                } )
*/
            } )
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