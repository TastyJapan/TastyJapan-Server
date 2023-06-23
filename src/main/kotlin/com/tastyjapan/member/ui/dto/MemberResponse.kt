package com.tastyjapan.member.ui.dto


data class MemberResponse(
    val id: Long,
    val name: String,
    val email: String,
    val picture: String,
)