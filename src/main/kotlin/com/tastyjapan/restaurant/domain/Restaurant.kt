package com.tastyjapan.restaurant.domain

import com.tastyjapan.menu.domain.Menu
import com.tastyjapan.picture.domain.RestaurantPicture
import com.tastyjapan.review.domain.BlogReview
import com.tastyjapan.review.domain.Review
import com.tastyjapan.group.domain.Group
import com.tastyjapan.group.domain.GroupRestaurant
import com.tastyjapan.review.domain.ExternalReview
import lombok.Getter
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor
@Getter
data class Restaurant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    val id: Long? = null,

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
) {

    /**
     * 연관 관계 메서드
     */

    @OneToMany(mappedBy = "restaurant")
    val restaurantPictures: MutableList<RestaurantPicture> = mutableListOf()

    @OneToMany(mappedBy = "restaurant")
    val blogReviews: MutableList<BlogReview> = mutableListOf()

    @OneToMany(mappedBy = "restaurant")
    val externalReviews: MutableList<ExternalReview> = mutableListOf()

    @OneToMany(mappedBy = "restaurant")
    val reviews: MutableList<Review> = mutableListOf()

    @OneToMany(mappedBy = "restaurant")
    val menus: MutableList<Menu> = mutableListOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_restaurant_id")
    val groupRestaurant: GroupRestaurant? = null

}