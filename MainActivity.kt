package com.example.assignment4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable


class MainActivity : AppCompatActivity() {
    private lateinit var contact: List<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        var cont = Contact("Ahmed","Kunwar",123,"ahmed@gmail.com")
        var databaseHandler: DatabaseHandler= DatabaseHandler(this)
        var status = databaseHandler.addContact(cont)
        var conta = Contact("MAhmed","Kunwar",123,"ahmed@gmail.com")

        databaseHandler.addContact(conta)

        val listView = findViewById<ListView>(R.id.view)
        viewcontacts()
        flb.setOnClickListener { val intent=Intent(this,Add_contact::class.java)
            startActivity(intent)}

        listView.setOnItemClickListener { _, _, position, _ ->

            val intent = Intent(applicationContext,Contact_info::class.java)
            intent.putExtra("Object1",contact[position] as Serializable)
            startActivity(intent)
        }

    }


    //method for read records from database in ListView
    private fun viewcontacts(){

        val databaseHandler: DatabaseHandler= DatabaseHandler(this)
        contact = databaseHandler.viewContact()
        val listView = findViewById<ListView>(R.id.view)

        val listadp = adapter(this,contact as ArrayList<Contact>)

        listView.adapter = listadp
    }
}
