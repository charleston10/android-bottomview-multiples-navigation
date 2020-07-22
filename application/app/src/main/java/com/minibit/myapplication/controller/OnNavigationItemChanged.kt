package com.minibit.myapplication.controller

interface OnNavigationItemChanged {
    fun onItemChanged(itemId: Int)
    fun onBackItem(itemId: Int)
}