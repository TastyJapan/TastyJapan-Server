package com.tastyjapan.restaurant.domain

import com.tastyjapan.city.City
import com.tastyjapan.global.entity.BaseEntity
import com.tastyjapan.global.entity.CustomObject
import com.tastyjapan.menu.domain.Menu
import com.tastyjapan.review.domain.BlogReview
import com.tastyjapan.review.domain.Review
import com.tastyjapan.group.domain.GroupRestaurant
import com.tastyjapan.review.domain.ExternalReview
import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@Entity
@NoArgsConstructor
class Restaurant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    val id: Long = 0L,

    @Column(name = "restaurant_name")
    val name: String,

    @Column(name = "restaurant_longitude")
    val longitude: Double,

    @Column(name = "restaurant_latitude")
    val latitude: Double,

    @Column(name = "restaurant_address")
    val address: String,

    @Column(name = "restaurant_summary")
    val summary: String,

    @Column(name = "restaurant_rating")
    val rating: Double,

    @Enumerated(EnumType.STRING)
    val city: City
) : BaseEntity(), CustomObject {

    /**
     * 연관 관계 메서드
     */

    @OneToMany(mappedBy = "restaurant", cascade = [CascadeType.ALL])
    var restaurantPictures: MutableList<RestaurantPicture> = mutableListOf()

    @OneToMany(mappedBy = "restaurant", cascade = [CascadeType.ALL])
    var blogReviews: MutableList<BlogReview> = mutableListOf()

    @OneToMany(mappedBy = "restaurant", cascade = [CascadeType.ALL])
    var externalReviews: MutableList<ExternalReview> = mutableListOf()

    @OneToMany(mappedBy = "restaurant", cascade = [CascadeType.ALL])
    var reviews: MutableList<Review> = mutableListOf()

    @OneToMany(mappedBy = "restaurant", cascade = [CascadeType.ALL])
    var menus: MutableList<Menu> = mutableListOf()

    @OneToMany(mappedBy = "restaurants")
    var groupRestaurantList: MutableList<GroupRestaurant> = mutableListOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Restaurant) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    override fun toString(): String {
        return "Restaurant(id=$id, name='$name', longitude=$longitude, latitude=$latitude, address='$address', summary='$summary', rating=$rating, city=$city)"
    }

    fun addMenu(menu: Menu){
        menus.add(menu)
        menu.restaurant = this
    }

    fun addReview(review: Review){
        reviews.add(review)
        review.restaurant = this
    }

    fun addExternalReview(externalReview: ExternalReview){
        externalReviews.add(externalReview)
        externalReview.restaurant = this
    }

    fun addBlogReview(blogReview: BlogReview){
        blogReviews.add(blogReview)
        blogReview.restaurant = this
    }

    fun addRestaurantPicture(restaurantPicture: RestaurantPicture){
        restaurantPictures.add(restaurantPicture)
        restaurantPicture.restaurant = this
    }

    fun addGroupRestaurant(groupRestaurant: GroupRestaurant){
        groupRestaurantList.add(groupRestaurant)
        groupRestaurant.restaurants = this
    }
}