package com.example.mycontacts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycontacts.databinding.ActivityContactsListBinding
import kotlinx.android.synthetic.main.activity_contacts_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private var mApiService: APIService? = null
    private var listAdapter: ListAdapter? = null;
    private var contactsList = ArrayList<ContactDetails>()
    private lateinit var viewModel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this,viewModelFactory {ContactsViewModel() }).get(ContactsViewModel::class.java).apply { command.observe(this@MainActivity,commandObserver) }
        val binding: ActivityContactsListBinding = DataBindingUtil.setContentView(this, R.layout.activity_contacts_list)

        contactList!!.layoutManager = LinearLayoutManager(this)
        contactList.addItemDecoration(
            DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL)
        )
        binding.viewModel = viewModel

        mApiService = ApiClient.client.create(APIService::class.java)

        fetchContactsList()


    }

    private val commandObserver = Observer<UpdateCommand> { command ->
        command?.also{ when(it) {
            is UpdateCommand.updateList -> { setList()}
            is UpdateCommand.NavigateToDetailsScreen -> { navigateToDetailsScreen(it.position)}
        }}
    }

    private fun navigateToDetailsScreen(position: Int) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("position", position)
        intent.putExtra("contact",ContactsApplication.globalList[position] )
        startActivity(intent)
    }

    private fun setList() {
        ContactsApplication.globalList = ContactsApplication.globalList.sortedBy {!it.isFavorite}
        listAdapter = ListAdapter(this, ContactsApplication.globalList,viewModel)
        contactList!!.adapter = listAdapter
        listAdapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        if(!ContactsApplication.firstTime)
            setList()
    }

    private fun fetchContactsList() {
        val call = mApiService!!.fetchContacts()
        call.enqueue(object : Callback<List<ContactDetails>> {

            override fun onResponse(call: Call<List<ContactDetails>>, response: Response<List<ContactDetails>>) {

                Log.d(TAG, "Total Contacts: " + response.body()!!.size)
                val contacts = response.body()
                if (contacts != null) {
                    ContactsApplication.globalList = contacts
                    viewModel.prepareFavoriteList(ContactsApplication.globalList)
                    setList()
                }
            }

            override fun onFailure(call: Call<List<ContactDetails>>, t: Throwable) {
                Log.e(TAG, "Got error : " + t.localizedMessage)
            }
        })
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

}