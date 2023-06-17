package com.tastyjapan.member.domain

import com.tastyjapan.global.entity.BaseEntity
import com.tastyjapan.global.entity.CustomObject
import com.tastyjapan.group.domain.Groups
import com.tastyjapan.oauth.domain.Oauth
import com.tastyjapan.review.domain.Review
import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@Entity
@NoArgsConstructor
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long = 0L,

    @Column(name = "member_name")
    val name: String,

    @Column(name = "member_email")
    val email: String,

    @Column(name = "member_picture")
    val picture: String? = null,

    @Enumerated(EnumType.STRING)
    val role: Role,

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    var status: EntityStatus = EntityStatus.ACTIVE

) : BaseEntity(), CustomObject {
    /**
     * 연관 관계 메서드
     */
    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var groups: MutableList<Groups> = mutableListOf()

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var reviews: MutableList<Review> = mutableListOf()

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oauth_id")
    lateinit var oauth: Oauth

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Member) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    override fun toString(): String {
        return "Member(id=$id, name='$name', email='$email', picture='$picture', role=$role)"
    }

    fun addReview(review: Review) {
        reviews.add(review)
        review.member = this
    }

    fun softDelete() {
        this.status = EntityStatus.INACTIVE
    }
}