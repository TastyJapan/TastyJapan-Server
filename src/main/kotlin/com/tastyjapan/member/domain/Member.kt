package com.tastyjapan.member.domain

import com.tastyjapan.group.domain.Group
import com.tastyjapan.oauth.domain.Oauth
import com.tastyjapan.review.domain.Review
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long,

    @Column(name="member_name")
    val name: String,

    @Column(name = "member_email")
    val email: String,

    @Column(name = "member_picture")
    val picture: String,

    @Enumerated(EnumType.STRING)
    val role: Role,
    ) {

    @OneToMany(mappedBy = "member")
    var groups: MutableList<Group> = mutableListOf()

    @OneToMany(mappedBy = "member")
    val reviews: MutableList<Review> = mutableListOf()

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oauth_id")
    lateinit var oauth: Oauth
}