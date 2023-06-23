package com.tastyjapan.menu.mapper

import com.tastyjapan.exception.ErrorType
import com.tastyjapan.exception.ExceptionResponse
import com.tastyjapan.exception.TastyJapanException
import com.tastyjapan.menu.domain.MenuSort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class ConvertStringToMenu {
    fun convertMenuToEnum(menu: String): MenuSort {
        try {
            return MenuSort.valueOf(menu.uppercase())
        } catch (e: IllegalArgumentException) {
            throw TastyJapanException(HttpStatus.BAD_REQUEST, ExceptionResponse(ErrorType.MENU_NOT_FOUND))
        }
    }
}