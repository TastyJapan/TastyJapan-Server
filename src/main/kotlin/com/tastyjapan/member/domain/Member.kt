package com.tastyjapan.member.domain

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
    val role: Role
    ) {
}