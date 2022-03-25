package com.example.appevents.util

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.*

class Util {

    fun dateFormatter(date: Long): String {
        val c = Calendar.getInstance()
        c.timeInMillis = date

        return "${c[Calendar.DAY_OF_MONTH]}/${c[Calendar.MONTH]}/${c[Calendar.YEAR]}"
    }

    fun getEventLocation(latitude: Double, longitude: Double, context: Context): String {
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        return addresses[0].getAddressLine(0)
    }

    fun shareEvent(eventAddress: String, eventPrice: String, context: Context) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Veja só esse evento em $eventAddress por R$$eventPrice")
        sharingIntent.type = "text/plain"
        context.startActivity(Intent.createChooser(sharingIntent, "Compartilhar Evento"))
    }

    fun isEmailValid(email : String): Boolean {
        val emailSplit = email.split("@")

        return if(emailSplit.size >= 2) {
            email.isNotEmpty() && emailSplit[0].isNotEmpty() && emailSplit[1].isNotEmpty()
        } else {
            false
        }
    }

    fun isNameValid(name: String): Boolean {
        return name.isNotBlank() && name.isNotEmpty()
    }

    fun hideKeyboard(context: Context, view : View){
        val inputMethodManager: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }
}