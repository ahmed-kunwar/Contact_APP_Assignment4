package com.example.assignment4

import java.io.Serializable

class Contact(fi:String, la:String, ph: Int, em:String):Serializable
{
    var first:String? = null
    var last:String? = null
    var phone: Int? = null
    var email:String? =null


    init {
        first = fi
        last=la
        phone=ph
        email=em
    }
}
