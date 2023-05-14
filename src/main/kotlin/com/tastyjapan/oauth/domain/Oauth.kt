package com.tastyjapan.oauth.domain

import com.tastyjapan.member.domain.Member
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor
class Oauth(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oauth_id")
    val id: Long,

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "oauth")
    val member: Member,

    @Enumerated(EnumType.STRING)
    val provider : OauthProvider,
) {
    /**
     * 연관 관계 메서드
     */
    @Column(name = "refresh_token")
    val refreshToken: String = "tasty";
}