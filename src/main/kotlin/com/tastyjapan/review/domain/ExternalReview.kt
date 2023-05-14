package com.tastyjapan.review.domain

import com.tastyjapan.group.domain.Group
import com.tastyjapan.restaurant.domain.Restaurant
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor
class ExternalReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "external_review_id")
    val id: Long,

    @Column(name = "external_review_content")
    val content: String,

    @Column(name = "reviewer_photo_url")
    val url: String,

    @Column(name = "reviewer_nickname")
    val nickname: String,

    @Column(name = "rating")
    val rating: Double,

    /**
     * 연관 관계 메서드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="restaurant_id")
    var restaurant : Restaurant
) {

}