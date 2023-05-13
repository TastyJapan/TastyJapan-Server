package com.tastyjapan.group.domain

import com.tastyjapan.member.domain.Member
import com.tastyjapan.restaurant.domain.Restaurant
import lombok.Getter
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor
data class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    val id: Long? = null,

    @Column(name = "save_title")
    val title: String,

    ) {
    /**
     * 연관 관계 메서드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member? = null

    @OneToMany(mappedBy = "groups")
    val groupRestaurantList: List<GroupRestaurant> = mutableListOf()

}

