package com.tastyjapan.security.custom

data class JwtTokens(
    val accessToken: String? = null,
    val refreshToken: String? = null
)