package com.tastyjapan.picture.domain

import com.tastyjapan.restaurant.domain.Restaurant
import lombok.Getter
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor
class RestaurantPicture(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_picture_id")
    val id: Long? = null,

    @Column(name = "restaurant_picture_url")
    val url: String,
) {

    /**
     * 연관 관계 메서드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    var restaurant: Restaurant? = null
}