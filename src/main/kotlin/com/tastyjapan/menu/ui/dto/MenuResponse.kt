package com.tastyjapan.menu.ui.dto

import com.tastyjapan.menu.domain.Menu
import com.tastyjapan.menu.domain.MenuSort
import com.tastyjapan.restaurant.domain.Restaurant


data class MenuResponse(
    val name: String,
    val price: Long,
    val menuSort: MenuSort,
    val menuImages: MutableList<String>,
) {
    constructor(menu: Menu) : this(
        name = menu.name,
        price = menu.price,
        menuSort = menu.menu_sort,
        menuImages = menu.pictures.map { it }.toMutableList()
    )
}