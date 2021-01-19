package com.example.mycontacts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mycontacts.databinding.ContactListItemBinding


class ListAdapter(
    private val context: Context,
    private val mContacts: List<ContactDetails>,
    private val viewModel: ContactsViewModel ) : RecyclerView.Adapter<ListAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(ContactListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false),context)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.binding?.title?.text = mContacts[position].name
        holder.binding?.company?.text = mContacts[position].companyName
        holder.binding?.position = position
        holder.binding?.contacts = mContacts[position]
        holder.binding?.viewModel = viewModel

        holder.binding?.profilePic?.let {
            Glide.with(context)
                .load(mContacts[position].smallImageURL)
                .centerCrop()
                .placeholder(R.mipmap.user_icon_small)
                .into(it)
        }

        holder.binding?.favorite?.let {
            Glide.with(context)
                .load(getFavoritesIamge(mContacts[position].isFavorite))
                .centerCrop()
                .into(it)
        }
    }

    private fun getFavoritesIamge(favorite: Boolean): Int {
        return if(favorite)
            R.mipmap.favorite_true
        else
            R.mipmap.favorite_false
    }


    override fun getItemCount(): Int {
        return mContacts.size
    }



    class ContactViewHolder(v:  ContactListItemBinding, context :Context): RecyclerView.ViewHolder(v.root){
        val binding: ContactListItemBinding? = v

    }
}
