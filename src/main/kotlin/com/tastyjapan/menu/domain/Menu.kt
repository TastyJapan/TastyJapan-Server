package com.tastyjapan.menu.domain

import com.tastyjapan.restaurant.domain.Restaurant
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor
class Menu(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    val id: Long,

    @Column(name="menu_name")
    val name: String,

    @Column(name = "menu_price")
    val price: Long,

    @Enumerated(EnumType.STRING)
    val menu_sort: MenuSort,

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "menu_pictures")
    val pictures: MutableList<String> = mutableListOf(),

    /**
     * 연관 관계 메서드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="restaurant_id")
    val restaurant: Restaurant,

    ) {
}