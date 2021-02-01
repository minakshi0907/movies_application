package com.example.moviesapplication.base

import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    protected fun setTitle(title: String) {
        supportActionBar?.let {
            it.title = title
        }
    }

    fun showBackBtn() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }
    override fun onBackPressed() {
        finish()
    }
}