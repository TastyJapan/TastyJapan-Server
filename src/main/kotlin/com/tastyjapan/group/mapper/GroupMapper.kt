package com.tastyjapan.group.mapper

import com.tastyjapan.group.domain.Groups
import com.tastyjapan.group.ui.dto.GroupsResponse
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface GroupMapper {
    companion object {
        val INSTANCE: GroupMapper = Mappers.getMapper(GroupMapper::class.java)
    }

    fun groupEntityToResponse(groups: Groups): GroupsResponse
}