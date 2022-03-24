package com.algebra.androidroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.algebra.androidroom.model.TodoEntity
import com.algebra.androidroom.ui.TodosAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity( ) {

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_main )

        setupListeners( )
        rView.layoutManager = LinearLayoutManager( this )
        rView.adapter       = TodosAdapter( )
        ( rView.adapter as TodosAdapter ).todos = mockList( )
    }

    private fun setupListeners( ) {
        bSave.setOnClickListener {
            val todo = readNewTodo( )
            if( todo!=null ) {
                Toast
                    .makeText( this, "Todo $todo loaded", Toast.LENGTH_SHORT )
                    .show( )
            }
        }
    }

    override fun onCreateOptionsMenu( menu: Menu? ): Boolean {
        menuInflater.inflate( R.menu.menu, menu )
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected( item : MenuItem ) : Boolean {
        if( item.itemId==R.id.deleteAll ) {
            Toast.makeText( this, "Brisanje svega...", Toast.LENGTH_SHORT ).show( )
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

    private fun mockList( ) : List< TodoEntity > {
        val lista = mutableListOf< TodoEntity >( )
        lista.add( TodoEntity( 1,  "Zajutrak", "Vrlo rano" ))
        lista.add( TodoEntity( 2,  "Doručak", "Oko 8" ) )
        lista.add( TodoEntity( 3,  "Ručak", "U podne" ) )
        lista.add( TodoEntity( 4,  "Kava", "Prije doručka" ) )
        lista.add( TodoEntity( 5,  "Večera", "Oko 19" ) )
        lista.add( TodoEntity( 6,  "Odmor", "Iza ručka" ) )
        lista.add( TodoEntity( 7,  "Posao", "Od 8:30 do 16:30" ) )
        lista.add( TodoEntity( 8,  "Pauza", "U 12" ) )
        lista.add( TodoEntity( 9,  "Igra", "U 17:00" ) )
        lista.add( TodoEntity( 10, "Spavanje", "22:00" ) )
        return lista
    }
}