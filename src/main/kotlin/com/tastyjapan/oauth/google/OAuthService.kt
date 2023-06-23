package com.tastyjapan.oauth.google

interface OAuthService {
    fun requestToken(authCode: String): String
    fun getUserEmail(token: String): String
    fun getUserInfo(token: String): UserInfo
}