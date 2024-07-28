package com.example.contactsapp.models

import java.io.Serializable


data class Contact(
    val id : Int = -1,
    val name : String,
    val phoneNo : String,
    val description : String = ""
) : Serializable

val contacts = mutableListOf<Contact>()
