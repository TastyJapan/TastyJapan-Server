package com.tastyjapan.review.domain

import com.tastyjapan.restaurant.domain.Restaurant
import lombok.Getter
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor
data class BlogReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_review_id")
    val id: Long? = null,

    @Column(name = "review_url")
    val url: String,

    @Column(name = "blog_review_source")
    val source: String,
) {

    /**
     * 연관 관계 메서드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    var restaurant: Restaurant? = null
}