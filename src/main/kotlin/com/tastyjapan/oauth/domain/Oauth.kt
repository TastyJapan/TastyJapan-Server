package com.tastyjapan.oauth.domain

import com.tastyjapan.member.domain.Member
import lombok.Getter
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@NoArgsConstructor
@Getter
class Oauth(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oauth_id")
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "oauth")
    val member: Member,

    @Enumerated(EnumType.STRING)
    val provider: OauthProvider,
) {

    /**
     * 연관 관계 메서드
     */
    @Column(name = "refresh_token")
    var refreshToken: String = "tasty";
}