package com.example.mycontacts

import retrofit2.Call
import retrofit2.http.GET


interface APIService {
    @GET("technical-challenge/v3/contacts.json")    //End Url
    fun fetchContacts(): Call<List<ContactDetails>>
}