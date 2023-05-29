package com.tastyjapan.review.domain

import com.tastyjapan.global.entity.BaseEntity
import com.tastyjapan.global.entity.CustomObject
import com.tastyjapan.restaurant.domain.Restaurant
import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@Entity
@NoArgsConstructor
class BlogReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_review_id")
    val id: Long = 0L,

    @Column(name = "review_url")
    val url: String,

    @Enumerated(EnumType.STRING)
    val source: BlogSource,
) : BaseEntity(), CustomObject {

    /**
     * 연관 관계 메서드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    var restaurant: Restaurant? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BlogReview) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    override fun toString(): String {
        return "BlogReview(id=$id, url='$url', source='$source', restaurant=${restaurant?.id})"
    }
}