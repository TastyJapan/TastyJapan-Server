package com.tastyjapan.review.domain

import com.tastyjapan.global.entity.BaseEntity
import com.tastyjapan.global.entity.CustomObject
import com.tastyjapan.member.domain.Member
import com.tastyjapan.restaurant.domain.Restaurant
import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@Entity
@NoArgsConstructor
class Review(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "review_id") val id: Long = 0L,

    @Column(name = "review_content") val content: String,

    @Column(name = "rating") val rating: Double
) : BaseEntity(), CustomObject {
    /**
     * 연관 관계 메서드
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    var restaurant: Restaurant? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Review) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    override fun toString(): String {
        return "Review(id=$id, content='$content', rating=$rating, restaurant=${restaurant?.id}, member=${member?.id})"
    }
}