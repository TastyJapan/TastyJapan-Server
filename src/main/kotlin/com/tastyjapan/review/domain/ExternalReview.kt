package com.tastyjapan.review.domain

import com.tastyjapan.global.entity.BaseEntity
import com.tastyjapan.global.entity.CustomObject
import com.tastyjapan.restaurant.domain.Restaurant
import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@Entity
@NoArgsConstructor
class ExternalReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_review_id")
    val id: Long = 0L,

    @Column(name = "external_review_content")
    val content: String,

    @Column(name = "reviewer_photo_url")
    val url: String,

    @Column(name = "reviewer_nickname")
    val nickname: String,

    @Column(name = "rating")
    val rating: Double,

    @Column(name = "external_review_source")
    val source: String,
) : BaseEntity(), CustomObject {

    /**
     * 연관 관계 메서드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    var restaurant: Restaurant? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ExternalReview) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    override fun toString(): String {
        return "ExternalReview(id=$id, content='$content', url='$url', nickname='$nickname', rating=$rating, source='$source', restaurant=${restaurant?.id})"
    }
}