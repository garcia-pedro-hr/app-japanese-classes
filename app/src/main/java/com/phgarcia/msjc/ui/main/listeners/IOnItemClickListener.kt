package com.phgarcia.msjc.ui.main.listeners

interface IOnItemClickListener<T> {
    fun onItemClicked(obj: T, pos: Int)
}
