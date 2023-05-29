package com.tastyjapan.member.service

import com.tastyjapan.exception.ErrorType
import com.tastyjapan.exception.ExceptionResponse
import com.tastyjapan.exception.TastyJapanException
import com.tastyjapan.member.domain.repository.MemberRepository
import com.tastyjapan.member.mapper.MemberMapper
import com.tastyjapan.member.ui.dto.MemberResponse
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors
import java.util.stream.Stream

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
class MemberService(val memberRepository: MemberRepository) {
    fun getAllMembers(): List<MemberResponse> {
        return memberRepository.findAll().stream()
            .map { member -> MemberMapper.INSTANCE.memberEntityToResponse(member) }
            .collect(Collectors.toList())
    }

}