package com.minibit.myapplication.controller

interface OnNavigationItemChanged {
    fun onBackItem(itemId: Int)
    fun onNavigationReset(itemId: Int)
}