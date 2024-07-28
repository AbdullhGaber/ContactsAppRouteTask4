package com.example.contactsapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.databinding.ActivityMainBinding
import com.example.contactsapp.models.Contact
import com.example.contactsapp.models.contacts
import com.example.contactsapp.ui.adapters.ContactsRVAdapter
import com.example.contactsapp.util.Constants.CONTACT_KEY
import com.example.contactsapp.util.FieldName
import com.example.contactsapp.util.Validator

class MainActivity : AppCompatActivity() {
    private val mValidator by lazy { Validator(this) }

    private val mContactsRVAdapter by lazy {
        ContactsRVAdapter(
            onItemClickListener = {
                val bundle = Bundle().apply { putSerializable(CONTACT_KEY , it ) }
                Intent(
                    this@MainActivity, ContactsDetailsActivity::class.java
                ).apply {
                    putExtras(bundle)
                    startActivity(this)
                }
            }
        )
    }

    private lateinit var mBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        addOnTextFieldChangeListener()
        populateDataInRVAdapter()
        setUpContactsRV()
        setOnSaveButtonClickListener()
    }

    private fun addOnTextFieldChangeListener() {
        mBinding.nameEt.addTextChangedListener {
            mValidator.clearFieldError(FieldName.NAME)
        }

        mBinding.phoneEt.addTextChangedListener {
            mValidator.clearFieldError(FieldName.PHONE_NUMBER)
            mBinding.counterPhoneTv.text = "${mBinding.phoneEt.text.toString().length}/11"
        }
    }

    private fun populateDataInRVAdapter() {
        mContactsRVAdapter.asyncListDiffer.submitList(contacts)
    }

    private fun setOnSaveButtonClickListener() {
        mBinding.saveBtn.setOnClickListener {
            val name = mBinding.nameEt.text.toString()
            val phone = mBinding.phoneEt.text.toString()
            val description = mBinding.descriptionEt.text.toString()
            if (mValidator.areFieldsValid(name, phone)) {
                contacts.add(Contact(contacts.size + 1, name, phone, description))
                mContactsRVAdapter.asyncListDiffer.submitList(contacts)
                mContactsRVAdapter.notifyItemInserted(contacts.size)
            }else{
                if(mValidator.mError.nameFieldErrorMessage.isNotEmpty()){
                    mBinding.nameEt.error = mValidator.mError.nameFieldErrorMessage
                }

                if(mValidator.mError.phoneNumberFieldErrorMessage.isNotEmpty()){
                    mBinding.phoneEt.error = mValidator.mError.phoneNumberFieldErrorMessage
                }
            }
        }
    }

    private fun setUpContactsRV() {
        mBinding.contactsRv.apply{
            adapter = mContactsRVAdapter
            layoutManager = LinearLayoutManager(this@MainActivity , RecyclerView.VERTICAL , false)
        }
    }

}