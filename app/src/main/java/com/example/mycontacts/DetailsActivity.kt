package com.example.mycontacts

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.mycontacts.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity(){

    private lateinit var viewModel: ContactsViewModel
    lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var position: Int = intent.getIntExtra("position",0)
        val contact = intent.getSerializableExtra("contact") as? ContactDetails
        viewModel = ViewModelProviders.of(this,viewModelFactory {ContactsViewModel()}).get(ContactsViewModel::class.java).apply { command.observe(this@DetailsActivity,commandObserver) }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        binding.viewModel = viewModel
        binding.contacts = contact
        binding.position = position
        setSupportActionBar(binding.toolbar);

        ContactsApplication.firstTime = false

        binding?.profilePic?.let {
            Glide.with(this)
                .load(contact?.largeImageURL)
                .centerCrop()
                .placeholder(R.mipmap.user_large)
                .into(it)
        }

    }

    private val commandObserver = Observer<UpdateCommand> { command ->
        command?.also{ when(it) {
            is UpdateCommand.updateDetailFav -> {
                Glide.with(this)
                    .load(getFavoritesImage(it.isFavorite))
                    .centerCrop()
                    .into(binding.toolbarButton)
            }
        }}
    }

    private fun getFavoritesImage(favorite: Boolean): Int {
        return if(favorite)
            R.mipmap.favorite_true
        else
            R.mipmap.favorite_false
    }

}