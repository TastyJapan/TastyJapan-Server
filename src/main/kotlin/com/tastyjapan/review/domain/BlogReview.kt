package com.tastyjapan.review.domain

import com.tastyjapan.restaurant.domain.Restaurant
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor
class BlogReview (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_review_id")
    val id: Long,

    @Column(name = "review_url")
    val url: String,

    /**
     * 연관 관계 메서드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="restaurant_id")
    val restaurant: Restaurant,
    ){
}