package com.tastyjapan.menu.domain.repository

import com.tastyjapan.menu.domain.Menu
import org.springframework.data.jpa.repository.JpaRepository

interface MenuRepository : JpaRepository<Menu, Long>, MenuRepositoryCustom {
}