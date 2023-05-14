package com.tastyjapan.review.domain

import com.tastyjapan.member.domain.Member
import com.tastyjapan.restaurant.domain.Restaurant
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor
data class Review(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "review_id") val id: Long? = null,

    @Column(name = "review_content") val content: String,

    @Column(name = "rating") val rating: Double
) {
    /**
     * 연관 관계 메서드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    var restaurant: Restaurant? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null
}