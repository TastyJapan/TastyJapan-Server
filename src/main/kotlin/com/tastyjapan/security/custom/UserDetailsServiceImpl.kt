package com.tastyjapan.security.custom

import com.tastyjapan.exception.ErrorType
import com.tastyjapan.member.domain.repository.MemberRepository
import com.tastyjapan.security.exception.BusinessException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val memberRepository: MemberRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return UserDetailsImpl(
            memberRepository.findByEmail(username)
                ?: throw BusinessException(ErrorType.USER_NOT_FOUND)
        )
    }
}