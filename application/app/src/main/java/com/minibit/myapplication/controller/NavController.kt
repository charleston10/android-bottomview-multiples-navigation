package com.minibit.myapplication.controller

import android.view.View.NO_ID

abstract class NavController {
    abstract fun graphProvider(navGraphProvider: NavGraphProvider)
    abstract fun onBackPressed()
    abstract fun onBackPressedOnChild(): Boolean
    abstract fun onBackPressedOnNav()
    abstract fun onNavigationItemSelected(itemId: Int = NO_ID): Int
    abstract fun setOnNavigationChanged(listener: OnNavigationItemChanged)
}