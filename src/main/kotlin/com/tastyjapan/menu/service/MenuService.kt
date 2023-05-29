package com.tastyjapan.menu.service

import com.tastyjapan.menu.domain.repository.MenuRepository
import com.tastyjapan.menu.mapper.ConvertStringToMenu
import com.tastyjapan.menu.ui.dto.MenuResponse
import com.tastyjapan.menu.ui.dto.MenuRequest
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
class MenuService (val menuRepository: MenuRepository, val convertStringToMenu: ConvertStringToMenu) {
    fun getMenuByRestaurantId(restaurantId: Long): List<MenuResponse> {
        return menuRepository.findMenuByRestaurantId(restaurantId).stream()
            .map { menu -> MenuResponse(menu) }
            .collect(Collectors.toList())
    }
}