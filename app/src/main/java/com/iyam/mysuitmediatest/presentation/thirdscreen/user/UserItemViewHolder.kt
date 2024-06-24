package com.iyam.mysuitmediatest.presentation.thirdscreen.user

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.iyam.mysuitmediatest.R
import com.iyam.mysuitmediatest.core.ViewHolderBinder
import com.iyam.mysuitmediatest.databinding.UserListLayoutBinding
import com.iyam.mysuitmediatest.model.User

/*
Hi, Code Enthusiast!
https://github.com/yudiatmoko
*/

class UserItemViewHolder(
    private val binding: UserListLayoutBinding,
    private val onClicked: (User) -> Unit,
    private val adapter: UserAdapter
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<User> {
    override fun bind(item: User) {
        val name = listOf(item.firstName, item.lastName)
        val fullName = name.joinToString(" ")
        binding.ivUserImg.load(item.avatar)
        binding.tvUserName.text = fullName
        binding.tvUserEmail.text = item.email
        binding.root.setOnClickListener {
            onClicked.invoke(item)
        }
        setSelected(adapterPosition == adapter.getSelectedPosition())
    }
    private fun setSelected(selected: Boolean) {
        itemView.isActivated = selected

        val textColor = if (selected) {
            ContextCompat.getColor(itemView.context, R.color.blue)
        } else {
            ContextCompat.getColor(itemView.context, R.color.black)
        }
        binding.tvUserName.setTextColor(textColor)
        binding.tvUserEmail.setTextColor(textColor)
    }
}
