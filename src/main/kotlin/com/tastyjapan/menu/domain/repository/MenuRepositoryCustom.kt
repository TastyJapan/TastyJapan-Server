package com.tastyjapan.menu.domain.repository

import com.tastyjapan.menu.domain.Menu

interface MenuRepositoryCustom {
    fun findMenuByRestaurantId(restaurantId: Long): List<Menu>
}