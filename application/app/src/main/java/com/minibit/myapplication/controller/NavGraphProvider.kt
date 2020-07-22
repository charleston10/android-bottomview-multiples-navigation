package com.minibit.myapplication.controller

import androidx.annotation.NavigationRes

interface NavGraphProvider {
    @NavigationRes
    fun getNavGraphId(itemId: Int): Int?
}