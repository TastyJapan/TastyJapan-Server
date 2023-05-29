package com.tastyjapan.group.domain.repository

import com.tastyjapan.group.domain.Groups
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository : JpaRepository<Groups, Long>, GroupRepositoryCustom{
}