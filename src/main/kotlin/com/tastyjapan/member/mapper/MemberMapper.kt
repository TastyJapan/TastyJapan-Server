package com.tastyjapan.member.mapper

import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.ui.dto.MemberResponse
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers


@Mapper
interface MemberMapper {

    companion object {
        val INSTANCE: MemberMapper = Mappers.getMapper(MemberMapper::class.java)
    }

    fun memberEntityToResponse(member: Member): MemberResponse
}