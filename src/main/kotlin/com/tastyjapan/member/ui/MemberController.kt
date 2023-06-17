package com.tastyjapan.member.ui

import com.tastyjapan.global.annotation.CurrentMember
import com.tastyjapan.global.response.ApiResponse
import com.tastyjapan.global.response.ApiUtils
import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.service.MemberService
import com.tastyjapan.member.ui.dto.MemberResponse
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
class MemberController(val memberService: MemberService) {
    @GetMapping
    fun getAllUsers(): ResponseEntity<ApiResponse<List<MemberResponse>>> {
        val allMembers = memberService.getAllMembers()
        val apiResponse = ApiUtils.success(allMembers)
        return ResponseEntity.ok(apiResponse)
    }

    @GetMapping("/{user-id}", "/")
    fun getUser(
        @CurrentMember member: Member,
        @PathVariable(required = false, name = "user-id") userId: Long?
    ): ResponseEntity<ApiResponse<MemberResponse>> {
        val memberResponse = userId?.let {
            memberService.getMember(userId)
        } ?: memberService.getMember(member.id)
        val apiResponse = ApiUtils.success(memberResponse)
        return ResponseEntity.ok(apiResponse)
    }

}