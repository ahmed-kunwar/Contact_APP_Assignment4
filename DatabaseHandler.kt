package com.example.assignment4

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


class DatabaseHandler (var context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){
    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ContactsDatabase"
        private const val TABLE_CONTACTS = "ContactsTable"
        private val KEY_FNAME = "first"
        private val KEY_LNAME = "last"
        private val KEY_NUMBER = "phone"
        private val KEY_EMAIL = "email"
        
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_FNAME + " TEXT," + KEY_LNAME + " TEXT,"
                + KEY_NUMBER + " INTEGER," + KEY_EMAIL + " TEXT PRIMARY KEY" +
                ")")
        //KEY_IMAGE + " TEXT" +
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }



    //method to insert data
    fun addContact(contact: Contact):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_FNAME, contact.first) // Contact FName
        contentValues.put(KEY_LNAME, contact.last) // Contact LName
        contentValues.put(KEY_NUMBER, contact.phone ) // Contact Phone
        contentValues.put(KEY_EMAIL, contact.email ) // Contact Email
        // contentValues.put(KEY_IMAGE, contact.image ) // Contact Image
        // Inserting Row
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        if(success == -1.toLong())
        {
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context,"Inserted",Toast.LENGTH_SHORT).show()
        }
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to read data
    fun viewContact():ArrayList<Contact>{
        val contactList:ArrayList<Contact> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var fname: String
        var lname: String
        var number: Int
        var email: String
        //var image: ByteArray

        if (cursor.moveToFirst()) {
            do {
                fname = cursor.getString(cursor.getColumnIndex("fname"))
                Toast.makeText(context,fname,Toast.LENGTH_SHORT).show()
                lname = cursor.getString(cursor.getColumnIndex("lname"))
                number = cursor.getInt(cursor.getColumnIndex("number"))
                email = cursor.getString(cursor.getColumnIndex("email"))
                //image = cursor.getBlob(cursor.getColumnIndex("image"))
                val contact= Contact(fi = fname, la = lname, ph = number, em = email)
                contactList.add(contact)
            } while (cursor.moveToNext())
        }

        return contactList
    }


}