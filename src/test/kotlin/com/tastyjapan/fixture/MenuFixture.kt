package com.tastyjapan.fixture

import com.tastyjapan.menu.domain.Menu
import com.tastyjapan.menu.domain.MenuSort

object MenuFixture {
    fun createFirstRamenMenu(): Menu {
        return Menu(
            name = "Tonkotsu Ramen",
            price = 10000,
            menu_sort = MenuSort.RAMEN,
            pictures = mutableListOf("https://img-cf.kurly.com/shop/data/goodsview/20211125/gv00000248650_1.jpg"),
        )
    }

    fun createSushiMenu(): Menu {
        return Menu(
            name = "연어 특선 세트",
            price = 10000,
            menu_sort = MenuSort.SUSHI,
            pictures = mutableListOf("스시 사진1", "스시 사진2")
        )
    }
}