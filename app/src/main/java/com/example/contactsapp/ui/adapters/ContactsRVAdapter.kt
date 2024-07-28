package com.example.contactsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.databinding.ContactItemBinding
import com.example.contactsapp.models.Contact

class ContactsRVAdapter(
    val onItemClickListener : ContactsRVAdapter.OnItemClickListener
) : RecyclerView.Adapter<ContactsRVAdapter.ViewHolder>() {

    private val differUtil = object : DiffUtil.ItemCallback<Contact>(){
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }

    }

    val asyncListDiffer = AsyncListDiffer(this , differUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context) , parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = asyncListDiffer.currentList[position]
        holder.bind(contact)
        holder.setOnItemClickListener()
    }

    inner class ViewHolder(private val mBinding : ContactItemBinding)
        : RecyclerView.ViewHolder(mBinding.root){
        fun bind(contact : Contact){
            mBinding.nameTv.text = contact.name
            mBinding.phoneNoTv.text = contact.phoneNo
        }

        fun setOnItemClickListener(){
            mBinding.root.setOnClickListener{
                onItemClickListener.onClick(asyncListDiffer.currentList[adapterPosition])
            }
            mBinding.arrowRightIv.setOnClickListener {
                onItemClickListener.onClick(asyncListDiffer.currentList[adapterPosition])
            }
        }
    }

    fun interface OnItemClickListener{
        fun onClick(contact : Contact)
    }
}

