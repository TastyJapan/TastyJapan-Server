package com.tastyjapan.member.domain

import com.tastyjapan.group.domain.Groups
import com.tastyjapan.oauth.domain.Oauth
import com.tastyjapan.review.domain.Review
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor
data class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long? = null,

    @Column(name="member_name")
    val name: String,

    @Column(name = "member_email")
    val email: String,

    @Column(name = "member_picture")
    val picture: String,

    @Enumerated(EnumType.STRING)
    val role: Role,
    ) {
    /**
     * 연관 관계 메서드
     */
    @OneToMany(mappedBy = "member")
    var groups: MutableList<Groups> = mutableListOf()

    @OneToMany(mappedBy = "member")
    var reviews: MutableList<Review> = mutableListOf()

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oauth_id")
    lateinit var oauth: Oauth

}