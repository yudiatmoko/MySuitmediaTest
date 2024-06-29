package com.iyam.mysuitmediatest.presentation.thirdscreen.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.iyam.mysuitmediatest.databinding.UserListLayoutBinding
import com.iyam.mysuitmediatest.model.User

/*
Hi, Code Enthusiast!
https://github.com/yudiatmoko
*/

class UserAdapter(
    private val itemClick: (User) -> Unit
) : PagingDataAdapter<User, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object :
            DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    fun getSelectedPosition(): Int {
        return selectedPosition
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding = UserListLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserItemViewHolder(
            binding,
            itemClick,
            this
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val viewHolder = holder as UserItemViewHolder
        val user = getItem(position)
        user?.let { viewHolder.bind(it) }

        viewHolder.itemView.setOnClickListener {
            selectedPosition = holder.adapterPosition
            notifyDataSetChanged()
            user?.let { it1 -> itemClick(it1) }
        }
    }
}
