package com.example.mycontacts

sealed class UpdateCommand {
    object updateList : UpdateCommand()
    data class updateDetailFav(val isFavorite : Boolean) : UpdateCommand()
    data class NavigateToDetailsScreen(val position : Int) : UpdateCommand()
}