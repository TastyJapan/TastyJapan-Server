package com.tastyjapan.group.domain

import com.tastyjapan.global.entity.BaseEntity
import com.tastyjapan.global.entity.CustomObject
import com.tastyjapan.restaurant.domain.Restaurant
import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@Entity
@NoArgsConstructor
class GroupRestaurant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_restaurant_id")
    val id: Long = 0L,
    /**
     * 연관 관계 메서드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    var groups: Groups,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    var restaurants: Restaurant
) : BaseEntity(), CustomObject {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GroupRestaurant) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    override fun toString(): String {
        return "GroupRestaurant(id=$id, groups=${groups.id}, restaurants=${restaurants.id})"
    }

}

