package com.tastyjapan.restaurant.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

import com.tastyjapan.restaurant.domain.RestaurantPicture
import com.tastyjapan.restaurant.domain.QRestaurantPicture.restaurantPicture

@Repository
class RestaurantPictureRepositoryImpl(private val entityManager: EntityManager) : RestaurantPictureRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun findPictureByRestaurantId(restaurantId: Long): List<RestaurantPicture> {
        return queryFactory.selectFrom(restaurantPicture)
            .where(restaurantPicture.restaurant.id.eq(restaurantId))
            .fetch()
    }
}