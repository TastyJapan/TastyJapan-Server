package com.tastyjapan.security.custom

import com.tastyjapan.exception.ErrorType
import com.tastyjapan.global.annotation.NonMember
import com.tastyjapan.global.redis.RedisService
import com.tastyjapan.oauth.service.AuthService.Companion.LOGOUT_ACCESS_TOKEN_PREFIX
import com.tastyjapan.security.exception.BusinessException
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.http.HttpMethod
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtInterceptor(
    private val jwtUtil: JwtUtil,
    private val jwtProvider: JwtProvider,
    private val redisService: RedisService
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (preflight(request)) return true
        if (isNonMemberMethod(handler, request)) return true

        try {
            val accessToken = jwtUtil.resolveAccessToken(request)
            jwtProvider.parseToken(accessToken)
            if (isLogout(accessToken)) {
                throw BusinessException(ErrorType.INVALID_JWT)
            }
            setAuthentication(accessToken)
        } catch (e: ExpiredJwtException) {
            throw BusinessException(ErrorType.EXPIRED_JWT)
        } catch (e: NullPointerException) {
            throw BusinessException(ErrorType.NULL_JWT)
        } catch (e: Exception) {
            throw BusinessException(ErrorType.INVALID_JWT)
        }
        return true
    }

    private fun isNonMemberMethod(handler: Any, request: HttpServletRequest): Boolean {
        val handlerMethod = handler as HandlerMethod
        val accessToken = request.getHeader("Authorization")

        return (handlerMethod.getMethodAnnotation(NonMember::class.java) != null
                && (accessToken == null || accessToken.isBlank()))
    }

    private fun preflight(request: HttpServletRequest): Boolean {
        return request.method == HttpMethod.OPTIONS.name
    }

    private fun setAuthentication(accessToken: String) {
        val authentication = jwtUtil.getAuthentication(accessToken)
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun isLogout(accessToken: String): Boolean {
        redisService.getValue("${LOGOUT_ACCESS_TOKEN_PREFIX}:$accessToken")
            ?: return false
        return true
    }
}