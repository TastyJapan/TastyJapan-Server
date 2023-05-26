package com.tastyjapan.restaurant.domain

import com.tastyjapan.global.entity.BaseEntity
import com.tastyjapan.global.entity.CustomObject
import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@Entity
@NoArgsConstructor
class RestaurantPicture(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_picture_id")
    val id: Long = 0L,

    @Column(name = "restaurant_picture_url")
    val url: String,
) : BaseEntity(), CustomObject {

    /**
     * 연관 관계 메서드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    var restaurant: Restaurant? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RestaurantPicture) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    override fun toString(): String {
        return "RestaurantPicture(id=$id, url='$url', restaurant=${restaurant?.id})"
    }
}