package com.example.contactsapp.util

import android.content.Context
import com.example.contactsapp.R

class Validator(
    private val mContext : Context
) {
    val mError by lazy { Error() }

    fun areFieldsValid(
        name : String,
        phoneNumber : String
    ) : Boolean {

        if(name.isEmpty() && mError.nameFieldErrorMessage.lines().size <= 2
            && mError.nameFieldErrorMessage != mContext.getString(R.string.name_empty_error_msg)
            ) {
            mError.nameFieldErrorMessage += mContext.getString(R.string.name_empty_error_msg)
        }

        if(name.length < 3 && mError.nameFieldErrorMessage.lines().size <= 2 &&
            mError.nameFieldErrorMessage != mContext.getString(R.string.name_min_3_chars_error_msg)
            ) {
            mError.nameFieldErrorMessage += mContext.getString(R.string.name_min_3_chars_error_msg)
        }

        if(phoneNumber.isEmpty() && mError.phoneNumberFieldErrorMessage.lines().size <= 2
            && mContext.getString(R.string.phone_empty_error_msg) != mError.phoneNumberFieldErrorMessage
            ) {
            mError.phoneNumberFieldErrorMessage += mContext.getString(R.string.phone_empty_error_msg)
        }

        if(phoneNumber.length != 11 && mError.phoneNumberFieldErrorMessage.lines().size <= 2
            && mContext.getString(R.string.phone_11_chars_error_msg) != mError.phoneNumberFieldErrorMessage
            ) {
            mError.phoneNumberFieldErrorMessage += mContext.getString(R.string.phone_11_chars_error_msg)
        }

        return mError.nameFieldErrorMessage.isEmpty() && mError.phoneNumberFieldErrorMessage.isEmpty()
    }

    fun clearFieldError(field : FieldName){
        when(field){
            FieldName.NAME -> {
                mError.nameFieldErrorMessage = ""
            }

            FieldName.PHONE_NUMBER -> {
                mError.phoneNumberFieldErrorMessage = ""
            }
        }
    }
}

enum class FieldName {NAME , PHONE_NUMBER}