package com.iyam.mysuitmediatest.presentation.thirdscreen.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
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
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    fun getSelectedPosition(): Int {
        return selectedPosition
    }

    private val differ = AsyncListDiffer(
        this,
        object : DiffUtil.ItemCallback<User>() {
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
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    )

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

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val viewHolder = holder as UserItemViewHolder
        val category = differ.currentList[position]
        viewHolder.bind(category)

        viewHolder.itemView.setOnClickListener {
            selectedPosition = holder.adapterPosition
            notifyDataSetChanged()
            itemClick(category)
        }
    }

    fun setData(data: List<User>) {
        differ.submitList(data)
    }
}
