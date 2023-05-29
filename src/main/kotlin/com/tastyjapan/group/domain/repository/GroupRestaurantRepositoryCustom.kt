package com.tastyjapan.group.domain.repository

import com.tastyjapan.group.domain.GroupRestaurant

interface GroupRestaurantRepositoryCustom {
    fun findGroupRestaurantByGroupId(groupId: Long): List<GroupRestaurant>
}