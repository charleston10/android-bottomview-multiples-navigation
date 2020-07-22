package com.minibit.myapplication.controller

class NavBackStack : ArrayList<Int>() {
    companion object {
        fun of(vararg elements: Int): NavBackStack {
            val b = NavBackStack()
            b.addAll(elements.toTypedArray())
            return b
        }
    }

    fun removeLast() = removeAt(size - 1)

    fun moveLast(item: Int) {
        remove(item)
        add(item)
    }
}