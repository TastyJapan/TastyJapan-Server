package com.tastyjapan.menu.domain

import com.tastyjapan.global.entity.BaseEntity
import com.tastyjapan.global.entity.CustomObject
import com.tastyjapan.restaurant.domain.Restaurant
import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@Entity
@NoArgsConstructor
class Menu(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    val id: Long = 0L,

    @Column(name = "menu_name")
    val name: String,

    @Column(name = "menu_price")
    val price: Long,

    @Enumerated(EnumType.STRING)
    val menu_sort: MenuSort,

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "menu_pictures")
    val pictures: MutableList<String> = mutableListOf(),

    ) : BaseEntity(), CustomObject {
    /**
     * 연관 관계 메서드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    var restaurant: Restaurant? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Menu) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    override fun toString(): String {
        return "Menu(id=$id, name='$name', price=$price, menu_sort=$menu_sort, pictures=$pictures, restaurant=${restaurant?.id})"
    }
}