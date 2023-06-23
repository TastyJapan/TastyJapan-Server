package com.tastyjapan.oauth.ui.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.tastyjapan.security.custom.JwtTokens

data class SignInResponse(
    @get:JsonProperty("isMember")
    @param:JsonProperty("isMember")
    val isMember: Boolean,
    val jwtTokens: JwtTokens
)