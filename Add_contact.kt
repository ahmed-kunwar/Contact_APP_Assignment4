package com.example.assignment4

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_add_contact.*
import java.io.ByteArrayOutputStream
import java.util.jar.Manifest

class Add_contact : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        button2.setOnClickListener{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        val permisions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permisions, PERMISSION_CODE)
                    }
                else
                {
                    pickImageFromGallery()
                }
            }
            else
            {
                pickImageFromGallery()
            }
        }

        button3.setOnClickListener({
            val bitmap = (image.drawable as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

            val obj= stream.toByteArray()

            var contact = Contact(editText.text.toString(),editText1.text.toString(),editText4.text.toString().toInt(),editText5.text.toString())
            var databaseHandler: DatabaseHandler= DatabaseHandler(this)
            var status = databaseHandler.addContact(contact)
            var notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            lateinit var notificationChannel: NotificationChannel
            lateinit var builder: Notification.Builder
            val channelId="com.example.assignment4"
            val description = "Contact Status"
            val intent=Intent(this,MainActivity::class.java)
            val pendingintent=PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel= NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)
                builder = Notification.Builder(this, channelId)
                    .setContentTitle("Contacts")
                    .setContentText(contact.first+" "+ contact.last+"Added")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_launcher_foreground))
                    pendingintent.send()
            }
            else
            {

                builder = Notification.Builder(this)
                    .setContentTitle("Contacts")
                    .setContentText(contact.first+" "+ contact.last+"Added")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.ic_launcher_foreground))
                    pendingintent.send()

            }
            notificationManager.notify(1234,builder.build())

            //startActivity(intent)
        })


    }

    private fun pickImageFromGallery() {
       val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK&& requestCode == IMAGE_PICK_CODE)
        {
            image.setImageURI(data?.data)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode)
        {
            PERMISSION_CODE -> {
                if(grantResults.size> 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    pickImageFromGallery()
                }
                else
                {
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }






    companion object
    {
        private val IMAGE_PICK_CODE= 1000;
        private val PERMISSION_CODE = 1001
    }


}
