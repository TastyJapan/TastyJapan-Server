package com.tastyjapan.oauth.domain

import com.tastyjapan.global.entity.BaseEntity
import com.tastyjapan.global.entity.CustomObject
import com.tastyjapan.member.domain.Member
import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@Entity
@NoArgsConstructor
class Oauth(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oauth_id")
    val id: Long = 0L,

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "oauth")
    val member: Member,

    @Enumerated(EnumType.STRING)
    val provider: OauthProvider,
) : BaseEntity(), CustomObject {

    /**
     * 연관 관계 메서드
     */
    @Column(name = "refresh_token")
    var refreshToken: String = "tasty";


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Oauth) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    override fun toString(): String {
        return "Oauth(id=$id, member=${member.id}, provider=$provider, refreshToken='$refreshToken')"
    }
}