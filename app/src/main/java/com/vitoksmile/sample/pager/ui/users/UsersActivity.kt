package com.vitoksmile.sample.pager.ui.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vitoksmile.sample.pager.R
import kotlinx.android.synthetic.main.activity_users.*

class UsersActivity : AppCompatActivity() {

    private val viewModel: UsersViewModel by lazy {
        ViewModelProviders.of(this).get(UsersViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        val adapter = UsersAdapter()
        recyclerView.adapter = adapter

        viewModel.usersData.observe(this, Observer { adapter.setData(it) })
        viewModel.networkData.observe(this, Observer { state ->
            progressBar.isVisible = state.isLoading
        })
    }
}
