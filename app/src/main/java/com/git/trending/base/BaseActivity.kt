package com.git.trending.base

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity


/**Created by Shiv Jalkote on 09-May-2021. **/

open class BaseActivity : AppCompatActivity() {

    fun enableBackButton() {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)
    }

    fun setActionBarTitle(title: String, enableBackButton: Boolean = false) {
        if (enableBackButton)
            enableBackButton()
        val actionBar = supportActionBar
        actionBar?.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}