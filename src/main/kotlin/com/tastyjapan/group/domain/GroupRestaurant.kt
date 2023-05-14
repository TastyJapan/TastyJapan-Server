package com.tastyjapan.group.domain

import com.tastyjapan.restaurant.domain.Restaurant
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor
data class GroupRestaurant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_restaurant_id")
    val id: Long? = null,
    /**
     * 연관 관계 메서드
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    var groups: Groups,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    var restaurants: Restaurant
) {


}

