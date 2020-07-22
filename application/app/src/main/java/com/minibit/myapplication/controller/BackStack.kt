package com.minibit.myapplication.controller

class BackStack : ArrayList<Int>() {
    companion object {
        fun of(vararg elements: Int): BackStack {
            val b = BackStack()
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