package com.example.mycontacts

import java.io.Serializable

class ContactDetails : Serializable {

    val name: String? = null
    val id: String? = null
    val companyName: String? = null
    val smallImageURL: String? = null
    val largeImageURL: String? = null
    val emailAddress: String? = null
    val birthDate: String? = null
    val phone: Phone? = null
    val address: Address? = null
    var isFavorite : Boolean = false


}