package com.vitoksmile.sample.pager.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.vitoksmile.pager.adapter.SimplePagedAdapter
import com.vitoksmile.sample.pager.R
import com.vitoksmile.sample.pager.data.models.User
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter : SimplePagedAdapter<User, UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val ivAvatar = view.ivAvatar
        private val tvName = view.tvName
        private val tvEmail = view.tvEmail

        fun bind(user: User) = with(user) {
            ivAvatar.load(avatar) {
                transformations(CircleCropTransformation())
            }
            tvName.text = fullName
            tvEmail.text = email
        }
    }
}