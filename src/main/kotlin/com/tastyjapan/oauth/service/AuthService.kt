package com.tastyjapan.oauth.service

import com.tastyjapan.exception.ErrorType
import com.tastyjapan.global.redis.RedisService
import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.Role
import com.tastyjapan.member.domain.repository.MemberRepository
import com.tastyjapan.oauth.google.OAuthService
import com.tastyjapan.oauth.ui.dto.SignInResponse
import com.tastyjapan.oauth.ui.dto.SignUpRequest
import com.tastyjapan.security.custom.JwtService
import com.tastyjapan.security.custom.JwtTokens
import com.tastyjapan.security.exception.BusinessException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthService(
    private val memberRepository: MemberRepository,
    private val oAuthService: OAuthService,
    private val jwtService: JwtService,
    private val redisService: RedisService
) {
    @Value("\${jwt.refresh-token-expiry}")
    private val refreshTokenExpiry: Long = 0

    @Value("\${jwt.access-token-expiry}")
    private val accessTokenExpiry: Long = 0

    @Transactional
    fun signIn(code: String): SignInResponse {
        val token = oAuthService.requestToken(code)
        val email = oAuthService.getUserEmail(token)

        val isExist = memberRepository.existsByEmail(email)
        if (!isExist) {
            return SignInResponse(true, signup(token = token))
        }

        val tokens = jwtService.issue(email)
        storeRefresh(tokens, email)
        return SignInResponse(true, tokens)
    }

    @Transactional
    fun signup(token: String): JwtTokens {
        val userInfo = oAuthService.getUserInfo(token)

        join(userInfo.email, SignUpRequest(nickname = userInfo.name, picture = userInfo.picture))

        val tokens = jwtService.issue(userInfo.email)
        storeRefresh(tokens, userInfo.email)
        return tokens
    }

    @Transactional
    fun refresh(refreshToken: String): JwtTokens {
        val email = redisService.getValue("$REFRESH_TOKEN_PREFIX:${refreshToken}") as String?
            ?: throw BusinessException(ErrorType.EXPIRED_REFRESH_TOKEN)

        val member: Member = memberRepository.findByEmail(email)
            ?: throw BusinessException(ErrorType.USER_NOT_FOUND)

        val jwtToken = jwtService.refresh(refreshToken, email, member.id)
        storeRefresh(jwtToken, member.email)
        redisService.deleteValue("$REFRESH_TOKEN_PREFIX:${refreshToken}")
        return jwtToken
    }

    @Transactional
    fun logout(accessToken: String, refreshToken: String) {
        redisService.getValue("$REFRESH_TOKEN_PREFIX:$refreshToken")
            ?: throw BusinessException(ErrorType.INVALID_REFRESH_TOKEN)
        redisService.deleteValue("$REFRESH_TOKEN_PREFIX:$refreshToken")

        storeLogoutAccessToken(accessToken)
    }

    @Transactional
    fun withdraw(memberId: Long): Boolean {
        val member =
            memberRepository.findById(memberId).orElseThrow { throw BusinessException(ErrorType.USER_NOT_FOUND) }
        member.softDelete()
        return true
    }

    private fun join(email: String, signUpRequest: SignUpRequest) {
        memberRepository.save(
            Member(
                email = email,
                name = signUpRequest.nickname,
                picture = signUpRequest.picture,
                role = Role.USER
            )
        )
    }

    private fun storeRefresh(jwtToken: JwtTokens, email: String) {
        redisService.setValue(
            "$REFRESH_TOKEN_PREFIX:${jwtToken.refreshToken}",
            email,
            refreshTokenExpiry
        )
    }

    private fun storeLogoutAccessToken(accessToken: String) {
        redisService.setValue(
            "$LOGOUT_ACCESS_TOKEN_PREFIX:${accessToken}",
            "logout",
            accessTokenExpiry
        )
    }

    companion object {
        const val REFRESH_TOKEN_PREFIX = "refresh"
        const val LOGOUT_ACCESS_TOKEN_PREFIX = "logout"
    }
}