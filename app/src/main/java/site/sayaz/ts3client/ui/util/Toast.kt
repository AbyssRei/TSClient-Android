package site.sayaz.ts3client.ui.util

import android.content.Context

fun toast(context: Context,string: String){
    android.widget.Toast.makeText(context,string,android.widget.Toast.LENGTH_SHORT).show()
}