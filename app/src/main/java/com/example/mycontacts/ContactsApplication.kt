package com.example.mycontacts

import android.app.Application

class ContactsApplication : Application() {
    companion object {
        lateinit var  globalList :List<ContactDetails>
        var firstTime : Boolean = true
    }





}