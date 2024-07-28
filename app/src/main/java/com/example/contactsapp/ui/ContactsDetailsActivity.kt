package com.example.contactsapp.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.contactsapp.databinding.ActivityContactsDetailsBinding
import com.example.contactsapp.models.Contact
import com.example.contactsapp.util.Constants

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class ContactsDetailsActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityContactsDetailsBinding

    private val mContact by lazy{
        intent.getSerializableExtra(Constants.CONTACT_KEY , Contact::class.java)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityContactsDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        displayBackButton()
        bindUI()
    }

    @SuppressLint("RestrictedApi")
    private fun displayBackButton(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun bindUI(){
        mBinding.nameValueTv.text = mContact.name
        mBinding.phoneNoValueTv.text = mContact.phoneNo
        mBinding.descriptionValueTv.text = mContact.description
    }
}