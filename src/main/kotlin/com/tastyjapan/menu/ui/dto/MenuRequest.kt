package com.tastyjapan.menu.ui.dto

data class MenuRequest(
    val name: String,
    val price: Long,
    val menu_sort: String,
    val pictures: MutableList<String> = mutableListOf(),
) {
}