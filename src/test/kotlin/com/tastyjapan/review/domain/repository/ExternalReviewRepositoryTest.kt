package com.tastyjapan.review.domain.repository

import com.tastyjapan.review.domain.ExternalReview
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class ExternalReviewRepositoryTest {

    @Autowired
    lateinit var externalReviewRepository: ExternalReviewRepository

    lateinit var externalReview: ExternalReview

    @BeforeEach
    fun setUp() {
        externalReviewRepository.deleteAll()
        externalReview = ExternalReview(
            content = "externalReview1",
            url = "https://www.google.com",
            nickname = "nickname1",
            rating = 5.00,
            source = "google"
        )
        externalReviewRepository.save(externalReview)
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 메뉴를 저장하면, 해당 ID의 메뉴는 후에 작성된 메뉴 정보로 업데이트 된다.")
    @Test
    fun saveSameId() {
        // given
        val externalReview2 = ExternalReview(
            id = externalReview.id,
            content = "externalReview2",
            url = "https://www.google.com",
            nickname = "nickname2",
            rating = 4.00,
            source = "google"
        )

        // when
        externalReviewRepository.save(externalReview2)

        // then
        assertThat(
            externalReviewRepository.findById(externalReview.id).get().content
        ).isEqualTo(externalReview2.content)
    }

    @DisplayName("id가 없는 Menu 엔티티를 저장하면 순차적으로 ID를 부여하여 저장한다.")
    @Test
    fun saveNoIdGroup() {
        // given
        val externalReview2 = ExternalReview(
            content = "externalReview2",
            url = "https://www.google.com",
            nickname = "nickname2",
            rating = 4.00,
            source = "google"
        )
        externalReviewRepository.save(externalReview2)

        // when&then
        val idDiff = externalReview2.id - externalReview.id
        assertThat(idDiff).isEqualTo(1L);
    }
}