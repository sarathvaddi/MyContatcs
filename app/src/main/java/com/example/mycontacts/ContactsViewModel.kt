package com.example.mycontacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactsViewModel() : ViewModel() {

    var sortedList : List<ContactDetails> = emptyList()
    val command = MutableLiveData<UpdateCommand>()


    fun onFavoriteClicked(isFavorite :Boolean, position : Int) {
        ContactsApplication.globalList.find { it.id == ContactsApplication.globalList[position].id  }?.isFavorite = isFavorite
        prepareFavoriteList(ContactsApplication.globalList)
        command.value = UpdateCommand.updateList
    }

    fun onFavoriteClickedFromDetail(isFavorite :Boolean, position : Int) {
        ContactsApplication.globalList.find { it.id == ContactsApplication.globalList[position].id  }?.isFavorite = isFavorite
        //prepareFavoriteList(ContactsApplication.globalList)
        //command.value = UpdateCommand.updateList
        command.value = UpdateCommand.updateDetailFav(isFavorite)
    }

    fun onItemClicked(position: Int){
        command.value = UpdateCommand.NavigateToDetailsScreen(position)
    }

    fun prepareFavoriteList(contacts: List<ContactDetails>) {
          ContactsApplication.globalList = contacts.sortedBy { !it.isFavorite }
    }
}