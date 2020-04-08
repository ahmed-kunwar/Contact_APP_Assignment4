package com.example.assignment4


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class adapter(var context: Context, var contacts:ArrayList<Contact>):BaseAdapter() {
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return contacts.count()

    }

    override fun getItem(position: Int): Any {
       return contacts.get(position)
    }

    private class ViewHolder(row : View?){
        var name:TextView
        //var lname:TextView = row?.findViewById(R.id.contact_text2) as TextView
        init {
            this.name=row?.findViewById(R.id.contact_text)as TextView
        }
    }





    override fun getView(position: Int, Convertview: View?, parent: ViewGroup): View {

        var view:View?
        var viewHolder:ViewHolder
        if(Convertview == null)
        {
            var layout = LayoutInflater.from(context)
            view=layout.inflate(R.layout.list_object,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }
        else
        {
            view= Convertview
            viewHolder=view.tag as ViewHolder
        }

        var contacts:Contact = getItem(position) as Contact
        viewHolder.name.text = contacts.first

        return view as View


    }

}