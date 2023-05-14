package com.tastyjapan.group.domain

import com.tastyjapan.restaurant.domain.Restaurant
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor
class GroupRestaurant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_restaurant_id")
    val id: Long
    ){
    @OneToMany(mappedBy = "groupRestaurant")
    var groups: MutableSet<Group> = mutableSetOf()

    @OneToMany(mappedBy = "groupRestaurant")
    var restaurants: MutableSet<Restaurant> = mutableSetOf()

}