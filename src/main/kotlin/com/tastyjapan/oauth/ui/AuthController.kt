package com.tastyjapan.oauth.ui

import com.tastyjapan.global.annotation.CurrentMember
import com.tastyjapan.global.response.ApiResponse
import com.tastyjapan.global.response.ApiUtils
import com.tastyjapan.oauth.service.AuthService
import com.tastyjapan.member.domain.Member
import com.tastyjapan.oauth.ui.dto.SignInResponse
import com.tastyjapan.security.custom.JwtTokens
import com.tastyjapan.oauth.ui.dto.SignUpRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/signin")
    fun signIn(
        @RequestHeader(value = "auth-code") code: String,
        @RequestHeader(value = "provider") provider: String
    ): ResponseEntity<ApiResponse<SignInResponse>> {
        val result = authService.signIn(code = code, provider = provider)
        val apiResponse = ApiUtils.success(result)
        return ResponseEntity.ok(apiResponse)
    }

    @PostMapping("/refresh")
    fun refresh(@RequestHeader(value = "Refresh-Token") refreshToken: String): ResponseEntity<ApiResponse<JwtTokens>> {
        val result = authService.refresh(refreshToken)
        val apiResponse = ApiUtils.success(result)
        return ResponseEntity.ok(apiResponse)
    }

    @PostMapping("/logout")
    fun logout(
        @RequestHeader(value = "Authorization") accessToken: String,
        @RequestHeader(value = "Refresh-Token") refreshToken: String
    ): ResponseEntity<ApiResponse<Boolean>> {
        authService.logout(accessToken, refreshToken)
        return ResponseEntity.ok(ApiUtils.success(true))
    }

    @PostMapping("/withdrawal")
    fun withdraw(@CurrentMember member: Member): ResponseEntity<ApiResponse<Boolean>> {
        authService.withdraw(member.id)
        return ResponseEntity.ok(ApiUtils.success(true))
    }
}