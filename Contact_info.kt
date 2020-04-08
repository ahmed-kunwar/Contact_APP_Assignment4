package com.example.assignment4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_contact_info.*

class Contact_info : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_info)

        var contact:Contact = intent.getSerializableExtra("Object1") as Contact
        this.text1.text=contact.first + " "+ contact.last
        this.text2.text=contact.phone.toString()
        this.text3.text=contact.email

    }
}
