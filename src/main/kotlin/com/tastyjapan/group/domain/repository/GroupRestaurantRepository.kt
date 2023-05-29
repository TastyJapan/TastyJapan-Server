package com.tastyjapan.group.domain.repository

import com.tastyjapan.group.domain.GroupRestaurant
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRestaurantRepository : JpaRepository<GroupRestaurant, Long>, GroupRestaurantRepositoryCustom{
}