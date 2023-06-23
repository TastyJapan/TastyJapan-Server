package com.tastyjapan.menu.domain.repository

import com.tastyjapan.group.domain.Groups
import com.tastyjapan.member.domain.Member
import com.tastyjapan.member.domain.Role
import com.tastyjapan.menu.domain.Menu
import com.tastyjapan.menu.domain.MenuSort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class MenuRepositoryTest {

    @Autowired
    lateinit var menuRepository: MenuRepository

    lateinit var menu: Menu
    @BeforeEach
    private fun setUp() {
        menuRepository.deleteAll()
        menu = Menu(
            name = "menu1",
            price = 1000,
            menu_sort = MenuSort.RAMEN,
        )
        menuRepository.save(menu)
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 메뉴를 저장하면, 해당 ID의 메뉴는 후에 작성된 메뉴 정보로 업데이트 된다.")
    @Test
    fun saveSameId() {
        // given
        val menu2: Menu = Menu(
            id = menu.id,
            name = "menu2",
            price = 2000,
            menu_sort = MenuSort.RAMEN,
            )
        // when
        menuRepository.save(menu2)

        // then
        assertThat(menuRepository.findById(menu.id).get().name).isEqualTo(menu2.name)
    }


    @DisplayName("id가 없는 Menu 엔티티를 저장하면 순차적으로 ID를 부여하여 저장한다.")
    @Test
    fun saveNoIdGroup() {
        // given
        val menu2 = Menu(name = "menu2", price = 2000, menu_sort = MenuSort.RAMEN)
        menuRepository.save(menu2)

        // when&then
        val idDiff = menu2.id - menu.id
        assertThat(idDiff).isEqualTo(1L);
    }

}