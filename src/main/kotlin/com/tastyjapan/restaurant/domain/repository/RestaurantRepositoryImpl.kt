package com.tastyjapan.restaurant.domain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.tastyjapan.city.City
import com.tastyjapan.group.domain.QGroupRestaurant
import com.tastyjapan.menu.domain.MenuSort
import com.tastyjapan.restaurant.domain.Restaurant
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

import com.tastyjapan.restaurant.domain.QRestaurant.restaurant
import com.tastyjapan.menu.domain.QMenu.menu
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl


@Repository
class RestaurantRepositoryImpl(private val entityManager: EntityManager) : RestaurantRepositoryCustom {
    private val queryFactory: JPAQueryFactory = JPAQueryFactory(entityManager)

    override fun findRestaurantsByLatAndLong(
        longMin: Double,
        latMin: Double,
        longMax: Double,
        latMax: Double,
        pageable: Pageable
    ): Slice<Restaurant> {
        val query = queryFactory
            .selectFrom(restaurant)
            .where(
                restaurant.longitude.between(longMin, longMax),
                restaurant.latitude.between(latMin, latMax)
            )

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        query.offset(pageRequest.offset)
        query.limit(pageRequest.pageSize.toLong())

        val content = query.fetch()
        val hasNext = content.size > pageRequest.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }

    override fun findRestaurantByCityAndMenu(
        longMin: Double,
        latMin: Double,
        longMax: Double,
        latMax: Double,
        city: City,
        menuSort: MenuSort,
        pageable: Pageable
    ): Slice<Restaurant> {
        val query = queryFactory
            .selectFrom(restaurant)
            .leftJoin(restaurant.menus, menu)
            .where(
                restaurant.longitude.between(longMin, longMax),
                restaurant.latitude.between(latMin, latMax),
                restaurant.city.eq(city),
                restaurant.menus.any().menu_sort.eq(menuSort)
            )

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        query.offset(pageRequest.offset)
        query.limit(pageRequest.pageSize.toLong())

        val content = query.fetch()
        val hasNext = content.size > pageRequest.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }

    override fun findRestaurantsByCity(
        longMin: Double,
        latMin: Double,
        longMax: Double,
        latMax: Double,
        city: City,
        pageable: Pageable
    ): Slice<Restaurant> {
        val query = queryFactory
            .selectFrom(restaurant)
            .where(
                restaurant.longitude.between(longMin, longMax),
                restaurant.latitude.between(latMin, latMax),
                restaurant.city.eq(city)
            )

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        query.offset(pageRequest.offset)
        query.limit(pageRequest.pageSize.toLong())

        val content = query.fetch()
        val hasNext = content.size > pageRequest.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }

    override fun findRestaurantsByMenu(
        longMin: Double,
        latMin: Double,
        longMax: Double,
        latMax: Double,
        menuSort: MenuSort,
        pageable: Pageable
    ): Slice<Restaurant> {
        val query = queryFactory
            .selectFrom(restaurant)
            .leftJoin(restaurant.menus, menu)
            .where(
                restaurant.longitude.between(longMin, longMax),
                restaurant.latitude.between(latMin, latMax),
                restaurant.menus.any().menu_sort.eq(menuSort)
            )

        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        query.offset(pageRequest.offset)
        query.limit(pageRequest.pageSize.toLong())

        val content = query.fetch()
        val hasNext = content.size > pageRequest.pageSize

        return SliceImpl(content, pageRequest, hasNext)
    }

    override fun findRestaurantRecommend(): Restaurant {
        val sumReviews = restaurant.reviews.size()
        val sumExternalReviews = restaurant.externalReviews.size()
        val sumBlogReviews = restaurant.blogReviews.size()

        return queryFactory
            .select(restaurant)
            .from(restaurant)
            .orderBy(sumReviews.add(sumExternalReviews).add(sumBlogReviews).desc())
            .fetchFirst()
    }

    override fun checkRestaurantId(restaurantId: Long): Boolean {
        return queryFactory.selectOne()
            .from(restaurant)
            .where(restaurant.id.eq(restaurantId).and(restaurant.isDeleted.isFalse))
            .fetchFirst() != null
    }
}