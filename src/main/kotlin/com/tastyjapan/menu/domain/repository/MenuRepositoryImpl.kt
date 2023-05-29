package com.tastyjapan.menu.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.tastyjapan.menu.domain.Menu
import javax.persistence.EntityManager
import com.tastyjapan.menu.domain.QMenu.menu
import com.tastyjapan.restaurant.domain.QRestaurant.restaurant

class MenuRepositoryImpl(private val entityManager: EntityManager) : MenuRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)
}